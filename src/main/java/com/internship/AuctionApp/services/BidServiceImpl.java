package com.internship.AuctionApp.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.internship.AuctionApp.Bids.BidCreateRequest;
import com.internship.AuctionApp.DTOs.BidDTO;
import com.internship.AuctionApp.DTOs.BidHistoryDTO;
import com.internship.AuctionApp.DTOs.ProductDTO;
import com.internship.AuctionApp.DTOs.UserDTO;
import com.internship.AuctionApp.Exceptions.BidRequestNotValidException;
import com.internship.AuctionApp.Exceptions.EntityNotFoundException;
import com.internship.AuctionApp.Exceptions.ServiceException;
import com.internship.AuctionApp.Models.Bid;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Models.User;
import com.internship.AuctionApp.Repositories.BidRepository;
import com.internship.AuctionApp.configuration.JWTConfig;
import com.internship.AuctionApp.utils.JWTDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final ProductService productService;
    private final UserServiceImpl userServiceImpl;
    private final JWTConfig jwtConfig;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, ProductService productService, UserServiceImpl userServiceImpl, JWTConfig jwtConfig) {
        this.bidRepository = bidRepository;
        this.productService = productService;
        this.userServiceImpl = userServiceImpl;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public BidHistoryDTO getBidHistory(final Long productId) throws EntityNotFoundException {
        Product product = null;
        try {
            product = productService.getProduct(productId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Invalid product");
        }
        final List<Bid> bidsList = this.findAllBidByProductId(product);
        Long latestBidderId = null;
        BidHistoryDTO bidResponse;
        if (bidsList.size() > 0) {
            latestBidderId = bidsList.get(bidsList.size() - 1).getUser().getUserId();
            double max = bidsList.stream().mapToDouble(v -> v.getBidPrice()).max().orElseThrow(NoSuchElementException::new);
            bidResponse = new BidHistoryDTO(latestBidderId, (float) max, bidsList.size());
        } else {
            bidResponse = new BidHistoryDTO(latestBidderId, product.getStartPrice(), 0);
        }
        return bidResponse;
    }

    @Override
    public BidDTO createBid(final BidCreateRequest bidCreateRequest, final String authorizationHeader) throws Exception {
        Long productId = Long.valueOf(bidCreateRequest.getProductId());
        Product product = null;
        try {
            product = productService.getProduct(productId);
        } catch (Exception err) {
            throw new EntityNotFoundException("Product not found");
        }
        final DecodedJWT decodedJWT = JWTDecode.verifyToken(authorizationHeader, jwtConfig.getTokenPrefix());
        final String username = decodedJWT.getSubject();
        User user = userServiceImpl.loadUserByUsername(username);
        if (user.getUserId().equals(product.getUser().getUserId())) {
            throw new BidRequestNotValidException("User can not bid on his own product.");
        }
        if (bidCreateRequest.getBidPrice() < product.getStartPrice()) {
            throw new BidRequestNotValidException("Bid can not be lower than the starting price.");
        }
        final List<Bid> bidsList = this.findAllBidByProductId(product);
        double max = 0;
        if (bidsList.size() > 0) {
            max = bidsList.stream().mapToDouble(v -> v.getBidPrice()).max().orElseThrow(NoSuchElementException::new);
        }
        if (max >= bidCreateRequest.getBidPrice()) {
            throw new BidRequestNotValidException("Bids lower than the highest one are not allowed.");
        }
        final Timestamp created_at = new Timestamp(System.currentTimeMillis());
        final Bid bid = new Bid(user, product, bidCreateRequest.getBidPrice(), created_at);
        try {
            bidRepository.save(bid);
        } catch (Exception exception) {
            throw new ServiceException("Bidding on product failed.");
        }
        return new BidDTO(
                bid.getId(),
                new UserDTO(bid.getUser()),
                new ProductDTO(bid.getProductId()),
                bid.getBidPrice(),
                bid.getCreatedAt());
    }

    @Override
    public List<Bid> findAllBidByProductId(final Product product_id) {
        List<Bid> bids = null;
        try {
            bids = bidRepository.findAllByProductId(product_id);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return bids;
    }
}
