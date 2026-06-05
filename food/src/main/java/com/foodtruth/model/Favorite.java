package com.foodtruth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "favorite", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "food_id"}),
    @UniqueConstraint(columnNames = {"user_id", "recipe_id"})
})
public class Favorite {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "user_id", nullable = false) private Long userId;
    @Column(name = "food_id") private Long foodId;
    @Column(name = "recipe_id") private Long recipeId;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "food_id", insertable = false, updatable = false)
    private FoodItem food;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; } public void setUserId(Long u) { this.userId = u; }
    public Long getFoodId() { return foodId; } public void setFoodId(Long f) { this.foodId = f; }
    public Long getRecipeId() { return recipeId; } public void setRecipeId(Long r) { this.recipeId = r; }
    public FoodItem getFood() { return food; }
    public Recipe getRecipe() { return recipe; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
