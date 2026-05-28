package br.com.news.dto;

import br.com.news.util.NewsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {

    private Long id;

    private String title;

    private String resume;
    
    private String content;

    private NewsStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
