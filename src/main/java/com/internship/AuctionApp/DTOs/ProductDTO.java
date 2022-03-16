package com.internship.AuctionApp.DTOs;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;

import java.util.List;

public class ProductDTO {
    private Product product;
    private List<Image> images;


    public ProductDTO(Product product, List<Image> images) {
        this.product = product;
        this.images = images;
    }

    public ProductDTO() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
