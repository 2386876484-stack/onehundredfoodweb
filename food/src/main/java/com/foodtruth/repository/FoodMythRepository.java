package com.foodtruth.repository;

import com.foodtruth.model.FoodMyth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMythRepository extends JpaRepository<FoodMyth, Long> {
}
