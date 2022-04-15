package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.DTOs.ProductDTO;

import java.util.List;

public class ProductFilterResponse {
    private List<ProductDTO> products;
    private int total;
    private int pageSize;

    public ProductFilterResponse(List<ProductDTO> products, int pageSize, int total) {
        this.products = products;
        this.total = total;
        this.pageSize = pageSize;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
