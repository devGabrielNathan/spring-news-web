package br.com.news.service;

import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.entity.Author;
import br.com.news.mapper.AuthorMapper;
import br.com.news.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AuthorResponse> findAll() {
        List<Author> authorEntities = authorRepository.findAllByOrderByNameAsc();
        return authorMapper.toResponseList(authorEntities);
    }

    public List<AuthorResponse> search(String query) {
        List<Author> authorEntities = authorRepository.searchByNameOrEmailOrSignature(query.trim());
        return authorMapper.toResponseList(authorEntities);
    }

    public List<AuthorResponse> findByIsEditor(boolean isEditor) {
        List<Author> authorEntities = authorRepository.findByIsEditorOrderByNameAsc(isEditor);
        return authorMapper.toResponseList(authorEntities);
    }

    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return authorMapper.toResponse(author);
    }

    public AuthorResponse create(AuthorRequest request) {
        if (authorRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Author author = authorMapper.toEntity(request);
        author.setPassword(passwordEncoder.encode(request.getPassword()));
        return authorMapper.toResponse(authorRepository.save(author));
    }

    public AuthorResponse update(Long id, AuthorRequest request) {
        Author existingAuthor = authorRepository.findById(id).orElseThrow();
        Author updatedAuthor = authorMapper.toEntity(request);
        updatedAuthor.setId(id);
        
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            updatedAuthor.setPassword(existingAuthor.getPassword());
        } else {
            updatedAuthor.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
        return authorMapper.toResponse(authorRepository.save(updatedAuthor));
    }


    public void delete(Long id) {
        authorRepository.findById(id).orElseThrow();
        authorRepository.deleteById(id);
    }
}
