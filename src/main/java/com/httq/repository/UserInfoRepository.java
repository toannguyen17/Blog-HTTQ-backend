package com.httq.repository;

import com.httq.model.User;
import com.httq.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	Optional<UserInfo> findByUser(User user);

	void deleteByUser(User user);
}
