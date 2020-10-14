package com.httq.app.api.v1.profile;

import com.httq.model.Role;
import com.httq.model.User;
import com.httq.model.UserInfo;
import com.httq.services.profile.ProfileService;
import com.httq.system.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private Auth auth;

    @GetMapping("{id}")
    public ResponseEntity<Optional<UserInfo>> getProfile(@PathVariable("id") Long id) {

        User user = auth.user();
        if (user.getRoles().contains(Role.ROLE_ADMIN)){

        }
        Optional<UserInfo> userInfo = profileService.findById(id);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
