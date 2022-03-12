package com.internship.AuctionApp.Bids;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.services.BidServiceImpl;
import com.internship.AuctionApp.services.ProductServiceImpl;
import com.internship.AuctionApp.services.UserServiceImpl;
import com.internship.AuctionApp.utils.JWTDecode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"https://auction-app-internship-fr.herokuapp.com/"})
public class BidController {

    @Autowired
    private final BidServiceImpl bidServiceImpl;

    @Autowired
    private final UserServiceImpl userServiceImpl;

    @Autowired
    private final ProductServiceImpl productServiceImpl;

    @GetMapping(path = "/get/bid-history/{id}")
    public ResponseEntity<?> getBidHistory(@PathVariable String id) {
        final Long product_id = Long.valueOf(id);
        final Product product = productServiceImpl.getProduct(product_id);
        final List<Bid> bidsList = bidServiceImpl.findAllBidByProductId(product);
        Long latestBidderId = null;
        BidResponse bidResponse;
        if (bidsList.size() > 0) {
            latestBidderId = bidsList.get(bidsList.size() - 1).getUser_id().getUser_id();
            double max = bidsList.stream()
                    .mapToDouble(v -> v.getBid_price())
                    .max().orElseThrow(NoSuchElementException::new);
            bidResponse = new BidResponse(latestBidderId, (float) max, bidsList.size());
        } else {
            bidResponse = new BidResponse(latestBidderId, product.getStart_price(), 0);
        }
        return new ResponseEntity<>(bidResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/post/bid")
    public ResponseEntity<?> bidOnProduct(@RequestBody BidCreateRequest bidCreateRequest, HttpServletRequest request) {
        final Long product_id = Long.valueOf(bidCreateRequest.getProduct_id());
        Product product = null;
        try {
            product = productServiceImpl.getProduct(product_id);
        } catch (Exception err) {
            return new ResponseEntity<>("Product does not exist.", HttpStatus.BAD_REQUEST);
        }
        final DecodedJWT decodedJWT = JWTDecode.verifyToken(request.getHeader(AUTHORIZATION));
        final String username = decodedJWT.getSubject();
        User user = userServiceImpl.loadUserByUsername(username);
        if (user.getUser_id().equals(product.getUser_id())) {
            return new ResponseEntity<>("User can not bid on his own product.", HttpStatus.UNAUTHORIZED);
        }
        if (bidCreateRequest.getBid_price() < product.getStart_price()) {
            return new ResponseEntity<>("Bid can not be lower than the starting price.", HttpStatus.BAD_REQUEST);
        }
        final List<Bid> bidsList = bidServiceImpl.findAllBidByProductId(product);
        for (int i = 0; i < bidsList.size(); i++) {
            if (bidsList.get(i).getBid_price() >= bidCreateRequest.getBid_price()) {
                return new ResponseEntity<>("Bids lower than the highest one are not allowed.", HttpStatus.BAD_REQUEST);
            }
        }
        final Timestamp created_at = new Timestamp(System.currentTimeMillis());
        final Bid bid = new Bid(user, product, bidCreateRequest.getBid_price(), created_at);
        try {
            bidServiceImpl.createBid(bid);
        } catch (Exception exception) {
            return new ResponseEntity<>("Server error.", HttpStatus.SERVICE_UNAVAILABLE);
        }
        return ResponseEntity.ok().body(bid);
    }
}
