package com.httq.repository;

import com.httq.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoSearchRepository extends CrudRepository<UserInfo, Long> {
    Iterable<UserInfo> findAllByFirstNameContainsOrLastNameContains(String key1, String key2);
}
