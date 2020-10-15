package com.httq.services.comment;

import com.httq.model.Comment;
import com.httq.model.Post;
import com.httq.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Iterable<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public void save(Comment entity) {
		commentRepository.save(entity);
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return commentRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		commentRepository.deleteById(id);
	}

	@Override
	public Page<Comment> listComment(Post post, Pageable pageable) {
		return commentRepository.findAllByPost(post, pageable);
	}
}
