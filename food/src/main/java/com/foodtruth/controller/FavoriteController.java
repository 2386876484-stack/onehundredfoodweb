package com.foodtruth.controller;

import com.foodtruth.model.Favorite;
import com.foodtruth.model.User;
import com.foodtruth.repository.FavoriteRepository;
import com.foodtruth.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class FavoriteController {

    private final FavoriteRepository favRepo;
    private final UserRepository userRepo;

    public FavoriteController(FavoriteRepository fr, UserRepository ur) { this.favRepo = fr; this.userRepo = ur; }

    @GetMapping("/favorites")
    public String list(Model model, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        List<Favorite> favs = favRepo.findByUserIdOrderByCreatedAtDesc(user.getId());
        List<Favorite> foods = favs.stream().filter(f -> f.getFoodId() != null).toList();
        List<Favorite> recipes = favs.stream().filter(f -> f.getRecipeId() != null).toList();
        model.addAttribute("foodFavs", foods);
        model.addAttribute("recipeFavs", recipes);
        return "favorites";
    }

    @PostMapping("/favorite/toggle/food/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Map<String,Object>> toggleFood(@PathVariable Long id, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        Optional<Favorite> existing = favRepo.findByUserIdAndFoodId(user.getId(), id);
        if (existing.isPresent()) { favRepo.delete(existing.get()); return ResponseEntity.ok(Map.of("favorited", false)); }
        else { Favorite f = new Favorite(); f.setUserId(user.getId()); f.setFoodId(id); favRepo.save(f); return ResponseEntity.ok(Map.of("favorited", true)); }
    }

    @PostMapping("/favorite/toggle/recipe/{id}")
    @ResponseBody
    @Transactional
    public ResponseEntity<Map<String,Object>> toggleRecipe(@PathVariable Long id, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        Optional<Favorite> existing = favRepo.findByUserIdAndRecipeId(user.getId(), id);
        if (existing.isPresent()) { favRepo.delete(existing.get()); return ResponseEntity.ok(Map.of("favorited", false)); }
        else { Favorite f = new Favorite(); f.setUserId(user.getId()); f.setRecipeId(id); favRepo.save(f); return ResponseEntity.ok(Map.of("favorited", true)); }
    }

    @GetMapping("/favorite/check/food/{id}")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> checkFood(@PathVariable Long id, Authentication auth) {
        if (auth == null) return ResponseEntity.ok(Map.of("favorited", false));
        User user = userRepo.findByUsername(auth.getName()).orElse(null);
        if (user == null) return ResponseEntity.ok(Map.of("favorited", false));
        return ResponseEntity.ok(Map.of("favorited", favRepo.existsByUserIdAndFoodId(user.getId(), id)));
    }
}
