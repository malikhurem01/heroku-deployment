package com.internship.AuctionApp.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.internship.AuctionApp.Bids.BidCreateRequest;
import com.internship.AuctionApp.Bids.BidResponse;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.Repositories.BidRepository;
import com.internship.AuctionApp.utils.JWTDecode;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;

    private final ProductService productService;

    private final UserServiceImpl userServiceImpl;

    @Override
    public BidResponse getBidHistory(Long product_id) {
        final Product product = productService.getProduct(product_id);
        final List<Bid> bidsList = this.findAllBidByProductId(product);
        Long latestBidderId = null;
        BidResponse bidResponse;
        if (bidsList.size() > 0) {
            latestBidderId = bidsList.get(bidsList.size() - 1).getUser_id().getUser_id();
            double max = bidsList.stream().mapToDouble(v -> v.getBid_price()).max().orElseThrow(NoSuchElementException::new);
            bidResponse = new BidResponse(latestBidderId, (float) max, bidsList.size());
        } else {
            bidResponse = new BidResponse(latestBidderId, product.getStart_price(), 0);
        }
        return bidResponse;
    }

    @Override
    public Bid createBid(BidCreateRequest bidCreateRequest, String authorizationHeader) throws Exception {
        Long product_id = Long.valueOf(bidCreateRequest.getProduct_id());
        Product product = null;
        try {
            product = productService.getProduct(product_id);
        } catch (Exception err) {
            throw new Exception("Product not found");
        }

        final DecodedJWT decodedJWT = JWTDecode.verifyToken(authorizationHeader);
        final String username = decodedJWT.getSubject();
        User user = userServiceImpl.loadUserByUsername(username);
        if (user.getUser_id().equals(product.getUser_id())) {
            throw new Exception("User can not bid on his own product.");
        }
        if (bidCreateRequest.getBid_price() < product.getStart_price()) {
            throw new Exception("Bid can not be lower than the starting price.");
        }
        final List<Bid> bidsList = this.findAllBidByProductId(product);
        double max = 0;
        if (bidsList.size() > 0) {
            max = bidsList.stream().mapToDouble(v -> v.getBid_price()).max().orElseThrow(NoSuchElementException::new);
        }
        if (max >= bidCreateRequest.getBid_price()) {
            throw new Exception("Bids lower than the highest one are not allowed.");
        }
        final Timestamp created_at = new Timestamp(System.currentTimeMillis());
        final Bid bid = new Bid(user, product, bidCreateRequest.getBid_price(), created_at);
        try {
            return bidRepository.save(bid);
        } catch (Exception exception) {
            throw new Exception("Bidding on product failed.");
        }
    }

    @Override
    public List<Bid> findAllBidByProductId(Product product_id) {
        return bidRepository.findAllByProduct_id(product_id);
    }
}
