package br.com.news.service;

import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.entity.Author;
import br.com.news.mapper.AuthorMapper;
import br.com.news.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorResponse> findAll() {
        List<Author> authorEntities = authorRepository.findAll();
        return authorMapper.toResponseList(authorEntities);
    }

    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return authorMapper.toResponse(author);
    }

    public AuthorResponse create(AuthorRequest request) {
        if (authorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("");
        }

        Author author = authorMapper.toEntity(request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    public AuthorResponse update(Long id, AuthorRequest request) {
        authorRepository.findById(id).orElseThrow();
        Author updatedAuthor = authorMapper.toEntity(request);
        updatedAuthor.setId(id);
        return authorMapper.toResponse(authorRepository.save(updatedAuthor));
    }

    public void delete(Long id) {
        authorRepository.findById(id).orElseThrow();
        authorRepository.deleteById(id);
    }
}
