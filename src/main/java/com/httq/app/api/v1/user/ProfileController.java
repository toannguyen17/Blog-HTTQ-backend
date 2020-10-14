package com.httq.app.api.v1.user;


import com.httq.dto.BaseResponse;
import com.httq.dto.post.PostFindByUser;
import com.httq.dto.post.PostPageProfile;
import com.httq.model.Post;
import com.httq.model.User;
import com.httq.services.post.PostService;
import com.httq.services.user.UserService;
import com.httq.system.auth.Auth;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/post")
public class ProfileController {
	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private Auth auth;

	@Autowired
	private ModelMapper modelMapper;

	private Integer size = 1;

	@PostMapping("search")
	public ResponseEntity<BaseResponse<PostPageProfile>> search(@RequestBody PostFindByUser search) {
		BaseResponse<PostPageProfile> response = new BaseResponse<>();

		User user = auth.user();

		Integer page = search.getPage();
		Pageable pageable = null;

		if (page == null || page < 1) {
			pageable = PageRequest.of(0, size, Sort.by("id").descending());
		} else {
			--page;
			pageable = PageRequest.of(page, size, Sort.by("id").descending());
		}

		Page<Post> postPage;
		if (search.getKey() != null){
			postPage = postService.searchByUserAndKey(user, search.getKey(), pageable);
		}else{
			postPage = postService.searchByUser(user, pageable);
		}

		PostPageProfile postPageProfile = modelMapper.map(postPage, PostPageProfile.class);

		response.setData(postPageProfile);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
