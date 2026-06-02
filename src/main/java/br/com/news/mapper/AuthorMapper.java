package br.com.news.mapper;

import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorMapper {

    private final NewsMapper newsMapper;

    @Autowired
    public AuthorMapper(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    public Author toEntity(AuthorRequest request) {
        Author author = new Author(
                null,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getBirthDate(),
                request.getEducation(),
                request.getSignature(),
                request.isEditor(),
                null
        );

        if (request.getNews() != null) {
            author.setNews(newsMapper.toEntityList(request.getNews(), author));
        }

        return author;
    }

    public AuthorResponse toResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getBirthDate(),
                author.getEducation(),
                author.getSignature(),
                author.isEditor(),
                newsMapper.toResponseList(author.getNews())
        );
    }

    public List<AuthorResponse> toResponseList(List<Author> authorEntities) {
        return authorEntities.stream().map(this::toResponse).toList();
    }
}