package br.com.news.entity;
import br.com.news.utils.SignatureStatus;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private LocalDate birthDate;
    private String education;
    private String email;
    private String signature;
    private SignatureStatus status;
}
