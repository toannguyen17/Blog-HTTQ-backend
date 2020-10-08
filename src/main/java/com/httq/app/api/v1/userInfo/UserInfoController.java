package com.httq.app.api.v1.userInfo;

import com.httq.model.UserInfo;
import com.httq.services.userinfo.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:4200")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/")
    public ResponseEntity<Iterable<UserInfo>> getUserInfo() {
        Iterable<UserInfo> userInfos = userInfoService.findAll();
        return new ResponseEntity<>(userInfos, HttpStatus.OK);
    }
    
}
