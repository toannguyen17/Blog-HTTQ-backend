package com.httq.app.api.v1.userInfo;

import com.httq.dto.BaseResponse;
import com.httq.dto.user.UserResponseDTO;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.services.user.UserService;
import com.httq.services.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private UserService userService;

    @GetMapping("/userInfo/{id}")
    public ResponseEntity<BaseResponse<UserInfo>> showUserInfo(@PathVariable("id") Long id) {
        BaseResponse<UserInfo> baseResponse = new BaseResponse<>();
        Optional<UserInfo> userInfo = userInfoService.findById(id);
        if (userInfo.isPresent()){
            baseResponse.setData(userInfo.get());
        } else {
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<BaseResponse<UserResponseDTO>> updateUserInfo (@PathVariable("id") Long id, @RequestBody UserInfo userInfo) {
        BaseResponse<UserResponseDTO> baseResponse = new BaseResponse<>();
        UserResponseDTO userResponseDTO = null;

        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            Optional<UserInfo> optionalUserInfo = userInfoService.findByUser(user);

            UserInfo uInfo;
            if (optionalUserInfo.isPresent()){
                uInfo = optionalUserInfo.get();
            }else {
                uInfo = new UserInfo();
                uInfo.setUser(user);
            }

            uInfo.setFirstName(userInfo.getFirstName());
            uInfo.setLastName(userInfo.getLastName());
            uInfo.setGender(userInfo.getGender());
            uInfo.setPhone(userInfo.getPhone());
            uInfo.setAddress(userInfo.getAddress());
            userInfoService.save(uInfo);

            userResponseDTO = userService.getInfo(user, uInfo);
        }
        baseResponse.setData(userResponseDTO);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
