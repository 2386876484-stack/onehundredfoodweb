package com.foodtruth.controller;

import com.foodtruth.model.ShoppingGuide;
import com.foodtruth.repository.ShoppingGuideRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ShoppingGuideController {

    private static final Map<String,String> CAT_EMOJI = new LinkedHashMap<>();
    static { CAT_EMOJI.put("水果","🍎"); CAT_EMOJI.put("蔬菜","🥬"); CAT_EMOJI.put("肉类","🥩"); CAT_EMOJI.put("海鲜","🦐"); CAT_EMOJI.put("蛋奶豆","🥚"); }

    private final ShoppingGuideRepository repo;
    public ShoppingGuideController(ShoppingGuideRepository repo) { this.repo = repo; }

    @GetMapping({"/shopping", "/shopping/"})
    public String list(Model model) {
        List<ShoppingGuide> all = repo.findAll();
        Map<String,List<ShoppingGuide>> grouped = all.stream()
            .collect(Collectors.groupingBy(ShoppingGuide::getCategory, LinkedHashMap::new, Collectors.toList()));
        model.addAttribute("grouped", grouped);
        model.addAttribute("catEmoji", CAT_EMOJI);
        model.addAttribute("total", all.size());
        return "shopping";
    }

    @GetMapping("/shopping/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return repo.findById(id).map(s -> { model.addAttribute("s", s); return "shopping-detail"; })
                .orElse("redirect:/shopping");
    }

    public void saveAll(List<ShoppingGuide> list) { repo.saveAll(list); }
    public long count() { return repo.count(); }
}
