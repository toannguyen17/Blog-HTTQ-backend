package com.httq.repository;

import com.httq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String username);

	Optional<User> findByEmail(String email);
}
