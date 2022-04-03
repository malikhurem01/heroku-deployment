package com.internship.AuctionApp.DTOs;

import java.sql.Timestamp;

public class BidDTO {
    private Long id;
    private UserDTO userDTO;
    private ProductDTO productDTO;
    private float bidPrice;
    private Timestamp created_at;

    public BidDTO(Long id, UserDTO userDTO, ProductDTO productDTO, float bidPrice, Timestamp created_at) {
        this.id = id;
        this.userDTO = userDTO;
        this.productDTO = productDTO;
        this.bidPrice = bidPrice;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        userDTO = userDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        productDTO = productDTO;
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
