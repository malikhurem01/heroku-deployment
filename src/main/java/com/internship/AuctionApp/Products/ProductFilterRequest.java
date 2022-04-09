package com.internship.AuctionApp.Products;

import java.util.List;

public class ProductFilterRequest {
    private Long entityId;
    private int offset;
    private int pageSize;
    private String sort;

    public ProductFilterRequest(int offset, int pageSize, String sort){
        this.offset = offset;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public ProductFilterRequest(Long entityId, int offset, int pageSize, String sort){
        this(offset, pageSize, sort);
        this.entityId = entityId;
    }

    public Long getId() {
        return entityId;
    }

    public void setId(Long id) {
        this.entityId = id;
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
