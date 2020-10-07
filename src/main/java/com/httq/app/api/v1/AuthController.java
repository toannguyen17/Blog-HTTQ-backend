package com.httq.app.api.v1;

import com.httq.dto.UserDataDTO;
import com.httq.dto.UserResponseDTO;
import com.httq.model.User;
import com.httq.services.user.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "api/v1/auth")
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/signin")
	@ApiOperation(value = "${AuthController.signin}")
	@ApiResponses(value = {//
	                       @ApiResponse(code = 400, message = "Something went wrong"), //
	                       @ApiResponse(code = 422, message = "Invalid email/password supplied")})
	public String login(//
	                    @ApiParam("Email") @RequestParam String email, //
	                    @ApiParam("Password") @RequestParam String password) {
		return userService.signin(email, password);
	}

	@PostMapping("/signup")
	@ApiOperation(value = "${AuthController.signup}")
	@ApiResponses(value = {//
	                       @ApiResponse(code = 400, message = "Something went wrong"), //
	                       @ApiResponse(code = 403, message = "Access denied"), //
	                       @ApiResponse(code = 422, message = "Email is already in use")})
	public String signup(@ApiParam("Signup User") @RequestBody UserDataDTO user) {
		return userService.signup(modelMapper.map(user, User.class));
	}

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	public String refresh(HttpServletRequest req) {
		return userService.refresh(req.getRemoteUser());
	}
}
