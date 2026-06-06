package br.com.news.service;

import java.util.List;

import br.com.news.entity.Author;
import br.com.news.repository.AuthorRepository;
import br.com.news.util.NewsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.news.dto.NewsRequest;
import br.com.news.dto.NewsResponse;
import br.com.news.entity.News;
import br.com.news.mapper.NewsMapper;
import br.com.news.repository.NewsRepository;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsService(NewsRepository newsRepository, AuthorRepository authorRepository, NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.newsMapper = newsMapper;
    }

    public List<NewsResponse> findAll() {
        return newsMapper.toResponseList(newsRepository.findAll());
    }

    public List<NewsResponse> findAllOrderByUpdatedAtDesc() {
        return newsMapper.toResponseList(newsRepository.findAllByOrderByUpdatedAtDesc());
    }

    public List<NewsResponse> findByStatus(NewsStatus status) {
        return newsMapper.toResponseList(newsRepository.findByStatusOrderByUpdatedAtDesc(status));
    }

    public List<NewsResponse> search(String query) {
        return newsMapper.toResponseList(newsRepository.searchByTitleOrResume(query.trim()));
    }

    public NewsResponse findById(Long id) {
        return newsMapper.toResponse(newsRepository.findById(id).orElseThrow());
    }

    public NewsResponse create(NewsRequest request) {
        Author author = authorRepository.findById(request.getAuthorId()).orElseThrow();
        News news = newsMapper.toEntity(request, author);
        return newsMapper.toResponse(newsRepository.save(news));
    }

    public NewsResponse update(Long id, NewsRequest request) {
        newsRepository.findById(id).orElseThrow();
        Author author = authorRepository.findById(request.getAuthorId()).orElseThrow();
        News newsToUpdate = newsMapper.toEntity(request, author);
        newsToUpdate.setId(id);
        return newsMapper.toResponse(newsRepository.save(newsToUpdate));
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> findByStatusOrderByPublicatedAtDesc(NewsStatus status, Pageable pageable) {
        return newsRepository.findByStatusOrderByPublicatedAtDesc(status, pageable).getContent();
    }

    public List<News> searchByStatusAndTitleOrResume(NewsStatus status, String query) {
        return newsRepository.searchByStatusAndTitleOrResume(status, query.trim());
    }
}
