package com.foodtruth.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "shopping_guide")
public class ShoppingGuide {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false, length = 20) private String category;
    @Column(name = "how_to_pick", nullable = false, columnDefinition = "TEXT") private String howToPick;
    @Column(name = "storage_tips", length = 500) private String storageTips;
    @Column(length = 100) private String season;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String n) { this.name = n; }
    public String getCategory() { return category; } public void setCategory(String c) { this.category = c; }
    public String getHowToPick() { return howToPick; } public void setHowToPick(String h) { this.howToPick = h; }
    public String getStorageTips() { return storageTips; } public void setStorageTips(String s) { this.storageTips = s; }
    public String getSeason() { return season; } public void setSeason(String s) { this.season = s; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
