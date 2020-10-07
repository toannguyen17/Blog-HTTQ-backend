package com.httq.app.api.v1.user;


import com.httq.dto.BaseResponse;
import com.httq.dto.user.UserResponseDTO;
import com.httq.exception.CustomException;
import com.httq.model.User;
import com.httq.services.user.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "me")
	public ResponseEntity<BaseResponse<UserResponseDTO>> getMyInfo(HttpServletRequest req) {
		BaseResponse<UserResponseDTO> response = new BaseResponse<UserResponseDTO>();
		response.setData(userService.myInfo(req));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<BaseResponse<UserResponseDTO>> getUserById(@PathVariable("id") Long id) {
		BaseResponse<UserResponseDTO> response = new BaseResponse<UserResponseDTO>();
		response.setData(userService.getInfoById(id));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
