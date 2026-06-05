package com.foodtruth.controller;

import com.foodtruth.model.Recipe;
import com.foodtruth.repository.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RecipeController {

    private static final Map<String,String> CAT_EMOJI = new LinkedHashMap<>();
    static { CAT_EMOJI.put("荤菜","🥩"); CAT_EMOJI.put("素菜","🥬"); CAT_EMOJI.put("汤羹","🥣"); CAT_EMOJI.put("主食","🍚"); CAT_EMOJI.put("凉菜","🥒"); }

    private final RecipeRepository repo;
    public RecipeController(RecipeRepository repo) { this.repo = repo; }

    @GetMapping("/recipes")
    public String list(Model model) {
        List<Recipe> all = repo.findAll();
        Map<String,List<Recipe>> grouped = all.stream()
            .collect(Collectors.groupingBy(Recipe::getCategory, LinkedHashMap::new, Collectors.toList()));
        model.addAttribute("grouped", grouped);
        model.addAttribute("catEmoji", CAT_EMOJI);
        model.addAttribute("total", all.size());
        return "recipes";
    }

    @GetMapping("/recipes/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return repo.findById(id).map(r -> {
            model.addAttribute("recipe", r);
            return "recipe-detail";
        }).orElse("redirect:/recipes");
    }

    public void saveAll(List<Recipe> recipes) { repo.saveAll(recipes); }
    public long count() { return repo.count(); }
}
