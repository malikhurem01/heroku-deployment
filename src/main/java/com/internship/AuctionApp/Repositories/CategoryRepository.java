package com.internship.AuctionApp.Repositories;

import com.internship.AuctionApp.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByIdIn(List<Long> categories);
}
