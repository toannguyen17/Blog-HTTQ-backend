package com.httq.services.post;

import com.httq.model.Post;
import com.httq.services.IGeneralService;

import java.util.Optional;

public interface PostService extends IGeneralService<Post> {
	boolean existsBySeo(String seo);

	Optional<Post> findBySeo(String seo);

	Iterable<Post> lastPost();

	Iterable<Post> findTop10Trending();

	Iterable<Post> findTop21Trending();
}
