package com.httq.app.api.v1;

import com.httq.dto.BaseResponse;
import com.httq.dto.home.HomeResponse;
import com.httq.services.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<BaseResponse<HomeResponse>> doGetHome(){
        BaseResponse<HomeResponse> response = new BaseResponse<>();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

