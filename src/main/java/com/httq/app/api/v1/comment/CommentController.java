package com.httq.app.api.v1.comment;

import com.httq.dto.BaseResponse;
import com.httq.dto.comment.CommentCreate;
import com.httq.dto.comment.CommentDTO;
import com.httq.dto.comment.CommentPage;
import com.httq.dto.comment.CommentPageForm;
import com.httq.dto.post.PostAuth;
import com.httq.dto.post.PostFindByUser;
import com.httq.dto.post.PostPageProfile;
import com.httq.model.*;
import com.httq.services.comment.CommentService;
import com.httq.services.post.PostService;
import com.httq.services.userInfo.UserInfoService;
import com.httq.system.auth.Auth;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

	@Autowired
	private Environment environment;

	@Autowired
	private CommentService commentService;

	@Autowired
	private PostService postService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private Auth auth;

	@Autowired
	private ModelMapper modelMapper;

	private Integer size = 10;

	@PostMapping
	public ResponseEntity<BaseResponse<CommentDTO>> addComment(@RequestBody CommentCreate commentCreate){
		BaseResponse<CommentDTO> response = new BaseResponse<>();

		Optional<Post> optionalPost = postService.findById(commentCreate.getPost());

		if (optionalPost.isPresent()){
			User user = auth.user();
			Post post = optionalPost.get();

			Comment comment = new Comment();
			comment.setPost(post);
			comment.setUser(user);
			comment.setContent(commentCreate.getComment());

			commentService.save(comment);

			CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

			PostAuth postAuth = modelMapper.map(user, PostAuth.class);

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
			commentDTO.setAuth(postAuth);
			commentDTO.setLike(0L);
			response.setData(commentDTO);
		}else{
			response.setStatus(1);
			response.setMsg("Diary does not exist.");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*
		Error status:
		0: Xóa thành công
		1: Không có quyền
		2: Comment không tồn tại
	 */
	@DeleteMapping("delete/{id}")
	public ResponseEntity<BaseResponse<String>> deleteComment(@PathVariable("id") Long id){
		BaseResponse<String> response = new BaseResponse<>();
		 Optional<Comment> optionalComment = commentService.findById(id);

		 // Nguời dùng đăng nhập
		 User user = auth.user();

		if (optionalComment.isPresent()){
			Comment comment = optionalComment.get();
			User userComment = comment.getUser();
			if (
				user.getRoles().contains(Role.ROLE_ADMIN) ||
				user.getId().equals(userComment.getId())
			){
				commentService.deleteById(id);
				response.setMsg("Delete successfully.");
				response.setStatus(0);
			}else{
				response.setMsg("You do not have permission to access.");
				response.setStatus(1);
			}
		}else{
			response.setMsg("Comment does not exist.");
			response.setStatus(2);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get comment page
	@PostMapping("page")
	public ResponseEntity<BaseResponse<CommentPage>> search(@RequestBody CommentPageForm commentPageForm) {
		BaseResponse<CommentPage> response = new BaseResponse<>();

		Optional<Post> optionalPost = postService.findById(commentPageForm.getId());

		if (optionalPost.isPresent()){
			Post post = optionalPost.get();

			Integer page = commentPageForm.getPage();
			Pageable pageable = null;

			if (page == null || page < 1) {
				pageable = PageRequest.of(0, size, Sort.by("id").descending());
			} else {
				--page;
				pageable = PageRequest.of(page, size, Sort.by("id").descending());
			}

			Page<Comment> commentPage = commentService.listComment(post, pageable);

			CommentPage pageC = modelMapper.map(commentPage, CommentPage.class);

			List<CommentDTO> commentDTOList = commentPage.stream().map(comment -> {
				CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);

				User user = comment.getUser();

				PostAuth postAuth = modelMapper.map(user, PostAuth.class);

				Optional<UserInfo> optionalUserInfo = userInfoService.findByUser(user);
				if (optionalUserInfo.isPresent()) {
					UserInfo userInfo = optionalUserInfo.get();
					postAuth.setFirstName(userInfo.getFirstName());
					postAuth.setLastName(userInfo.getLastName());
					if (userInfo.getAvatar() != null){
						Image image = userInfo.getAvatar();
						postAuth.setThumbnail(environment.getProperty("app-url") + "/images/" + image.getName() + "." + image.getFormat());
					}
				}
				commentDTO.setAuth(postAuth);
				commentDTO.setLike(0L);

				return commentDTO;
			}).collect(Collectors.toList());

			pageC.setComments(commentDTOList);

			response.setData(pageC);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
