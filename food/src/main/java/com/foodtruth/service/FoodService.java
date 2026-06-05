package com.foodtruth.service;

import com.foodtruth.dto.MythCreateDTO;
import com.foodtruth.model.FoodItem;
import com.foodtruth.model.FoodMyth;
import com.foodtruth.repository.FoodItemRepository;
import com.foodtruth.repository.FoodItemSpecifications;
import com.foodtruth.repository.FoodMythRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    private final FoodItemRepository foodItemRepository;
    private final FoodMythRepository foodMythRepository;

    public FoodService(FoodItemRepository foodItemRepository,
                       FoodMythRepository foodMythRepository) {
        this.foodItemRepository = foodItemRepository;
        this.foodMythRepository = foodMythRepository;
    }

    public List<FoodItem> searchFoods(String search, String category, String tag) {
        Specification<FoodItem> spec = Specification
                .where(FoodItemSpecifications.hasCategory(category))
                .and(FoodItemSpecifications.hasSearch(search))
                .and(FoodItemSpecifications.hasTag(tag));
        return foodItemRepository.findAll(spec);
    }

    @Transactional
    public Optional<FoodItem> getFoodById(Long id) {
        return foodItemRepository.findById(id);
    }

    @Transactional
    public FoodMyth addMyth(Long foodId, MythCreateDTO dto) {
        FoodItem food = foodItemRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("食物不存在"));
        FoodMyth myth = new FoodMyth();
        myth.setFoodItem(food);
        myth.setMyth(dto.getMyth());
        myth.setTruth(dto.getTruth());
        myth.setSummary(dto.getSummary());
        myth.setSource(dto.getSource());
        return foodMythRepository.save(myth);
    }

    public long countFoods() {
        return foodItemRepository.count();
    }

    public void saveAllFoods(List<FoodItem> foods) {
        foodItemRepository.saveAll(foods);
    }
}
