package br.com.news.controller;

import br.com.news.dto.NewsRequest;
import br.com.news.dto.NewsResponse;
import br.com.news.entity.Author;
import br.com.news.mapper.NewsMapper;
import br.com.news.service.NewsService;
import br.com.news.util.NewsStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @Autowired
    public AdminController(NewsService newsService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
    }

    @GetMapping("/admin")
    public String admin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.isAuthenticated()) {
            return "redirect:/login";
        }

        return "redirect:/admin/news";
    }

    @GetMapping("/admin/news")
    public String adminNews(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "q", required = false) String query,
            Model model) {

        List<NewsResponse> newsList;

        if (query != null && !query.isBlank()) {
            newsList = newsService.search(query);
            model.addAttribute("searchQuery", query);
        } else if (status != null && !status.isBlank()) {
            NewsStatus newsStatus = NewsStatus.valueOf(status.toUpperCase());
            newsList = newsService.findByStatus(newsStatus);
        } else {
            newsList = newsService.findAllOrderByUpdatedAtDesc();
        }

        model.addAttribute("newsList", newsList);
        model.addAttribute("activeStatus", status);

        return "admin/news/index";
    }

    @GetMapping("/admin/authors")
    public String adminAuthors(Model model) {
        return "admin/authors/index";
    }

    @GetMapping("/admin/news/create")
    public String createForm(Model model, @AuthenticationPrincipal Author currentUser) {
        NewsRequest request = new NewsRequest();
        if (currentUser != null) {
            request.setAuthorId(currentUser.getId());
        }

        model.addAttribute("news", request);
        return "admin/news/form/index";
    }

    @PostMapping("/admin/news/create")
    public String createNews(@Valid @ModelAttribute("news") NewsRequest request, BindingResult bindingResult,
            Model model, @AuthenticationPrincipal Author currentUser) {

        if (currentUser != null) {
            request.setAuthorId(currentUser.getId());
        }

        if (bindingResult.hasErrors()) {
            return "admin/news/form/index";
        }

        try {
            newsService.create(request);
            return "redirect:/admin/news?status=escrita";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar notícia: " + e.getMessage());
            return "admin/news/form/index";
        }
    }

    @GetMapping("/admin/news/update")
    public String updateForm(@RequestParam("id") Long id, Model model) {
        try {
            NewsResponse newsResponse = newsService.findById(id);
            NewsRequest newsRequest = newsMapper.toRequest(newsResponse);
            model.addAttribute("news", newsRequest);
            model.addAttribute("newsId", id);
            return "admin/news/form/index";
        } catch (Exception e) {
            return "redirect:/admin/news?status=escrita";
        }
    }

    @PostMapping("/admin/news/update")
    public String updateNews(@RequestParam("id") Long id, @Valid @ModelAttribute("news") NewsRequest request,
            BindingResult bindingResult, Model model, @AuthenticationPrincipal Author currentUser) {

        if (currentUser != null) {
            request.setAuthorId(currentUser.getId());
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("newsId", id);
            return "admin/news/form/index";
        }

        try {
            newsService.update(id, request);
            return "redirect:/admin/news?status=escrita";
        } catch (Exception e) {
            model.addAttribute("newsId", id);
            model.addAttribute("error", "Erro ao atualizar notícia: " + e.getMessage());
            return "admin/news/form/index";
        }
    }

    @GetMapping("/admin/news/delete")
    public String deleteNews(@RequestParam("id") Long id) {
        newsService.delete(id);
        return "redirect:/admin/news?status=escrita";
    }
}
