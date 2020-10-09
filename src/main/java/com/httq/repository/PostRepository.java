package com.httq.repository;

import com.httq.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	boolean existsBySeo(String seo);
}
