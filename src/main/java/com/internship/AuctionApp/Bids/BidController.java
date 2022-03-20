package com.internship.AuctionApp.Bids;

import com.internship.AuctionApp.DTOs.BidHistoryDTO;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
public class BidController {

    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping(path = "/get/bid-history/{id}")
    public ResponseEntity<?> getBidHistory(@PathVariable final String id) {
        if(id == null){
            return ResponseEntity.badRequest().build();
        }
        final Long product_id = Long.valueOf(id);
        BidHistoryDTO bidResponse;
        try {
            bidResponse = bidService.getBidHistory(product_id);
        }catch (EntityNotFoundException exception) {
            return ResponseEntity.badRequest().build();
        }catch(Exception exception){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(bidResponse, HttpStatus.OK);
    }

    @PostMapping(path = "/post/bid")
    public ResponseEntity<?> bidOnProduct(@RequestBody final BidCreateRequest bidCreateRequest,
                                          final HttpServletRequest request) throws Exception {
        Bid bid;
        try {
            bid = bidService.createBid(bidCreateRequest, request.getHeader(AUTHORIZATION));
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(bid);
    }
}
