package com.httq.app.api.v1.user;


import com.httq.dto.BaseResponse;
import com.httq.dto.image.ImageDTO;
import com.httq.dto.user.ChangePWRequest;
import com.httq.dto.user.UserResponseDTO;
import com.httq.model.Image;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.services.image.ImageService;
import com.httq.services.user.UserService;
import com.httq.services.userInfo.UserInfoService;
import com.httq.system.auth.Auth;
import com.httq.system.filesystem.Storage;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	public static String path_avatar = "users/avatar";

	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private Storage storage;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private Auth auth;

	@Autowired
	private Hashids hashidsAvatar;

	@Autowired
	private ImageService imageService;

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

	@PutMapping("changePassword")
	public ResponseEntity<BaseResponse<String>> changePassword(@RequestBody ChangePWRequest changePWRequest) {
		BaseResponse<String> response = new BaseResponse<>();
		User user = auth.user();
		if (passwordEncoder.matches(changePWRequest.getPassword(), user.getPassword())) {
			if (!changePWRequest.getPassword().equals(changePWRequest.getNewPassword())) {
				response.setMsg("Password changed.");
				response.setData("OK");
				String pass = passwordEncoder.encode(changePWRequest.getNewPassword());
				user.setPassword(pass);
				userService.save(user);
			}
		} else {
			response.setMsg("Failed");
			response.setStatus(1);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("uploadAvatar")
	public ResponseEntity<BaseResponse<ImageDTO>> uploadAvatar(MultipartFile file) {
		BaseResponse<ImageDTO> response = new BaseResponse<>();
		if (file != null) {
			User user = auth.user();
			Optional<UserInfo> optionalUserInfo = userInfoService.findByUser(user);
			UserInfo userInfo = optionalUserInfo.orElse(new UserInfo(user));

			Long timestamp = new Date().getTime();

			String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
			String[] listDot = filename.split("\\.");

			String format = listDot[listDot.length - 1];
			Random rd = new Random();

			Image image = new Image(user);
			image.setName(hashidsAvatar.encode(timestamp + rd.nextInt(10000)));
			image.setFormat(format);
			image.setPath(UserController.path_avatar);
			imageService.save(image);

			image.setName(hashidsAvatar.encode(image.getId() + timestamp));
			imageService.save(image);

			userInfo.setAvatar(image);
			userInfoService.save(userInfo);

			storage.putFile(file, UserController.path_avatar, image.getName() + "." + image.getFormat());

			ImageDTO imageDTO = new ImageDTO();
			response.setData(imageDTO);
			imageDTO.setUrl(environment.getProperty("app-url") + "/images/" + image.getName() + "." + image.getFormat());
		} else {
			response.setStatus(1);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
