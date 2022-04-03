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

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://auction-app-internship-fr.herokuapp.com/"})
@RequestMapping(path = "/api/v1")
public class CategoriesController {

    CategoryRepository categoryRepository;
    SubcategoryRepository subcategoryRepository;

    @Autowired
    public CategoriesController(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository){
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @GetMapping(path = "/get/categories")
    public ResponseEntity<?> getAllCategories(){
        List<Category> categoriesList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(int i = 0; i < categoriesList.size(); i++){
            if(categoriesList.get(i).getProductList().isEmpty()){
                continue;
            }
            final int subcategorySize = categoriesList.get(i).getSubcategoryList().size();
            List<SubcategoryDTO> subcategoryDTOList = new ArrayList<>();
            for(int j = 0; j < subcategorySize; j++){
                if(categoriesList.get(i).getSubcategoryList().get(j).getProductList().isEmpty()){
                    continue;
                }
                Long subcategoryId = categoriesList.get(i).getSubcategoryList().get(j).getId();
                Subcategory subcategory = subcategoryRepository.findById(subcategoryId).get();
                SubcategoryDTO subcategoryDTO = new SubcategoryDTO(
                        subcategory.getId(),
                        subcategory.getName(),
                        subcategory.getProductList().size());
                subcategoryDTOList.add(subcategoryDTO);
            }
            categoryDTOList.add(new CategoryDTO(categoriesList.get(i).getId(), subcategoryDTOList, categoriesList.get(i).getName()));
        }
        return ResponseEntity.ok().body(categoryDTOList);
    }
}
