package br.com.news.mapper;

import br.com.news.dto.AuthorPatchRequest;
import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.entity.Author;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthorMapper {
    public Author toEntity(AuthorRequest request) {
        return new Author(
                null,
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getBirthDate(),
                request.getEducation(),
                request.getSignature(),
                request.isEditor());
    }

    public void updateEntityFromPatch(Author author, AuthorPatchRequest request) {
        if (Objects.nonNull(request.getName())) {
            author.setName(request.getName());
        }

        if (Objects.nonNull(request.getEmail())) {
            author.setEmail(request.getEmail());
        }

        if (Objects.nonNull(request.getBirthDate())) {
            author.setBirthDate(request.getBirthDate());
        }

        if (Objects.nonNull(request.getEducation())) {
            author.setEducation(request.getEducation());
        }

        if (Objects.nonNull(request.getSignature())) {
            author.setSignature(request.getSignature());
        }
        if (Objects.nonNull(request.getIsEditor())) {
            author.setEditor(request.getIsEditor());
        }

    }

    public AuthorResponse toResponse(Author author) {
        return new AuthorResponse(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getBirthDate(),
                author.getEducation(),
                author.getSignature(),
                author.isEditor());
    }

    public List<AuthorResponse> toResponseList(List<Author> authorEntities) {
        return authorEntities.stream().map(this::toResponse).toList();
    }
}
