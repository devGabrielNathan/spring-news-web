package br.com.news.dto;

import br.com.news.util.NewsStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String resume;

    @NotBlank
    private String content;

    private NewsStatus status;

    @NotNull
    private Long authorId;
}
