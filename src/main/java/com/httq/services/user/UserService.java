package com.httq.services.user;

import com.httq.dto.AuthResponse;
import com.httq.dto.user.UserResponseDTO;
import com.httq.exception.CustomException;
import com.httq.model.User;
import com.httq.services.IGeneralService;
import org.springframework.security.authentication.LockedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService extends IGeneralService<User> {
	Optional<User> findByEmail(String email);

	void resetFailAttempts(String email);

	void updateFailAttempts(String email) throws LockedException;

	AuthResponse authenticate(String email, String password) throws CustomException;

	AuthResponse signup(User user) throws CustomException;

	User search(String email);

	UserResponseDTO getInfoById(Long id);

	UserResponseDTO myInfo(HttpServletRequest req);

	String refresh(String email) throws CustomException;
}
