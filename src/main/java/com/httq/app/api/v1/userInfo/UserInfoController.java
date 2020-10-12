package com.httq.app.api.v1.userInfo;

import com.httq.dto.BaseResponse;
import com.httq.model.UserInfo;
import com.httq.services.userInfo.UserInfoService;
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
    public ResponseEntity<BaseResponse<UserInfo>> updateUserInfo (@PathVariable("id") Long id, @RequestBody UserInfo userInfo) {
        BaseResponse<UserInfo> baseResponse = new BaseResponse<>();
        Optional<UserInfo> optionalUserInfo = userInfoService.findById(id);
        if (!optionalUserInfo.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        baseResponse.setData(optionalUserInfo.get());
        userInfo.setFirstName(userInfo.getFirstName());
        userInfo.setLastName(userInfo.getLastName());
        userInfo.setPhone(userInfo.getPhone());
        userInfo.setGender(userInfo.getGender());
        userInfo.setAddress(userInfo.getAddress());

        userInfoService.save(userInfo);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
