package com.httq.system.auth;

import com.httq.model.User;
import com.httq.model.UserDetailCustom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Auth {
	public Auth(){
	}

	public User user() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof UserDetailCustom) {
				return ((UserDetailCustom) principal).getUserDetails();
			}
		}
		return null;
	}
}
