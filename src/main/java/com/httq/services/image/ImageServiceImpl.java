package com.httq.services.image;

import com.httq.model.Image;
import com.httq.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;

	public ImageServiceImpl(){
	}

	@Override
	public Iterable<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public void save(Image entity) {
		imageRepository.save(entity);
	}

	@Override
	public Optional<Image> findById(Long id) {
		return imageRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		imageRepository.deleteById(id);
	}

	@Override
	public Optional<Image> findByName(String name) {
		return imageRepository.findByName(name);
	}
}
