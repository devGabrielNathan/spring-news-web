package br.com.news.controller;

import br.com.news.entity.News;
import br.com.news.repository.NewsRepository;
import br.com.news.util.NewsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PublicController {

    private final NewsRepository newsRepository;

    @Autowired
    public PublicController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("headTitle", "PostBlog");

        List<News> newsList = newsRepository.findNewsByStatusOrderByCreatedAtDesc(NewsStatus.APROVADA);
        model.addAttribute("newsList", newsList);

        return "public/home";
    }
}
