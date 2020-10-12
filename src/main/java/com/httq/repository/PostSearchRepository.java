package com.httq.repository;

import com.httq.model.Post;
import com.httq.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface PostSearchRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByTitleContainsOrSubTitleContainsOrContentPlainTextContainsOrTagsContains(String key1, String key2, String key3, Tag tag);
    Iterable<Post> findAllByTitleContainsOrSubTitleContainsOrContentPlainTextContains(String key1, String key2, String key3);

}
