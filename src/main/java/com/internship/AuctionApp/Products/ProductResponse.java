package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.Models.Image;
import com.internship.AuctionApp.Models.Product;

import java.util.List;

public class ProductResponse {
    private Product product;
    private List<Image> images;


    public ProductResponse(Product product, List<Image> images) {
        this.product = product;
        this.images = images;
    }

    public ProductResponse() {

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
