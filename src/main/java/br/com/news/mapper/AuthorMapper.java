package br.com.news.mapper;

import br.com.news.dto.AuthorPatchRequest;
import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.entity.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthorMapper {
    public AuthorEntity toEntity(AuthorRequest authorRequest) {
        return new AuthorEntity(
                null,
                authorRequest.getName(),
                authorRequest.getEmail(),
                authorRequest.getPassword(),
                authorRequest.getBirthDate(),
                authorRequest.getEducation(),
                authorRequest.getSignature(),
                authorRequest.getStatus(),
                authorRequest.isEditor()
        );
    }

    public void updateEntityFromPatch(AuthorEntity authorEntity, AuthorPatchRequest authorPatchRequest) {
        if (Objects.nonNull(authorPatchRequest.getName())) {
            authorEntity.setName(authorPatchRequest.getName());
        }

        if (Objects.nonNull(authorPatchRequest.getEmail())) {
            authorEntity.setEmail(authorPatchRequest.getEmail());
        }

        if (Objects.nonNull(authorPatchRequest.getBirthDate())) {
            authorEntity.setBirthDate(authorPatchRequest.getBirthDate());
        }

        if (Objects.nonNull(authorPatchRequest.getEducation())) {
            authorEntity.setEducation(authorPatchRequest.getEducation());
        }

        if (Objects.nonNull(authorPatchRequest.getSignature())) {
            authorEntity.setSignature(authorPatchRequest.getSignature());
        }
        if (Objects.nonNull(authorPatchRequest.getStatus())) {
            authorEntity.setStatus(authorPatchRequest.getStatus());
        }
        if (Objects.nonNull(authorPatchRequest.getIsEditor())) {
            authorEntity.setEditor(authorPatchRequest.getIsEditor());
        }

    }

    public AuthorResponse toResponse(AuthorEntity authorEntity) {
        return new AuthorResponse(
                authorEntity.getId(),
                authorEntity.getName(),
                authorEntity.getEmail(),
                authorEntity.getBirthDate(),
                authorEntity.getEducation(),
                authorEntity.getSignature(),
                authorEntity.getStatus(),
                authorEntity.isEditor()
        );
    }



    public List<AuthorResponse> toResponseList(List<AuthorEntity> authorEntities) {
        return authorEntities.stream().map(this::toResponse).toList();
    }
}
