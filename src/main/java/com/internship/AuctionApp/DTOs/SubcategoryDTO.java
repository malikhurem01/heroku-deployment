package com.internship.AuctionApp.DTOs;

import java.util.List;

public class SubcategoryDTO {
    private Long id;
    private String name;
    private int total;

    public SubcategoryDTO(Long id, String name, int total) {
        this.id = id;
        this.name = name;
        this.total = total;
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
