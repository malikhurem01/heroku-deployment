package com.internship.AuctionApp.Models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bid")
@Getter
@Setter
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
}
