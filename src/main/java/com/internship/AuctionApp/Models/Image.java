package com.internship.AuctionApp.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "SEQUENCE_GENERATOR", allocationSize = 1)
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product productId;

    @Column(name="image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public Image(Long imageId, Product productId, String imageUrl) {
        this.imageId = imageId;
        this.productId = productId;
        this.imageUrl = imageUrl;
    }

    public Image() {

    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
