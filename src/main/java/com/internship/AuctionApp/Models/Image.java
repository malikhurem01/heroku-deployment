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
    @Column(nullable = false)
    private Long image_id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product_id;

    @Column(columnDefinition = "TEXT")
    private String image_url;

    public Image(Long image_id, Product product_id, String image_url) {
        this.image_id = image_id;
        this.product_id = product_id;
        this.image_url = image_url;
    }

    public Image() {

    }

    public Long getImage_id() {
        return image_id;
    }

    public void setImage_id(Long image_id) {
        this.image_id = image_id;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
