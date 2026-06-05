package com.foodtruth.repository;

import com.foodtruth.model.ShoppingGuide;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingGuideRepository extends JpaRepository<ShoppingGuide, Long> {
    List<ShoppingGuide> findByNameContainingOrHowToPickContaining(String name, String pick);
}
