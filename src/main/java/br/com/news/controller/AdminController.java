package br.com.news.controller;

import br.com.news.dto.NewsRequest;
import br.com.news.dto.NewsResponse;
import br.com.news.entity.Author;
import br.com.news.entity.News;
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

import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.mapper.AuthorMapper;
import br.com.news.service.AuthorService;

import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private static final Set<NewsStatus> AUTHOR_EDITABLE_STATUSES = Set.of(NewsStatus.ESCRITA, NewsStatus.ARQUIVADA);

    private final NewsService newsService;
    private final NewsMapper newsMapper;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AdminController(NewsService newsService, NewsMapper newsMapper, AuthorService authorService, AuthorMapper authorMapper) {
        this.newsService = newsService;
        this.newsMapper = newsMapper;
        this.authorService = authorService;
        this.authorMapper = authorMapper;
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
            @AuthenticationPrincipal Author currentUser,
            Model model) {

        List<NewsResponse> newsList = resolveNewsList(currentUser, status, query);

        if (query != null && !query.isBlank()) {
            model.addAttribute("searchQuery", query);
        }

        model.addAttribute("newsList", newsList);
        model.addAttribute("activeStatus", status);

        return "admin/news/index";
    }

    @GetMapping("/admin/authors")
    public String adminAuthors(
            @RequestParam(value = "role", required = false) String role,
            @RequestParam(value = "q", required = false) String query,
            Model model) {

        List<AuthorResponse> authorsList;

        if (query != null && !query.isBlank()) {
            authorsList = authorService.search(query);
            model.addAttribute("searchQuery", query);
        } else if (role != null && !role.isBlank()) {
            boolean isEditor = "revisor".equalsIgnoreCase(role);
            authorsList = authorService.findByIsEditor(isEditor);
        } else {
            authorsList = authorService.findAll();
        }

        model.addAttribute("authorsList", authorsList);
        model.addAttribute("activeRole", role);

        return "admin/authors/index";
    }

    @GetMapping("/admin/authors/delete")
    public String deleteAuthor(@RequestParam("id") Long id) {
        authorService.delete(id);
        return "redirect:/admin/authors";
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

        if (!currentUser.isEditor()) {
            request.setStatus(sanitizeStatusForAuthor(request.getStatus()));
        }

        if (bindingResult.hasErrors()) {
            return "admin/news/form/index";
        }

        try {
            newsService.create(request);
            return "redirect:/admin/news";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar notícia: " + e.getMessage());
            return "admin/news/form/index";
        }
    }

    @GetMapping("/admin/news/update")
    public String updateForm(@RequestParam("id") Long id, Model model, @AuthenticationPrincipal Author currentUser) {
        try {
            News news = newsService.findEntityById(id);

            if (!canEditNews(currentUser, news)) {
                return "redirect:/admin/news";
            }

            NewsRequest newsRequest = newsMapper.toRequest(newsMapper.toResponse(news));
            model.addAttribute("news", newsRequest);
            model.addAttribute("newsId", id);
            return "admin/news/form/index";
        } catch (Exception e) {
            return "redirect:/admin/news";
        }
    }

    @PostMapping("/admin/news/update")
    public String updateNews(@RequestParam("id") Long id, @Valid @ModelAttribute("news") NewsRequest request,
            BindingResult bindingResult, Model model, @AuthenticationPrincipal Author currentUser) {

        News existingNews;
        try {
            existingNews = newsService.findEntityById(id);
        } catch (Exception e) {
            model.addAttribute("newsId", id);
            model.addAttribute("error", "Erro ao atualizar notícia: Notícia não encontrada.");
            return "admin/news/form/index";
        }

        if (!canEditNews(currentUser, existingNews)) {
            return "redirect:/admin/news";
        }

        request.setAuthorId(existingNews.getAuthor().getId());

        if (isStatusChangeToReviewAction(request.getStatus()) && !currentUser.isEditor()) {
            return "redirect:/admin/news";
        }

        if (!currentUser.isEditor()) {
            request.setStatus(sanitizeStatusForAuthor(request.getStatus()));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("newsId", id);
            return "admin/news/form/index";
        }

        try {
            newsService.update(id, request);
            return "redirect:/admin/news";
        } catch (Exception e) {
            model.addAttribute("newsId", id);
            model.addAttribute("error", "Erro ao atualizar notícia: " + e.getMessage());
            return "admin/news/form/index";
        }
    }

    @GetMapping("/admin/news/delete")
    public String deleteNews(@RequestParam("id") Long id) {
        newsService.delete(id);
        return "redirect:/admin/news";
    }

    @GetMapping("/admin/authors/create")
    public String createAuthorForm(Model model) {
        AuthorRequest request = new AuthorRequest();
        request.setNews(new java.util.ArrayList<>());
        model.addAttribute("author", request);
        model.addAttribute("isAuthorForm", true);
        return "admin/authors/form/index";
    }

    @PostMapping("/admin/authors/create")
    public String createAuthor(@Valid @ModelAttribute("author") AuthorRequest request, BindingResult bindingResult, Model model) {
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            bindingResult.rejectValue("password", "NotBlank", "A senha é obrigatória");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("isAuthorForm", true);
            return "admin/authors/form/index";
        }

        try {
            authorService.create(request);
            return "redirect:/admin/authors";
        } catch (Exception e) {
            model.addAttribute("isAuthorForm", true);
            model.addAttribute("error", "Erro ao criar autor: " + e.getMessage());
            return "admin/authors/form/index";
        }
    }

    @GetMapping("/admin/authors/update")
    public String updateAuthorForm(@RequestParam("id") Long id, Model model) {
        try {
            AuthorResponse response = authorService.findById(id);
            AuthorRequest request = authorMapper.toRequest(response);
            model.addAttribute("author", request);
            model.addAttribute("authorId", id);
            model.addAttribute("isAuthorForm", true);
            return "admin/authors/form/index";
        } catch (Exception e) {
            return "redirect:/admin/authors";
        }
    }

    @PostMapping("/admin/authors/update")
    public String updateAuthor(@RequestParam("id") Long id, @Valid @ModelAttribute("author") AuthorRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            boolean hasOtherErrors = bindingResult.getFieldErrors().stream()
                    .anyMatch(err -> !err.getField().equals("password"));

            if (hasOtherErrors || (request.getPassword() != null && !request.getPassword().trim().isEmpty() && bindingResult.hasFieldErrors("password"))) {
                model.addAttribute("authorId", id);
                model.addAttribute("isAuthorForm", true);
                return "admin/authors/form/index";
            }
        }

        try {
            authorService.update(id, request);
            return "redirect:/admin/authors";
        } catch (Exception e) {
            model.addAttribute("authorId", id);
            model.addAttribute("isAuthorForm", true);
            model.addAttribute("error", "Erro ao atualizar autor: " + e.getMessage());
            return "admin/authors/form/index";
        }
    }

    private List<NewsResponse> resolveNewsList(Author currentUser, String status, String query) {
        if (currentUser.isEditor()) {
            return resolveNewsListForEditor(status, query);
        }
        return resolveNewsListForAuthor(currentUser.getId(), status, query);
    }

    private List<NewsResponse> resolveNewsListForEditor(String status, String query) {
        if (query != null && !query.isBlank()) {
            return newsService.search(query);
        }
        if (status != null && !status.isBlank()) {
            return newsService.findByStatus(NewsStatus.valueOf(status.toUpperCase()));
        }
        return newsService.findAllOrderByUpdatedAtDesc();
    }

    private List<NewsResponse> resolveNewsListForAuthor(Long authorId, String status, String query) {
        if (query != null && !query.isBlank()) {
            return newsService.searchByAuthor(authorId, query);
        }
        if (status != null && !status.isBlank()) {
            return newsService.findByAuthorAndStatus(authorId, NewsStatus.valueOf(status.toUpperCase()));
        }
        return newsService.findAllByAuthorOrderByUpdatedAtDesc(authorId);
    }

    private boolean canEditNews(Author currentUser, News news) {
        if (currentUser.isEditor()) {
            return true;
        }
        if (!news.getAuthor().getId().equals(currentUser.getId())) {
            return false;
        }
        return AUTHOR_EDITABLE_STATUSES.contains(news.getStatus());
    }

    private boolean isStatusChangeToReviewAction(NewsStatus status) {
        return status == NewsStatus.APROVADA || status == NewsStatus.REJEITADA;
    }

    private NewsStatus sanitizeStatusForAuthor(NewsStatus status) {
        if (status == null || !AUTHOR_EDITABLE_STATUSES.contains(status)) {
            return NewsStatus.ESCRITA;
        }
        return status;
    }
}
