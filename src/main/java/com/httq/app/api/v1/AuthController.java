package com.httq.app.api.v1;

import com.httq.dto.AuthResponse;
import com.httq.dto.BaseResponse;
import com.httq.dto.user.UserDataDTO;
import com.httq.exception.CustomException;
import com.httq.model.User;
import com.httq.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("authenticate")
	public ResponseEntity<BaseResponse<AuthResponse>> authenticate(
		@RequestParam String email,
		@RequestParam String password) {
		BaseResponse<AuthResponse> response = new BaseResponse<>();
		try {
			response.setData(userService.authenticate(email, password));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException customException) {
			response.setMsg(customException.getMessage());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}

	@PostMapping("signup")
	public ResponseEntity<BaseResponse<AuthResponse>> signup(@RequestBody UserDataDTO user) {
		BaseResponse<AuthResponse> response = new BaseResponse<>();
		try {
			response.setData(userService.signup(modelMapper.map(user, User.class)));
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException customException) {
			response.setMsg(customException.getMessage());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}

	@CrossOrigin("*")
	@GetMapping("refresh")
	public ResponseEntity<BaseResponse<String>> refresh(HttpServletRequest req) {
		BaseResponse<String> response = new BaseResponse<>();
		try {
			String token = userService.refresh(req.getRemoteUser());
			response.setData(token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (CustomException customException) {
			response.setMsg(customException.getMessage());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}
}
