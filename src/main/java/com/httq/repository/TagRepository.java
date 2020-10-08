package com.httq.repository;

import com.httq.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
	Iterable<Tag> findAllByTagContains(String tag);

	Optional<Tag> findByTag(String tag);

	Integer countAllByTagContains(String tag);

	void deleteByTag(String tag);
}
