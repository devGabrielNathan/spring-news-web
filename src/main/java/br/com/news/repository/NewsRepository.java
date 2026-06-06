package br.com.news.repository;

import br.com.news.util.NewsStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.news.entity.News;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findByStatusOrderByPublicatedAtDesc(NewsStatus status, Pageable pageable);

    List<News> findAllByOrderByUpdatedAtDesc();

    List<News> findByStatusOrderByUpdatedAtDesc(NewsStatus status);

    @Query("SELECT n FROM News n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(n.resume) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "ORDER BY n.updatedAt DESC")
    List<News> searchByTitleOrResume(@Param("query") String query);

    @Query("SELECT n FROM News n WHERE n.status = :status " +
            "AND (LOWER(n.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(n.resume) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "ORDER BY n.publicatedAt DESC")
    List<News> searchByStatusAndTitleOrResume(@Param("status") NewsStatus status, @Param("query") String query);
}
