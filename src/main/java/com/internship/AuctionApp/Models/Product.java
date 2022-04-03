package com.internship.AuctionApp.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Product {

    @Id
    @SequenceGenerator(name = "SEQUENCE_GENERATOR", allocationSize = 1, sequenceName = "SEQUENCE_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GENERATOR")
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Category category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subcategory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Subcategory subcategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", orphanRemoval = true)
    @JsonManagedReference
    private List<Image> images;

    @Column(name = "image_main_url", columnDefinition = "TEXT")
    private String imageMainUrl;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(name = "on_stock", nullable = false)
    private int onStock;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String size;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "start_price", nullable = false)
    private float startPrice;

    @Column(name = "auction_date_start", nullable = false)
    private Date auctionDateStart;

    @Column(name = "auction_date_end", nullable = false)
    private Date auctionDateEnd;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String country;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String city;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String street;

    @Column(name = "zip_code")
    private int zipCode;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private Product(ProductBuilder productBuilder) {
        this.user = productBuilder.user;
        this.category = productBuilder.category;
        this.subcategory = productBuilder.subcategory;
        this.imageMainUrl = productBuilder.imageMainUrl;
        this.title = productBuilder.title;
        this.onStock = productBuilder.onStock;
        this.size = productBuilder.size;
        this.description = productBuilder.description;
        this.startPrice = productBuilder.startPrice;
        this.auctionDateStart = productBuilder.auctionDateStart;
        this.auctionDateEnd = productBuilder.auctionDateEnd;
        this.country = productBuilder.country;
        this.city = productBuilder.city;
        this.street = productBuilder.street;
        this.zipCode = productBuilder.zipCode;
        this.createdAt = productBuilder.createdAt;
        this.updatedAt = productBuilder.updatedAt;
    }

    public Product() {

    }

    public static class ProductBuilder {
        private User user;
        private Category category;
        private Subcategory subcategory;
        private String imageMainUrl;
        private String title;
        private int onStock;
        private String size;
        private String description;
        private float startPrice;
        private Date auctionDateStart;
        private Date auctionDateEnd;
        private String country;
        private String city;
        private String street;
        private int zipCode;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public ProductBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public ProductBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }


        public ProductBuilder setSubcategory(Subcategory subcategory) {
            this.subcategory = subcategory;
            return this;
        }

        public ProductBuilder setMainImageUrl(String url) {
            this.imageMainUrl = url;
            return this;
        }

        public ProductBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ProductBuilder setOnStock(int onStock) {
            this.onStock = onStock;
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

        public ProductBuilder setStartPrice(float startPrice) {
            this.startPrice = startPrice;
            return this;
        }

        public ProductBuilder setAuctionDateStart(Date auctionDateStart) {
            this.auctionDateStart = auctionDateStart;
            return this;
        }

        public ProductBuilder setAuctionDateEnd(Date auctionDateEnd) {
            this.auctionDateEnd = auctionDateEnd;
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

        public ProductBuilder setZipCode(int zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public ProductBuilder setCreatedAt(Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProductBuilder setUpdatedAt(Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public String getImageMainUrl() {
        return imageMainUrl;
    }

    public void setImageMainUrl(String imageMainUrl) {
        this.imageMainUrl = imageMainUrl;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOnStock() {
        return onStock;
    }

    public void setOnStock(int onStock) {
        this.onStock = onStock;
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

    public float getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(float startPrice) {
        this.startPrice = startPrice;
    }

    public Date getAuctionDateStart() {
        return auctionDateStart;
    }

    public void setAuctionDateStart(Date auctionDateStart) {
        this.auctionDateStart = auctionDateStart;
    }

    public Date getAuctionDateEnd() {
        return auctionDateEnd;
    }

    public void setAuctionDateEnd(Date auctionDateEnd) {
        this.auctionDateEnd = auctionDateEnd;
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

}
