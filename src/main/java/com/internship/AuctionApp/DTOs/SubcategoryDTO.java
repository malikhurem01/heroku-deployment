package com.internship.AuctionApp.DTOs;

public class SubcategoryDTO {
    private Long id;
    private Long categoryId;
    private String name;
    private int total;

    public SubcategoryDTO(Long id, Long categoryId, String name, int total) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.total = total;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
