package com.httq.repository;

import com.httq.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsBySeo(String seo);

    Optional<Post> findBySeo(String seo);

    Iterable<Post> findTop8ByOrderByIdDesc();

    @Query(value = "select * from posts order by (view_trend / (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(created_at))) desc limit 10", nativeQuery = true)
    Iterable<Post> findTop10Trending();

    @Query(value = "select * from posts order by (view_trend / (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(created_at))) desc limit 21", nativeQuery = true)
    Iterable<Post> findTop21Trending();

}
