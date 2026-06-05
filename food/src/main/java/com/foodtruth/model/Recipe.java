package com.foodtruth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 300)
    private String description;

    @Column(nullable = false, length = 20)
    private String category;

    @Column(length = 10)
    private String difficulty;

    @Column(name = "cooking_time", length = 20)
    private String cookingTime;

    @Column(name = "cost_level", length = 20)
    private String costLevel;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String steps;

    @Column(length = 500)
    private String tips;

    @Column(name = "health_note", length = 300)
    private String healthNote;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public String getCookingTime() { return cookingTime; }
    public void setCookingTime(String cookingTime) { this.cookingTime = cookingTime; }
    public String getCostLevel() { return costLevel; }
    public void setCostLevel(String costLevel) { this.costLevel = costLevel; }
    public String getIngredients() { return ingredients; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
    public String getSteps() { return steps; }
    public void setSteps(String steps) { this.steps = steps; }
    public String getTips() { return tips; }
    public void setTips(String tips) { this.tips = tips; }
    public String getHealthNote() { return healthNote; }
    public void setHealthNote(String healthNote) { this.healthNote = healthNote; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
