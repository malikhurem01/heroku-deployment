package com.internship.AuctionApp.Bids;

import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.services.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
public class BidController {

    @Autowired
    private final BidService bidService;

    @GetMapping(path = "/get/bid-history/{id}")
    public ResponseEntity<?> getBidHistory(@PathVariable String id) {
        final Long product_id = Long.valueOf(id);
        final BidResponse bidResponse = bidService.getBidHistory(product_id);
        return new ResponseEntity<>(bidResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/post/bid")
    public ResponseEntity<?> bidOnProduct(@RequestBody BidCreateRequest bidCreateRequest, HttpServletRequest request) throws Exception {
        Bid bid;
        try {
            bid = bidService.createBid(bidCreateRequest, request.getHeader(AUTHORIZATION));
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }
        return ResponseEntity.ok().body(bid);
    }
}
