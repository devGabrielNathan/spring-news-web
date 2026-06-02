package br.com.news.entity;

import br.com.news.util.NewsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "News")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, length = 500)
    private String resume;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = true)
    @ColumnDefault("'ESCRITA'")
    @Enumerated(EnumType.STRING)
    private NewsStatus status = NewsStatus.ESCRITA;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = true, columnDefinition = "TIMESTAMP")
    private LocalDateTime publicatedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "author_id")
    private Author author;
}
