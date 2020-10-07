package com.httq.services.user;

import com.httq.model.User;
import com.httq.services.IGeneralService;
import org.springframework.security.authentication.LockedException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService extends IGeneralService<User> {
	Optional<User> findByEmail(String email);
	void resetFailAttempts(String email);
	void updateFailAttempts(String email) throws LockedException;

	String signin(String email, String password);
	String signup(User user);
	User search(String email);
	User whoami(HttpServletRequest req);
	String refresh(String email);
}
