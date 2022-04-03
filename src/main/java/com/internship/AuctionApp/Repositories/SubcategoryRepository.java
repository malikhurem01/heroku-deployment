package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
}
