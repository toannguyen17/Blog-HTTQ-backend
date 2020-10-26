package com.httq.app.api.v1.post;

import com.httq.app.helper.Helpers;
import com.httq.app.helper.Str;
import com.httq.dto.BaseResponse;
import com.httq.dto.post.PostAuth;
import com.httq.dto.post.PostFormData;
import com.httq.dto.post.PostViewData;
import com.httq.dto.post.reViewPost;
import com.httq.model.*;
import com.httq.services.post.PostService;
import com.httq.services.tag.TagService;
import com.httq.services.userInfo.UserInfoService;
import com.httq.system.auth.Auth;
import org.hashids.Hashids;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	private Environment environment;

	@Autowired
	private PostService postService;

	@Autowired
	private TagService tagService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private Hashids hashids;

	@Autowired
	private Str str;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Auth auth;

	@Autowired
	private Helpers helpers;

	/*
		Error status:
		0: Tạo thành công
		1: Chưa đăng nhập
	 */
	@PostMapping
	public ResponseEntity<BaseResponse<reViewPost>> createPost(@RequestBody PostFormData formData) {
		BaseResponse<reViewPost> response = new BaseResponse<>();
		User user = auth.user();

		if (user != null) {
			String seo = str.slug(formData.getTitle());
			Post post = modelMapper.map(formData, Post.class);

			post.setUser(user);

			if (postService.existsBySeo(seo)) {
				seo += "-" + hashids.encode(new Date().getTime());
			}
			post.setSeo(seo);

			if (formData.getTags().size() > 0) {
				List<Tag> tagList = new ArrayList<>();

				formData.getTags().forEach(tag -> {
					Optional<Tag> optionalTag = tagService.findByTag(tag);
					if (optionalTag.isPresent()) {
						tagList.add(optionalTag.get());
					} else {
						Tag tag1 = new Tag(tag);
						tagService.save(tag1);
						tagList.add(tag1);
					}
				});
				post.setTags(tagList);
			}

			postService.save(post);
			response.setMsg("Create successfully.");

			reViewPost reViewPost = modelMapper.map(post, reViewPost.class);
			reViewPost.setDescription(helpers.cutText(post.getContentPlainText(), 0, 175));
			response.setData(reViewPost);
		} else {
			response.setMsg("Only for login users.");
			response.setStatus(1);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*
		Error status:
		0: Xóa thành công
		1: Không có quyền
		2: Bài viết không tồn tại
	 */
	@PutMapping
	public ResponseEntity<BaseResponse<reViewPost>> updatePost(@RequestBody PostFormData formData) {
		BaseResponse<reViewPost> response = new BaseResponse<>();

		Optional<Post> optionalPost = postService.findBySeo(formData.getSeo());
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			User user = auth.user();

			if (user.getRoles().contains(Role.ROLE_ADMIN) ||
				user.getId().equals(post.getUser().getId())) {

				if (!post.getTitle().equals(formData.getTitle())) {
					String seo = str.slug(formData.getTitle());
					Optional<Post> optionalPostCheck = postService.findBySeo(seo);

					if (optionalPostCheck.isPresent() && !optionalPostCheck.get().getId().equals(post.getId())) {
						seo += "-" + hashids.encode(new Date().getTime());
						post.setSeo(seo);
					}

					post.setTitle(formData.getTitle());
				}

				post.setThumbnail(formData.getThumbnail());
				post.setSubTitle(formData.getSubTitle());
				post.setContent(formData.getContent());
				post.setContentPlainText(formData.getContentPlainText());
				post.setStatus(formData.getStatus());

				if (formData.getTags().size() > 0) {
					List<Tag> tagList = new ArrayList<>();

					formData.getTags().forEach(tag -> {
						Optional<Tag> optionalTag = tagService.findByTag(tag);
						if (optionalTag.isPresent()) {
							tagList.add(optionalTag.get());
						} else {
							Tag tag1 = new Tag(tag);
							tagService.save(tag1);
							tagList.add(tag1);
						}
					});
					post.setTags(tagList);
				}

				postService.save(post);

				response.setMsg("Update successfully.");
				reViewPost reViewPost = modelMapper.map(post, reViewPost.class);
				reViewPost.setDescription(helpers.cutText(post.getContentPlainText(), 0, 175));
				response.setData(reViewPost);
			} else {
				response.setMsg("You do not have permission to access.");
				response.setStatus(1);
			}
		} else {
			response.setMsg("Diary does not exist.");
			response.setStatus(2);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("{seo}")
	public ResponseEntity<BaseResponse<PostViewData>> getPostBySeo(@PathVariable String seo) {
		Optional<Post> optionalPost = postService.findBySeo(seo);

		User user = auth.user();

		BaseResponse<PostViewData> response = new BaseResponse<>();
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			if ((user != null && user.getRoles().contains(Role.ROLE_ADMIN)) ||
				post.getStatus() != PostStatusList.PRIVATE
			) {
				long view = post.getView();
				++view;
				post.setView(view);
				post.setViewTrend(view);
				postService.save(post);
				PostViewData postView = modelMapper.map(post, PostViewData.class);
				PostAuth postAuth = modelMapper.map(post.getUser(), PostAuth.class);

				Optional<UserInfo> optionalUserInfo = userInfoService.findByUser(post.getUser());
				if (optionalUserInfo.isPresent()) {
					UserInfo userInfo = optionalUserInfo.get();
					postAuth.setFirstName(userInfo.getFirstName());
					postAuth.setLastName(userInfo.getLastName());
					if (userInfo.getAvatar() != null){
						Image image = userInfo.getAvatar();
						postAuth.setThumbnail(environment.getProperty("app-url") + "/images/" + image.getName() + "." + image.getFormat());
					}
				}
				postView.setAuth(postAuth);
				response.setData(postView);
			}else{
				response.setStatus(403);
			}
		} else {
			response.setStatus(404);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*
		Error status:
		0: Xóa thành công
		1: Không có quyền
		2: Bài viết không tồn tại
	 */
	@DeleteMapping("{seo}")
	public ResponseEntity<BaseResponse<String>> doDeletePostBySeo(@PathVariable String seo) {
		BaseResponse<String> response = new BaseResponse<>();
		Optional<Post> optionalPost = postService.findBySeo(seo);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			User user = auth.user();
			if (user.getRoles().contains(Role.ROLE_ADMIN) ||
				user.getId().equals(post.getUser().getId())) {
				postService.deleteById(optionalPost.get().getId());
				response.setMsg("Deleted successfully.");
			} else {
				response.setMsg("You do not have permission to access.");
				response.setStatus(1);
			}
		} else {
			response.setMsg("Diary does not exist.");
			response.setStatus(2);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
