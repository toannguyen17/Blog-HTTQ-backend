package com.httq.app.api.v1.user;


import com.httq.dto.BaseResponse;
import com.httq.dto.user.ChangePWRequest;
import com.httq.dto.user.UserResponseDTO;
import com.httq.model.User;
import com.httq.services.user.UserService;
import com.httq.system.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Auth auth;

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
            if (changePWRequest.getPassword().equals(changePWRequest.getNewPassword())) {
            } else {
                response.setMsg("Password changed.");
                response.setData("OK");
                String pass = passwordEncoder.encode(changePWRequest.getPassword());
                user.setPassword(pass);
                userService.save(user);
            }
        } else {
            response.setMsg("Failed");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
