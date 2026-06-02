package br.com.news.controller;

import br.com.news.dto.AuthorResponse;
import br.com.news.dto.NewsResponse;
import br.com.news.service.AuthorService;
import br.com.news.service.NewsService;
import br.com.news.util.NewsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class NewsController {

    private final NewsService newsService;
    private final AuthorService authorService;

    @Autowired
    public NewsController(NewsService newsService, AuthorService authorService) {
        this.newsService = newsService;
        this.authorService = authorService;
    }

    @GetMapping("/news")
    public String newsList(Model model) {
        model.addAttribute("newsList", newsService.findByStatusOrderByPublicatedAtDesc(NewsStatus.APROVADA, PageRequest.of(0, 12)));

        return "public/news/index";
    }

    @GetMapping("/news/{id}")
    public String newsDetail(@PathVariable Long id, Model model) {
        NewsResponse news = newsService.findById(id);
        AuthorResponse author = authorService.findById(news.getAuthor());
        model.addAttribute("news", news);
        model.addAttribute("author", author);

        return "public/news/detail";
    }
}
