package com.internship.AuctionApp.DTOs;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private Product product;
    private List<Image> images;


    public ProductDTO(Product product, List<Image> images) {
        this.product = product;
        this.images = images;
    }

    public ProductDTO() {

    }
}
