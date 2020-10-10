package com.httq.repository;

import com.httq.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	boolean existsBySeo(String seo);
	Optional<Post> findBySeo(String seo);
}
