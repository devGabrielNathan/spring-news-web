package br.com.news.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.news.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

}
