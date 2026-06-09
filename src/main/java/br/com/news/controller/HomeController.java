package br.com.news.controller;

import br.com.news.entity.News;
import br.com.news.service.NewsService;
import br.com.news.util.NewsStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final NewsService newsService;

    @Autowired
    public HomeController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String home(@RequestParam(name = "q", required = false) String searchQuery, Model model, HttpServletRequest request) {
        List<News> newsList;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            newsList = newsService.searchByStatusAndTitleOrResume(NewsStatus.APROVADA, searchQuery);
            model.addAttribute("searchQuery", searchQuery);
        } else {
            newsList = newsService.findByStatusOrderByPublicatedAtDesc(NewsStatus.APROVADA, PageRequest.of(0, 12));
        }

        model.addAttribute("currentPath", request.getRequestURI());
        model.addAttribute("newsList", newsList);

        return "public/home/index";
    }
}
