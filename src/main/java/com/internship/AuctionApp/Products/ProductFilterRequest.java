package com.internship.AuctionApp.Products;

public class ProductFilterRequest {
    private Long id;
    private int offset;
    private int pageSize;
    private String sort;

    public ProductFilterRequest(Long id, int offset, int pageSize, String sort){
        this.id = id;
        this.offset = offset;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public ProductFilterRequest(int offset, int pageSize, String sort){
        this.offset = offset;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
