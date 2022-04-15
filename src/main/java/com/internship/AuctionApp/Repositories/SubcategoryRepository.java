package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    public List<Subcategory> findAllByIdIn(List<Long> subcategoryList);
}
