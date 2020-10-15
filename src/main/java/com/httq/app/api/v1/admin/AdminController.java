package com.httq.app.api.v1.admin;

import com.httq.dto.BaseResponse;
import com.httq.dto.user.ChangePWRequest;
import com.httq.dto.user.UserDetailDTO;
import com.httq.model.Role;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.services.admin.AdminService;
import com.httq.services.user.UserService;
import com.httq.services.userInfo.UserInfoService;
import com.httq.system.auth.Auth;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BaseResponse<UserDetailDTO>> getUser(@PathVariable("id") Long id) {
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

    @GetMapping("showUser/{id}")
    public ResponseEntity<BaseResponse<UserDetailDTO>> getUserById(@PathVariable("id")Long id) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)){
            UserDetailDTO userDetailDTO = adminService.findById(id);
            if (userDetailDTO != null){
                baseResponse.setStatus(0);
                baseResponse.setMsg("User was found.");
            } else {
                baseResponse.setStatus(1);
                baseResponse.setMsg("Found no user.");
            }
            baseResponse.setData(userDetailDTO);
        } else {
            baseResponse.setStatus(43);
            baseResponse.setMsg("Access denied.");
            baseResponse.setData(null);
        }
        return new  ResponseEntity<>(baseResponse, HttpStatus.OK);
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

    @PutMapping("updateUser/{id}")
    public ResponseEntity<BaseResponse<UserDetailDTO>> updateUser(@PathVariable("id")Long id, @RequestBody UserDetailDTO userDetailDTO) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        Optional<User> optionalUser = userService.findById(id);
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                Optional<UserInfo> userInfo = userInfoService.findByUser(user);

                UserInfo uf;
                if (userInfo.isPresent()) {
                    uf = userInfo.get();
                } else {
                    uf = new UserInfo();
                    uf.setUser(user);
                }

                uf.setFirstName(userDetailDTO.getFirstName());
                uf.setLastName(userDetailDTO.getLastName());
                uf.setGender(userDetailDTO.getGender());
                uf.setPhone(userDetailDTO.getPhone());
                uf.setAddress(userDetailDTO.getAddress());
                userInfoService.save(uf);

                user.setEmail(userDetailDTO.getEmail());
                user.setEnabled(userDetailDTO.isEnabled());
                user.setCreatedAt(userDetailDTO.getCreatedAt());
                user.setUpdatedAt(userDetailDTO.getUpdatedAt());
                user.setAttempts(userDetailDTO.getAttempts());
                user.setAccountNonExpired(userDetailDTO.isAccountNonExpired());
                user.setAccountNonLocked(userDetailDTO.isAccountNonLocked());
                user.setCredentialsNonExpired(userDetailDTO.isCredentialsNonExpired());
                userService.save(user);
            }
            baseResponse.setData(userDetailDTO);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<BaseResponse<UserDetailDTO>> deleteUser(@RequestParam("id") Long id) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.deleteUserById(id);
            baseResponse.setData(null);
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

    @PutMapping("unblock-user")
    public ResponseEntity<BaseResponse<UserDetailDTO>> unblockUser(@RequestBody UserDetailDTO userDetailDTO) {
        BaseResponse<UserDetailDTO> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.unblockUser(userDetailDTO);
            baseResponse.setData(userDetailDTO);
            baseResponse.setMsg("User was unblocked.");
            baseResponse.setStatus(20);
        } else {
            baseResponse.setMsg("Access denied.");
            baseResponse.setStatus(43);
            baseResponse.setData(null);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("resetpw")
    public ResponseEntity<BaseResponse<String>> changePassword(@RequestBody Long id) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        if (auth.user().getRoles().contains(Role.ROLE_ADMIN)) {
            adminService.resetPassword(id);
            baseResponse.setStatus(20);
            baseResponse.setMsg("Password was reset.");
            baseResponse.setData("Successfully.");
        } else {
            baseResponse.setData("Unsuccessfully.");
            baseResponse.setMsg("Access denied.");
            baseResponse.setStatus(43);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
