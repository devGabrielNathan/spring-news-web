package br.com.news.mapper;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import br.com.news.dto.NewsPatchRequest;
import br.com.news.dto.NewsRequest;
import br.com.news.dto.NewsResponse;
import br.com.news.entity.News;

@Component
public class NewsMapper {

    public News toEntity(NewsRequest request) {
        return new News(
                null,
                request.getTitle(),
                request.getResume(),
                request.getContent(),
                request.getStatus()
            );
    }

    public void updateEntityFromPatch(News news, NewsPatchRequest request) {
        if (Objects.nonNull(request.getTitle())) {
            news.setTitle(request.getTitle());
        }
        if (Objects.nonNull(request.getResume())) {
            news.setResume(request.getResume());
        }
        if (Objects.nonNull(request.getContent())) {
            news.setContent(request.getContent());
        }
        if (Objects.nonNull(request.getStatus())) {
            news.setStatus(request.getStatus());
        }
    }

    public NewsResponse toResponse(News news) {
        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getResume(),
                news.getContent(),
                news.getStatus()
            );
    }

    public List<NewsResponse> toResponseList(List<News> news) {
        return news.stream()
                .map(this::toResponse).toList();
    }
}
