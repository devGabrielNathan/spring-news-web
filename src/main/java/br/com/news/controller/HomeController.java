package br.com.news.controller;

import br.com.news.entity.News;
import br.com.news.service.NewsService;
import br.com.news.util.NewsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final NewsService newsService;

    @Autowired
    public HomeController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<News> newsList = newsService.findByStatusOrderByPublicatedAtDesc(NewsStatus.APROVADA, PageRequest.of(0, 12));

        model.addAttribute("newsList", newsList);

        return "public/home/index";
    }
}
