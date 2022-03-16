package com.internship.AuctionApp.Models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @SequenceGenerator(name = "SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "SEQUENCE_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GENERATOR")
    private Long product_id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user_id;

    @Column(columnDefinition = "TEXT")
    private String image_main_url;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(nullable = false)
    private int on_stock;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String size;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private float start_price;

    @Column(nullable = false)
    private Date auction_date_start;

    @Column(nullable = false)
    private Date auction_date_end;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String country;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String city;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String street;

    @Column
    private int zip_code;

    @Column
    private Timestamp created_at;

    @Column
    private Timestamp updated_at;

    private Product(ProductBuilder productBuilder) {
        this.user_id = productBuilder.user_id;
        this.image_main_url = productBuilder.image_main_url;
        this.title = productBuilder.title;
        this.on_stock = productBuilder.on_stock;
        this.size = productBuilder.size;
        this.description = productBuilder.description;
        this.start_price = productBuilder.start_price;
        this.auction_date_start = productBuilder.auction_date_start;
        this.auction_date_end = productBuilder.auction_date_end;
        this.country = productBuilder.country;
        this.city = productBuilder.city;
        this.street = productBuilder.street;
        this.zip_code = productBuilder.zip_code;
        this.created_at = productBuilder.created_at;
        this.updated_at = productBuilder.updated_at;
    }

    public Product() {

    }

    public static class ProductBuilder {
        private User user_id;
        private String image_main_url;
        private String title;
        private int on_stock;
        private String size;
        private String description;
        private float start_price;
        private Date auction_date_start;
        private Date auction_date_end;
        private String country;
        private String city;
        private String street;
        private int zip_code;
        private Timestamp created_at;
        private Timestamp updated_at;

        public ProductBuilder setUser_id(User user) {
            this.user_id = user;
            return this;
        }
        public ProductBuilder setMainImageUrl(String url) {
            this.image_main_url = url;
            return this;
        }

        public ProductBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ProductBuilder setOn_stock(int on_stock) {
            this.on_stock = on_stock;
            return this;
        }

        public ProductBuilder setSize(String size) {
            this.size = size;
            return this;
        }

        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder setStart_price(float start_price) {
            this.start_price = start_price;
            return this;
        }

        public ProductBuilder setAuction_date_start(Date auction_date_start) {
            this.auction_date_start = auction_date_start;
            return this;
        }

        public ProductBuilder setAuction_date_end(Date auction_date_end) {
            this.auction_date_end = auction_date_end;
            return this;
        }

        public ProductBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public ProductBuilder setCity(String city) {
            this.city = city;
            return this;
        }

        public ProductBuilder setStreet(String street) {
            this.street = street;
            return this;
        }

        public ProductBuilder setZip_code(int zip_code) {
            this.zip_code = zip_code;
            return this;
        }

        public ProductBuilder setCreated_at(Timestamp created_at) {
            this.created_at = created_at;
            return this;
        }

        public ProductBuilder setUpdated_at(Timestamp updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getImage_main_url() {
        return image_main_url;
    }

    public void setImage_main_url(String image_main_url) {
        this.image_main_url = image_main_url;
    }

    public User getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOn_stock() {
        return on_stock;
    }

    public void setOn_stock(int on_stock) {
        this.on_stock = on_stock;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getStart_price() {
        return start_price;
    }

    public void setStart_price(float start_price) {
        this.start_price = start_price;
    }

    public Date getAuction_date_start() {
        return auction_date_start;
    }

    public void setAuction_date_start(Date auction_date_start) {
        this.auction_date_start = auction_date_start;
    }

    public Date getAuction_date_end() {
        return auction_date_end;
    }

    public void setAuction_date_end(Date auction_date_end) {
        this.auction_date_end = auction_date_end;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

}
