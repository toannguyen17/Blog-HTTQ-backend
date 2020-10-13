package com.httq.app.api.v1.comment;

import com.httq.dto.BaseResponse;
import com.httq.model.Comment;
import com.httq.model.Role;
import com.httq.model.User;
import com.httq.services.comment.CommentService;
import com.httq.system.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private Auth auth;

	@PostMapping("add")
	public ResponseEntity<BaseResponse<String>> addComment(){
		BaseResponse<String> response = new BaseResponse<>();
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
}
