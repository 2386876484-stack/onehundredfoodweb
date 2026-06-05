package com.foodtruth.controller;

import com.foodtruth.dto.MythCreateDTO;
import com.foodtruth.model.FoodItem;
import com.foodtruth.model.FoodMyth;
import com.foodtruth.service.FoodService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> listFoods(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag) {
        return ResponseEntity.ok(service.searchFoods(search, category, tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFood(@PathVariable Long id) {
        return service.getFoodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/myths")
    public ResponseEntity<?> addMyth(@PathVariable Long id,
                                     @Valid @RequestBody MythCreateDTO dto) {
        try {
            FoodMyth created = service.addMyth(id, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
