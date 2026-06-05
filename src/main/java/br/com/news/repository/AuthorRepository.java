package br.com.news.repository;

import br.com.news.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByEmail(String email);
    Optional<Author> findByEmail(String email);
    List<Author> findAllByOrderByNameAsc();
    List<Author> findByIsEditorOrderByNameAsc(boolean isEditor);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(a.email) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(a.signature) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "ORDER BY a.name ASC")
    List<Author> searchByNameOrEmailOrSignature(@Param("query") String query);
}
