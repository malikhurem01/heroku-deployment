package com.internship.AuctionApp.Products;

import java.util.List;

public class FilterRequest {
    //Category List
    private Integer category;
    //Subcategory List
    private List<Integer> subcategories;
    //Price Min
    private float priceMin;
    //Price Max
    private float priceMax;
    private int offset;
    private String sort;
    private int direction;

    public FilterRequest(Integer category, List<Integer> subcategories, float priceMin, float priceMax, int offset, String sort, int direction) {
        this.category = category;
        this.subcategories = subcategories;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
        this.offset = offset;
        this.sort = sort;
        this.direction = direction;
    }

    public boolean isFiltered(){
        return this.category != null || !this.subcategories.isEmpty() || this.priceMin > 0 || this.priceMax > 0;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public List<Integer> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Integer> subcategories) {
        this.subcategories = subcategories;
    }

    public float getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(float priceMin) {
        this.priceMin = priceMin;
    }

    public float getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(float priceMax) {
        this.priceMax = priceMax;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
