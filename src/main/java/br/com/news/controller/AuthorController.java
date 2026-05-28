package br.com.news.controller;

import br.com.news.dto.AuthorPatchRequest;
import br.com.news.dto.AuthorRequest;
import br.com.news.dto.AuthorResponse;
import br.com.news.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
    
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> findAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable Long id,
            @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorResponse> patch(@PathVariable Long id,
            @RequestBody AuthorPatchRequest request) {
        return ResponseEntity.ok(authorService.patch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
