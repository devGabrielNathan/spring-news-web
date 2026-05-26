package br.com.news.dto;

import br.com.news.utils.SignatureStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    private SignatureStatus status;

    private boolean isEditor;
}
