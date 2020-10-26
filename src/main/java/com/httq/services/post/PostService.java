package com.httq.services.post;

import com.httq.model.Post;
import com.httq.model.PostStatusList;
import com.httq.model.Tag;
import com.httq.model.User;
import com.httq.services.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService extends IGeneralService<Post> {
	boolean existsBySeo(String seo);

	Optional<Post> findBySeo(String seo);

	Iterable<Post> lastPost(PostStatusList status);

	Iterable<Post> findTopTrending(Integer status, Integer limit);

	Iterable<Post> findTop21Trending();

	Page<Post> searchByUser(User user, Pageable pageable);

	Page<Post> findAllByTagsIn(List<Tag> tags, Pageable pageable);

	Page<Post> searchByUserAndKey(User user, String key, Pageable pageable);
}
