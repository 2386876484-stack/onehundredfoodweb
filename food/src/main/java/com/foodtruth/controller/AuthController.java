package com.foodtruth.controller;

import com.foodtruth.model.User;
import com.foodtruth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository ur, PasswordEncoder pe) { this.userRepo = ur; this.encoder = pe; }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/register")
    public String registerPage() { return "register"; }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam(required = false) String email,
                             Model model) {
        if (username == null || username.isBlank() || username.length() < 3) {
            model.addAttribute("error", "用户名至少3个字符");
            return "register";
        }
        if (password == null || password.length() < 4) {
            model.addAttribute("error", "密码至少4位");
            return "register";
        }
        if (userRepo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(encoder.encode(password));
        u.setEmail(email);
        userRepo.save(u);
        return "redirect:/login?registered";
    }
}
