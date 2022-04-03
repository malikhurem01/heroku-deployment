package com.internship.AuctionApp.Products;

import com.internship.AuctionApp.DTOs.ProductDTO;

import java.util.List;

public class ProductFilterResponse {
    private List<ProductDTO> productDTOPage;
    private int total;
    private int pageSize;

    public ProductFilterResponse(List<ProductDTO> productDTOPage, int pageSize, int total) {
        this.productDTOPage = productDTOPage;
        this.total = total;
        this.pageSize = pageSize;
    }

    public List<ProductDTO> getProductDTOPage() {
        return productDTOPage;
    }

    public void setProductDTOPage(List<ProductDTO> productDTOPage) {
        this.productDTOPage = productDTOPage;
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
