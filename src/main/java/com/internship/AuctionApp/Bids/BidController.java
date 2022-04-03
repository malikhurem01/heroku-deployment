package com.internship.AuctionApp.Bids;

import com.internship.AuctionApp.DTOs.BidDTO;
import com.internship.AuctionApp.authentication.AuthenticationController;
import com.internship.AuctionApp.DTOs.BidHistoryDTO;
import com.internship.AuctionApp.Exceptions.BidRequestNotValidException;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping(path = "/get/bid-history/{id}")
    public ResponseEntity<?> getBidHistory(@PathVariable final String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            final Long product_id = Long.valueOf(id);
            BidHistoryDTO bidHistoryDTO = bidService.getBidHistory(product_id);
            return new ResponseEntity<>(bidHistoryDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/post/bid")
    public ResponseEntity<?> bidOnProduct(@RequestBody final BidCreateRequest bidCreateRequest,
                                          final HttpServletRequest request) throws Exception {
        try {
            BidDTO bid = bidService.createBid(bidCreateRequest, request.getHeader(AUTHORIZATION));
            return ResponseEntity.ok().body(bid);
        } catch (BidRequestNotValidException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
