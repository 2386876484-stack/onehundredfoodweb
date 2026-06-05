package com.foodtruth.repository;

import com.foodtruth.model.FoodItem;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class FoodItemSpecifications {

    public static Specification<FoodItem> hasSearch(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + keyword.toLowerCase() + "%";
            var mythJoin = root.join("myths", JoinType.LEFT);
            return cb.or(
                cb.like(cb.lower(root.get("name")), pattern),
                cb.like(cb.lower(root.get("description")), pattern),
                cb.like(cb.lower(mythJoin.get("myth")), pattern),
                cb.like(cb.lower(mythJoin.get("truth")), pattern)
            );
        };
    }

    public static Specification<FoodItem> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<FoodItem> hasTag(String tag) {
        return (root, query, cb) -> {
            if (tag == null || tag.isBlank()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tag"), tag);
        };
    }
}
