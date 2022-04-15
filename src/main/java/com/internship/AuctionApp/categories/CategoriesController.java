package com.internship.AuctionApp.categories;

import com.internship.AuctionApp.DTOs.CategoryDTO;
import com.internship.AuctionApp.DTOs.SubcategoryDTO;
import com.internship.AuctionApp.Models.Category;
import com.internship.AuctionApp.Models.Subcategory;
import com.internship.AuctionApp.Repositories.CategoryRepository;
import com.internship.AuctionApp.Repositories.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
@RequestMapping(path = "/api/v1")
public class CategoriesController {

    CategoryRepository categoryRepository;
    SubcategoryRepository subcategoryRepository;

    @Autowired
    public CategoriesController(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @GetMapping(path = "/get/categories")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categoriesList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = categoriesList.stream().map(category -> {
            List<SubcategoryDTO> subcategoryDTOList = category.getSubcategoryList().stream().map(subcategory -> {
                Long subcategoryId = subcategory.getId();
                Subcategory subcategoryItem = subcategoryRepository.findById(subcategoryId).get();
                SubcategoryDTO subcategoryDTO = new SubcategoryDTO(
                        subcategoryItem.getId(),
                        subcategoryItem.getCategory().getId(),
                        subcategoryItem.getName(),
                        subcategoryItem.getProductList().size());
                return subcategoryDTO;
            }).collect(Collectors.toList());
            subcategoryDTOList = subcategoryDTOList.stream().filter(subcategoryDTO -> subcategoryDTO.getTotal() > 0)
                    .collect(Collectors.toList());
            return new CategoryDTO(category.getId(), subcategoryDTOList, category.getName());
        }).collect(Collectors.toList());
        categoryDTOList = categoryDTOList.stream().filter(categoryDTO -> !categoryDTO.getSubcategories().isEmpty())
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryDTOList);
    }
}
