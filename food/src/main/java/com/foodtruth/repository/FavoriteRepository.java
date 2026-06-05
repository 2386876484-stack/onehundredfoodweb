package com.foodtruth.repository;

import com.foodtruth.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Favorite> findByUserIdAndFoodId(Long userId, Long foodId);
    Optional<Favorite> findByUserIdAndRecipeId(Long userId, Long recipeId);
    boolean existsByUserIdAndFoodId(Long userId, Long foodId);
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
    void deleteByUserIdAndFoodId(Long userId, Long foodId);
    void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
}
