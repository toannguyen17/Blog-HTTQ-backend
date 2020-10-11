package com.httq.app.api.v1.post;

import com.httq.app.helper.Str;
import com.httq.dto.BaseResponse;
import com.httq.dto.post.PostFormData;
import com.httq.dto.post.PostViewData;
import com.httq.model.Post;
import com.httq.model.Tag;
import com.httq.services.post.PostService;
import com.httq.services.tag.TagService;
import org.hashids.Hashids;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private TagService tagService;

    @Autowired
    private Hashids hashids;

    @Autowired
    private Str str;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public String createPost(@RequestBody PostFormData formData) {
        String seo = str.slug(formData.getTitle());
        Post post = modelMapper.map(formData, Post.class);

        if (postService.existsBySeo(seo)) {
            seo += "-" + hashids.encode(new Date().getTime());
        }
        post.setSeo(seo);

        if (formData.getTags().size() > 0){
            List<Tag> tagList = new ArrayList<>();

            formData.getTags().forEach(tag -> {
                Optional<Tag> optionalTag = tagService.findByTag(tag);
                if (optionalTag.isPresent()){
                    tagList.add(optionalTag.get());
                }else{
                    Tag tag1 = new Tag(tag);
                    tagService.save(tag1);
                    tagList.add(tag1);
                }
            });
            post.setTags(tagList);
        }

        postService.save(post);
        return "";
    }

    @PutMapping
    public String updatePost( @RequestBody PostFormData formData) {
        Optional<Post> optionalPost = postService.findBySeo(formData.getSeo());
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (!post.getTitle().equals(formData.getTitle())){
                String seo = str.slug(formData.getTitle());
                Optional<Post> optionalPostCheck = postService.findBySeo(seo);

                if (optionalPostCheck.isPresent() && !optionalPostCheck.get().getId().equals(post.getId())) {
                    seo += "-" + hashids.encode(new Date().getTime());
                    post.setSeo(seo);
                }

                post.setTitle(formData.getTitle());
            }

            post.setSubTitle(formData.getSubTitle());
            post.setContent(formData.getContent());
            post.setContentPlainText(formData.getContentPlainText());
            post.setStatus(formData.getStatus());

            if (formData.getTags().size() > 0){
                List<Tag> tagList = new ArrayList<>();

                formData.getTags().forEach(tag -> {
                    Optional<Tag> optionalTag = tagService.findByTag(tag);
                    if (optionalTag.isPresent()){
                        tagList.add(optionalTag.get());
                    }else{
                        Tag tag1 = new Tag(tag);
                        tagService.save(tag1);
                        tagList.add(tag1);
                    }
                });
                post.setTags(tagList);
            }

            postService.save(post);
        }

        return "";
    }

    @GetMapping("{seo}")
    public ResponseEntity<BaseResponse<PostViewData>> getPostBySeo(@PathVariable String seo) {
        Optional<Post> optionalPost = postService.findBySeo(seo);

        BaseResponse<PostViewData> response = new BaseResponse<>();
        if (optionalPost.isPresent()) {
            PostViewData postView = modelMapper.map(optionalPost.get(), PostViewData.class);
            response.setData(postView);
        } else {
            response.setStatus(404);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
