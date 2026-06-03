package br.com.news.mapper;

import java.util.List;

import br.com.news.entity.Author;
import org.springframework.stereotype.Component;

import br.com.news.dto.NewsRequest;
import br.com.news.dto.NewsResponse;
import br.com.news.entity.News;

@Component
public class NewsMapper {

    public News toEntity(NewsRequest request, Author author) {
        News news = new News();

        news.setTitle(request.getTitle());
        news.setResume(request.getResume());
        news.setContent(request.getContent());
        news.setAuthor(author);

        if (request.getStatus() != null) {
            news.setStatus(request.getStatus());
        }
        return news;
    }

    public List<News> toEntityList(List<NewsRequest> news, Author author) {
        return news.stream()
                .map(request -> toEntity(request, author)).toList();
    }

    public NewsResponse toResponse(News news) {
        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getResume(),
                news.getContent(),
                news.getStatus(),
                news.getCreatedAt(),
                news.getUpdatedAt(),
                news.getPublicatedAt(),
                news.getAuthor().getId());
    }

    public List<NewsResponse> toResponseList(List<News> news) {
        return news.stream()
                .map(this::toResponse).toList();
    }

    public NewsRequest toRequest(NewsResponse response) {
        return new NewsRequest(
                response.getTitle(),
                response.getResume(),
                response.getContent(),
                response.getStatus(),
                response.getAuthor());
    }
}
