package com.internship.AuctionApp.DTOs;

import java.sql.Timestamp;

public class BidDTO {
    private Long id;
    private UserDTO user;
    private ProductDTO products;
    private float bidPrice;
    private Timestamp created_at;

    public BidDTO(Long id, UserDTO user, ProductDTO products, float bidPrice, Timestamp created_at) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.bidPrice = bidPrice;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        user = user;
    }

    public ProductDTO getProducts() {
        return products;
    }

    public void setProducts(ProductDTO products) {
        products = products;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
