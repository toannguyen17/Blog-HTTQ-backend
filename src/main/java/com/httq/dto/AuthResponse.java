package com.httq.dto;

import com.httq.dto.user.UserResponseDTO;

public class AuthResponse {
	private UserResponseDTO user;
	private String          token;

	public AuthResponse(){
	}

	public AuthResponse(UserResponseDTO user, String token){
		this.user = user;
		this.token = token;
	}

	public UserResponseDTO getUser() {
		return user;
	}

	public void setUser(UserResponseDTO user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
