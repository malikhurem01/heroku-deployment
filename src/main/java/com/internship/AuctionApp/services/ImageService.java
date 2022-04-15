package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;

import java.util.List;

public interface ImageService {
    public List<Image> findAllByProductId(Product product);
}
