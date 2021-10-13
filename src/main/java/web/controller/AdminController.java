package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;

@Controller
public class AdminController {
    @GetMapping("/")
    public String toGo(Model model, Authentication authentication) {
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String getTo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        return "admin";
    }
}
