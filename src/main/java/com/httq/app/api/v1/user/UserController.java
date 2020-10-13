package com.httq.app.api.v1.user;


import com.httq.dto.BaseResponse;
import com.httq.dto.user.ChangePWRequest;
import com.httq.dto.user.UserResponseDTO;
import com.httq.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

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
    public ResponseEntity<BaseResponse<String>> changePassword(@RequestBody ChangePWRequest request) {
        BaseResponse<String> response = new BaseResponse<>();
        if (request.getNewPassword().equals(request.getConfirmNewPassword())){
            if (userService.changePassword(request.getEmail(), request.getPassword(), request.getNewPassword())) {
                response.setMsg("Password changed.");
                response.setData("OK");
            } else {
                response.setMsg("Failed");
                response.setData("Failed");
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
