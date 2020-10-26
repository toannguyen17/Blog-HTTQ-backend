package com.httq.app.api.v1;

import com.httq.app.helper.Helpers;
import com.httq.dto.BaseResponse;
import com.httq.dto.post.reViewPost;
import com.httq.dto.home.HomeResponse;
import com.httq.model.Post;
import com.httq.model.PostStatusList;
import com.httq.services.post.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Helpers helpers;

    @GetMapping
    public ResponseEntity<BaseResponse<HomeResponse>> doGetHome() {
        HomeResponse homeResponse = new HomeResponse();

        // Trending
        Iterable<Post> topTrending = postService.findTopTrending(1, 21);
        List<reViewPost> homeTopTrend = new ArrayList<>();

        homeResponse.setTopTrend(homeTopTrend);

        topTrending.forEach(post -> {
            reViewPost reViewPost = modelMapper.map(post, reViewPost.class);
            reViewPost.setDescription(helpers.cutText(post.getContentPlainText(), 0, 175));
            homeTopTrend.add(reViewPost);
        });

        //Last Post
        Iterable<Post> lastPost = postService.lastPost(PostStatusList.PUBLIC);
        List<reViewPost> homeLastPostList = new ArrayList<>();

        homeResponse.setLastPost(homeLastPostList);

        lastPost.forEach(post -> {
            reViewPost homeLastPost = modelMapper.map(post, reViewPost.class);
            homeLastPost.setDescription(helpers.cutText(post.getContentPlainText(), 0, 100));
            homeLastPostList.add(homeLastPost);
        });

        BaseResponse<HomeResponse> response = new BaseResponse<>(homeResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

