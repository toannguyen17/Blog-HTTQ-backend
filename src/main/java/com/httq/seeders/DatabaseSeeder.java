package com.httq.seeders;

import com.httq.model.Role;
import com.httq.model.User;
import com.httq.services.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class DatabaseSeeder {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	public DatabaseSeeder() {
	}

	@EventListener
	public void seed(ContextRefreshedEvent event) {
		this.seedCreateAdmin();
	}

	private void seedCreateAdmin() {
		Optional<User> u = userService.findByEmail("admin@admin.com");
		if (!u.isPresent()) {
			String password = bCryptPasswordEncoder.encode("123456");
			User   user     = new User();
			user.setEmail("admin@admin.com");
			user.setPassword(password);

			user.setRoles(Collections.singletonList(Role.ROLE_ADMIN));

			user.setEnabled(true);
			user.setAccountNonLocked(true);
			user.setAccountNonExpired(true);
			user.setCredentialsNonExpired(true);

			userService.save(user);
		}
	}
}
