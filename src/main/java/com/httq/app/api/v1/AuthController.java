package com.httq.app.api.v1;

import com.httq.dto.AuthResponse;
import com.httq.dto.BaseResponse;
import com.httq.dto.user.UserDataDTO;
import com.httq.dto.user.UserRegisterForm;
import com.httq.exception.CustomException;
import com.httq.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private UserService userService;

	@PostMapping("authenticate")
	public ResponseEntity<BaseResponse<AuthResponse>> authenticate(@RequestBody UserDataDTO userData) {
		BaseResponse<AuthResponse> response = new BaseResponse<>();
		try {
			response.setData(userService.authenticate(userData.getEmail(), userData.getPassword()));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException customException) {
			response.setMsg(customException.getMessage());
			response.setStatus(customException.getHttpStatus().value());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}

	@PostMapping("signup")
	public ResponseEntity<BaseResponse<AuthResponse>> signup(@RequestBody UserRegisterForm form) {
		BaseResponse<AuthResponse> response = new BaseResponse<>();
		try {
			response.setData(userService.signup(form));
			response.setMsg("Sign Up Success.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException customException) {
			response.setMsg(customException.getMessage());
			response.setStatus(customException.getHttpStatus().value());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}

	@GetMapping("refresh")
	public ResponseEntity<BaseResponse<String>> refresh(HttpServletRequest req) {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			String token = userService.refresh(req.getRemoteUser());
			response.setData(token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException customException) {
			response.setMsg(customException.getMessage());
			response.setStatus(customException.getHttpStatus().value());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}
}
