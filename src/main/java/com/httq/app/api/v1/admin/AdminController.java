package com.httq.app.api.v1.admin;


import com.httq.dto.BaseResponse;
import com.httq.dto.user.UserDetailDTO;
import com.httq.model.Role;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.services.admin.AdminService;
import com.httq.services.user.UserService;
import com.httq.services.userInfo.UserInfoService;
import com.httq.system.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    private Auth auth;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("users")
    public ResponseEntity<BaseResponse<Iterable<UserDetailDTO>>> getAllUsers() {
        BaseResponse<Iterable<UserDetailDTO>> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            Iterable<UserDetailDTO> users = adminService.findAllUser();
            if (users.iterator().hasNext()) {
                baseResponse.setStatus(20);
                baseResponse.setMsg("Users were found.");
            } else {
                baseResponse.setStatus(51);
                baseResponse.setMsg("Found no user.");
            }
            baseResponse.setData(users);
        } else {
            baseResponse.setStatus(43);
            baseResponse.setMsg("Access denied.");
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<BaseResponse<UserDetailDTO>> getUser(@PathVariable("id")Long id) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            UserDetailDTO userDetailDTO = adminService.findUserById(id);
            if (userDetailDTO != null) {
                baseResponse.setStatus(20);
                baseResponse.setMsg("User was found.");
            } else {
                baseResponse.setStatus(51);
                baseResponse.setMsg("Found no user.");
            }
            baseResponse.setData(userDetailDTO);
        } else {
            baseResponse.setStatus(43);
            baseResponse.setMsg("Access denied.");
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("users")
    public ResponseEntity<BaseResponse<UserDetailDTO>> createUser(@RequestBody UserDetailDTO userDetailDTO) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.createUser(userDetailDTO);
            baseResponse.setData(userDetailDTO);
            baseResponse.setStatus(20);
            baseResponse.setMsg("User was created.");
        } else {
            baseResponse.setMsg("Access denied.");
            baseResponse.setStatus(43);
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("users")
    public ResponseEntity<BaseResponse<UserDetailDTO>> updateUser(@RequestBody UserDetailDTO userDetailDTO) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.updateUser(userDetailDTO);
            baseResponse.setData(userDetailDTO);
            baseResponse.setStatus(20);
            baseResponse.setMsg("User was updated.");
        } else {
            baseResponse.setMsg("Access denied.");
            baseResponse.setStatus(43);
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("users")
    public ResponseEntity<BaseResponse<UserDetailDTO>> deleteUser(@RequestBody UserDetailDTO userDetailDTO) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.deleteUser(userDetailDTO);
            baseResponse.setData(userDetailDTO);
            baseResponse.setMsg("User was deleted.");
            baseResponse.setStatus(20);
        } else {
            baseResponse.setMsg("Access denied.");
            baseResponse.setStatus(43);
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("block-user")
    public ResponseEntity<BaseResponse<UserDetailDTO>> blockUser(@RequestBody UserDetailDTO userDetailDTO) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.blockUser(userDetailDTO);
            baseResponse.setData(userDetailDTO);
            baseResponse.setMsg("User was blocked.");
            baseResponse.setStatus(20);
        } else {
            baseResponse.setMsg("Access denied.");
            baseResponse.setStatus(43);
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable("id") Long id) {
        Optional<User> optionalUser = userService.findById(id);
        User user = optionalUser.orElse(null);
        Optional<UserInfo> optionalUserInfo = userInfoService.findByUser(user);

//        UserInfo userInfo = optionalUserInfo.orElse(null);

//        userInfoService.deleteById(userInfo.getId());

        userService.deleteById(user.getId());
//        adminService.deleteUserById(id);
        Map<String, Boolean> res = new HashMap<>();
        res.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(res);
    }
}
