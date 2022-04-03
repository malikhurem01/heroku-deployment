package com.internship.AuctionApp.DTOs;

import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private List<SubcategoryDTO> subcategoryDTOList;

    public CategoryDTO(Long id, List<SubcategoryDTO> subcategoryDTOList, String name) {
        this.id = id;
        this.name = name;
        this.subcategoryDTOList = subcategoryDTOList;
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

    public List<SubcategoryDTO> getSubcategoryDTOList() {
        return subcategoryDTOList;
    }

    public void setSubcategoryDTOList(List<SubcategoryDTO> subcategoryDTOList) {
        this.subcategoryDTOList = subcategoryDTOList;
    }
}
