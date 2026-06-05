package com.foodtruth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_myth")
public class FoodMyth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    @JsonBackReference
    private FoodItem foodItem;

    @Column(nullable = false, length = 500)
    private String myth;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String truth;

    @Column(nullable = false, length = 300)
    private String summary;

    @Column(length = 200)
    private String source;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FoodItem getFoodItem() { return foodItem; }
    public void setFoodItem(FoodItem foodItem) { this.foodItem = foodItem; }

    public String getMyth() { return myth; }
    public void setMyth(String myth) { this.myth = myth; }

    public String getTruth() { return truth; }
    public void setTruth(String truth) { this.truth = truth; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
