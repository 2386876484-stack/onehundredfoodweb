package com.foodtruth.controller;

import com.foodtruth.model.*;
import com.foodtruth.repository.*;
import com.foodtruth.service.FoodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PageController {
    private final FoodService service;
    private final RecipeRepository recipeRepo;
    private final ShoppingGuideRepository shopRepo;
    public PageController(FoodService service, RecipeRepository rr, ShoppingGuideRepository sr) {
        this.service = service; this.recipeRepo = rr; this.shopRepo = sr;
    }

    private static final List<String> CATEGORIES = List.of("饮品","零食","主食调味","烹饪方式","保健品","蔬果肉类");
    private static final Map<String, String> CAT_EMOJI = new LinkedHashMap<>();
    private static final Map<String, String> FOOD_EMOJI = new HashMap<>();
    static {
        CAT_EMOJI.put("饮品","🥤"); CAT_EMOJI.put("零食","🍪"); CAT_EMOJI.put("主食调味","🍞");
        CAT_EMOJI.put("烹饪方式","🍳"); CAT_EMOJI.put("保健品","💊"); CAT_EMOJI.put("蔬果肉类","🥩");
        String[][] fe = {{"果汁","🧃"},{"零度可乐","🥤"},{"蜂蜜水","🍯"},{"红糖水","🟤"},{"运动饮料","⚡"},{"牛奶","🥛"},
        {"黑巧克力","🍫"},{"果蔬脆片","🥬"},{"能量棒","🍫"},{"无糖饼干","🍪"},{"果干","🍇"},{"乳酸菌饮料","🧃"},
        {"全麦面包","🍞"},{"即食麦片","🥣"},{"鸡精","🧂"},{"海盐","🧂"},{"橄榄油","🫒"},{"杂粮饭","🍚"},
        {"初榨橄榄油","🫒"},{"生吃蔬菜","🥗"},{"微波炉","📡"},{"不粘锅","🍳"},
        {"蛋白粉","🥛"},{"维生素C片","💊"},{"钙片","🦴"},{"鱼油","🐟"},{"胶原蛋白","💊"},
        {"土豆","🥔"},{"鸡蛋","🥚"},{"红肉","🥩"},{"水果","🍎"},{"有机食品","🌿"},{"虾","🦐"},{"西兰花","🥦"},
        {"豆浆","🥛"},{"浓茶","🍵"},{"咖啡","☕"},{"海苔","🟩"},{"粗粮饼干","🍪"},{"风味酸奶","🥛"},{"每日坚果","🥜"},
        {"白粥","🥣"},{"方便面","🍜"},{"代糖","🧂"},{"烧烤","🔥"},{"隔夜菜","🍱"},{"空气炸锅","🌀"},
        {"酵素","💊"},{"葡萄籽","💊"},{"益生菌","💊"},{"猪蹄","🐷"},{"鸡汤","🍲"},{"胡萝卜","🥕"},
        {"菠菜","🥬"},{"豆腐","🫘"},{"牛油果","🥑"},{"奶茶","🧋"},{"苏打水","🫧"},{"椰子水","🥥"},{"姜茶","🍵"},
        {"魔芋爽","🌶️"},{"龟苓膏","🟤"},{"茯苓饼","🍥"},{"卤蛋","🥚"},{"寿司","🍣"},{"糙米","🌾"},{"荞麦面","🍜"},
        {"蚝油","🦪"},{"高压锅","♨️"},{"慢炖锅","🍲"},{"蒸菜","♨️"},{"铁锅","🍳"},{"叶酸","💊"},{"褪黑素","💤"},
        {"蜂胶","🍯"},{"阿胶","🟫"},{"苦瓜","🥒"},{"芹菜","🌿"},{"红薯","🍠"},{"山药","🟤"},{"莲藕","🪷"},
        {"银耳","🤍"},{"三文鱼","🍣"},{"鸡胸肉","🍗"},{"猪肝","🫁"},{"羊肉","🐑"},
        {"红酒","🍷"},{"普洱茶","🫖"},{"电解质水","⚡"},{"气泡水","🫧"},{"蛋白奶昔","🥤"},{"酸梅汤","🫐"},
        {"红枣","🟤"},{"黑芝麻糊","⚫"},{"核桃仁","🥜"},{"西梅干","🟤"},{"燕麦奶","🥛"},{"瓜子","🌻"},
        {"花生酱","🥜"},{"陈醋","🟤"},{"意面","🍝"},{"腐乳","🟫"},{"咖喱","🍛"},{"老干妈","🌶️"},
        {"腌制食品","🫙"},{"低温慢煮","🌡️"},{"勾芡","🥄"},{"生腌","🦐"},
        {"护肝片","💊"},{"鱼肝油","💊"},{"维生素D","☀️"},{"螺旋藻","🌿"},{"蛋白棒","🍫"},{"锌片","💊"},
        {"秋葵","🫛"},{"番茄","🍅"},{"洋葱","🧅"},{"大蒜","🧄"},{"芒果","🥭"},{"榴莲","🟡"},
        {"荔枝","🔴"},{"小龙虾","🦞"},{"鸭血","🟫"},{"皮蛋","🥚"},{"螺蛳粉","🍜"},{"蛙肉","🐸"}};
        for (String[] e : fe) FOOD_EMOJI.put(e[0], e[1]);
    }

    @GetMapping("/")
    public String home(Model model) {
        List<FoodItem> all = service.searchFoods(null, null, null);
        List<FoodItem> featured = new ArrayList<>();
        for (FoodItem f : all) {
            if (featured.size() >= 6) break;
            if (!featured.stream().anyMatch(x -> x.getCategory().equals(f.getCategory())))
                featured.add(f);
        }
        // fill remaining
        for (FoodItem f : all) {
            if (featured.size() >= 6) break;
            if (!featured.contains(f)) featured.add(f);
        }

        model.addAttribute("featured", featured);
        model.addAttribute("totalFoods", all.size());
        model.addAttribute("totalMyths", all.stream().mapToInt(f -> f.getMyths().size()).sum());
        model.addAttribute("foodEmoji", FOOD_EMOJI);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("catEmoji", CAT_EMOJI);
        model.addAttribute("tagCounts", Map.of(
            "伪健康", all.stream().filter(f -> "伪健康".equals(f.getTag())).count(),
            "被冤枉", all.stream().filter(f -> "被冤枉".equals(f.getTag())).count()
        ));
        return "home";
    }

    @GetMapping("/category/{name}")
    public String category(@PathVariable String name, Model model) {
        if (!CATEGORIES.contains(name)) return "redirect:/";
        List<FoodItem> list = service.searchFoods(null, name, null);
        model.addAttribute("category", name);
        model.addAttribute("catEmojiIcon", CAT_EMOJI.getOrDefault(name, "📂"));
        model.addAttribute("foods", list);
        model.addAttribute("foodEmoji", FOOD_EMOJI);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("catEmoji", CAT_EMOJI);
        return "category";
    }

    @GetMapping("/all-foods")
    public String allFoods(Model model) {
        List<FoodItem> list = service.searchFoods(null, null, null);
        model.addAttribute("grouped", groupByCat(list));
        model.addAttribute("foodEmoji", FOOD_EMOJI);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("catEmoji", CAT_EMOJI);
        return "all-foods";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "") String q, Model model) {
        model.addAttribute("query", q);
        model.addAttribute("foodEmoji", FOOD_EMOJI);
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("catEmoji", CAT_EMOJI);
        if (q.isBlank()) {
            model.addAttribute("foods", List.of());
            model.addAttribute("recipes", List.of());
            model.addAttribute("shops", List.of());
            return "search";
        }
        model.addAttribute("foods", service.searchFoods(q, null, null));
        model.addAttribute("recipes", recipeRepo.findByNameContainingOrDescriptionContaining(q, q));
        model.addAttribute("shops", shopRepo.findByNameContainingOrHowToPickContaining(q, q));
        return "search";
    }

    @GetMapping("/foods/{id}")
    public String foodDetail(@PathVariable Long id, Model model) {
        return service.getFoodById(id).map(food -> {
            model.addAttribute("food", food);
            model.addAttribute("foodEmoji", FOOD_EMOJI.getOrDefault(food.getName(), "📋"));
            model.addAttribute("categories", CATEGORIES);
            model.addAttribute("catEmoji", CAT_EMOJI);
            return "food-detail";
        }).orElse("redirect:/");
    }

    private Map<String, List<FoodItem>> groupByCat(List<FoodItem> list) {
        return list.stream().collect(Collectors.groupingBy(
            FoodItem::getCategory, LinkedHashMap::new, Collectors.toList()));
    }
}
