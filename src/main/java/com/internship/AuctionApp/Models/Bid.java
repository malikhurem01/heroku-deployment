package com.internship.AuctionApp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Optional;

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
    private User user_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product_id;

    @Column
    private float bid_price;

    @Column
    private Timestamp created_at;

    public Bid(User user_id, Product product_id, float bid_price, Timestamp created_at) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.bid_price = bid_price;
        this.created_at = created_at;
    }

    public Bid() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }

    public float getBid_price() {
        return bid_price;
    }

    public void setBid_price(float bid_price) {
        this.bid_price = bid_price;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
