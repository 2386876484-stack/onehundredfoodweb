package com.foodtruth.repository;

import com.foodtruth.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository
        extends JpaRepository<FoodItem, Long>,
                JpaSpecificationExecutor<FoodItem> {
}
