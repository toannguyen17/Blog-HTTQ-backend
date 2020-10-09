package com.httq.app.api.v1.post;

import com.httq.app.helper.Str;
import com.httq.dto.post.PostFormData;
import com.httq.model.Post;
import com.httq.model.PostStatusList;
import com.httq.services.post.PostService;
import org.hashids.Hashids;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private Hashids hashids;

	@Autowired
	private Str str;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("{seo}")
	public String getPostBySeo(){
		return "";
	}

	@PostMapping
	public String createPost(@RequestBody PostFormData formData){
		String seo = str.slug(formData.getTitle());
		Post post = modelMapper.map(formData, Post.class);
		post.setStatus(PostStatusList.valueOf(formData.getStatus()));

		if (postService.existsBySeo(seo)){
			seo += "-" + hashids.encode(new Date().getTime());
		}
		post.setSeo(seo);
		postService.save(post);
		return "";
	}

	@PutMapping()
	public String updatePost(Post post){

		return "";
	}

}
