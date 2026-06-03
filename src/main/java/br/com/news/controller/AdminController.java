package br.com.news.controller;

import br.com.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final NewsService newsService;

    @Autowired
    public AdminController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/admin")
    public String admin(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.isAuthenticated()) {
            return "redirect:/login";
        }

        return "redirect:/admin/news";
    }

    @GetMapping("/admin/news")
    public String adminNews(Model model) {
        model.addAttribute("newsList", newsService.findAll());
        return "admin/news/index";
    }
}
