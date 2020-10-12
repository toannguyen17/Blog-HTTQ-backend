package com.httq.services.post;

import com.httq.model.Post;
import com.httq.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	public PostServiceImpl(){
	}

	@Override
	public Iterable<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public void save(Post entity) {
		postRepository.save(entity);
	}

	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
	}

	@Override
	public boolean existsBySeo(String seo) {
		return postRepository.existsBySeo(seo);
	}

	@Override
	public Optional<Post> findBySeo(String seo) {
		return postRepository.findBySeo(seo);
	}

	@Override
	public Iterable<Post> lastPost() {
		return postRepository.findTop8ByOrderByIdDesc();
	}

	@Override
	public Iterable<Post> findTop10Trending() {
		return postRepository.findTop10Trending();
	}

	@Override
	public Iterable<Post> findTop21Trending() {
		return postRepository.findTop21Trending();
	}
}
