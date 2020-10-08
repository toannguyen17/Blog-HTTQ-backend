package com.httq.app.api.v1;

import com.httq.dto.BaseResponse;
import com.httq.dto.user.UserDataDTO;
import com.httq.exception.CustomException;
import com.httq.model.User;
import com.httq.services.user.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1")
@Api(tags = "api/v1")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("signin")
	public ResponseEntity<BaseResponse<String>> signin(
		 @RequestParam String email,
		 @RequestParam String password)
	{
		BaseResponse<String> response = new BaseResponse<String>();
		try {
			String token = userService.signin(email, password);
			response.setData(token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (CustomException customException){
			response.setMsg(customException.getMessage());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}

	@PostMapping("signup")
	public ResponseEntity<BaseResponse<String>> signup(@RequestBody UserDataDTO user)
	{
		BaseResponse<String> response = new BaseResponse<String>();
		try {
			String token = userService.signup(modelMapper.map(user, User.class));
			response.setData(token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (CustomException customException){
			response.setMsg(customException.getMessage());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}

	@GetMapping("refresh")
	public ResponseEntity<BaseResponse<String>> refresh(HttpServletRequest req) {
		BaseResponse<String> response = new BaseResponse<String>();
		try {
			String token = userService.refresh(req.getRemoteUser());
			response.setData(token);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (CustomException customException){
			response.setMsg(customException.getMessage());
			return new ResponseEntity<>(response, customException.getHttpStatus());
		}
	}
}
