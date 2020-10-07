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
	@ApiOperation(value = "${AuthController.signin}")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Something went wrong"),
		@ApiResponse(code = 422, message = "Invalid email/password supplied")
	})
	public ResponseEntity<BaseResponse<String>> signin(
		@ApiParam("Email") @RequestParam String email,
		@ApiParam("Password") @RequestParam String password)
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
	@ApiOperation(value = "${AuthController.signup}")
	@ApiResponses(value = {
		@ApiResponse(code = 400, message = "Something went wrong"),
		@ApiResponse(code = 403, message = "Access denied"),
		@ApiResponse(code = 422, message = "Email is already in use")
	})
	public ResponseEntity<BaseResponse<String>> signup(@ApiParam("Signup User") @RequestBody UserDataDTO user)
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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
