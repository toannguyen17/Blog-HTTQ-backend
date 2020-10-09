package com.httq.repository;

import com.httq.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSearchRepository extends CrudRepository<User,Long> {
    Iterable<User> findAllByEmailContains(String key);
}
