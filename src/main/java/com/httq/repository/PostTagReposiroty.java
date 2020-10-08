package com.httq.repository;

import com.httq.model.PostTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagReposiroty extends CrudRepository<PostTag,Long> {

}
