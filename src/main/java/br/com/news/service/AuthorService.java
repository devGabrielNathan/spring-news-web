package br.com.news.service;

import br.com.news.dto.AuthorPatchRequest;
import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.entity.AuthorEntity;
import br.com.news.mapper.AuthorMapper;
import br.com.news.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public AuthorResponse getAuthorById(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow();
        return authorMapper.toResponse(authorEntity);
    }

    public List<AuthorResponse> getAllAuthors() {
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        return authorMapper.toResponseList(authorEntities);
    }

    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        if (authorRepository.existsByEmail(authorRequest.getEmail())) {
            throw new RuntimeException("");
        }

        AuthorEntity authorEntity = authorMapper.toEntity(authorRequest);
        return authorMapper.toResponse(authorRepository.save(authorEntity));
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest authorRequest) {
        authorRepository.findById(id).orElseThrow();
        AuthorEntity updatedAuthor = authorMapper.toEntity(authorRequest);
        updatedAuthor.setId(id);
        return authorMapper.toResponse(authorRepository.save(updatedAuthor));
    }

    public AuthorResponse patchAuthor(Long id, AuthorPatchRequest authorPatchRequest) {
        AuthorEntity authorEntity = authorRepository.findById(id).orElseThrow();
        authorMapper.updateEntityFromPatch(authorEntity, authorPatchRequest);
        return authorMapper.toResponse(authorRepository.save(authorEntity));
    }

    public void deleteAuthor(Long id) {
        authorRepository.findById(id).orElseThrow();
        authorRepository.deleteById(id);
    }
}
