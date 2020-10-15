package com.httq.services.comment;

import com.httq.model.Comment;
import com.httq.model.Post;
import com.httq.services.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService extends IGeneralService<Comment> {
	Page<Comment> listComment(Post post, Pageable pageable);
}
