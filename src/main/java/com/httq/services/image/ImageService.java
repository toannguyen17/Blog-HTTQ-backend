package com.httq.services.image;

import com.httq.model.Image;
import com.httq.services.IGeneralService;

import java.util.Optional;

public interface ImageService extends IGeneralService<Image> {
	Optional<Image> findByName(String name);
}
