package com.internship.AuctionApp.services;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import com.internship.AuctionApp.Repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public List<Image> findAllByProductId(Product product) {
        return imageRepository.findAllByProductId(product);
    }
}
