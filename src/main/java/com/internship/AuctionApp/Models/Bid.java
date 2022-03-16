package com.internship.AuctionApp.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "SEQUENCE_GENERATOR", allocationSize = 1)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product productId;

    @Column(name="bid_price")
    private float bidPrice;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public Bid(User userId, Product productId, float bidPrice, Timestamp createdAt) {
        this.userId = userId;
        this.productId = productId;
        this.bidPrice = bidPrice;
        this.createdAt = createdAt;
    }

    public Bid() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public void setBid_price(float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreated_at(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
