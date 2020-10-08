package com.httq.repository;

import com.httq.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag,Long> {
    Iterable<Tag> findAllByContent(String content);
    void deleteByTag(String tag);
}
