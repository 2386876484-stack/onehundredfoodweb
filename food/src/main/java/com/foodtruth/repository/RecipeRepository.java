package com.foodtruth.repository;

import com.foodtruth.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByCategoryOrderByCreatedAt(String category);
    List<Recipe> findByNameContainingOrDescriptionContaining(String name, String desc);
}
