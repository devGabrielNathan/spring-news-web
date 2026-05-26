package br.com.news.entity;
import br.com.news.utils.SignatureStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 255)
    private String education;

    @Column(nullable = false, length = 255)
    private String signature;

    @Column(nullable = false)
    private SignatureStatus status;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isEditor;
}
