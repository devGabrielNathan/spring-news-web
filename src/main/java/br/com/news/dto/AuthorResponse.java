package br.com.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private Long id;

    private String name;

    private String email;

    private LocalDate birthDate;

    private String education;

    private String signature;

    private boolean isEditor;

    private List<NewsResponse> news;
}