package com.httq.app.api.v1;

import com.httq.app.api.v1.user.UserController;
import com.httq.model.Image;
import com.httq.services.image.ImageService;
import com.httq.system.filesystem.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
public class ImagesController {
	@Autowired
	private Storage storage;

	@Autowired
	private ImageService imageService;

	@GetMapping("/images/{name}.{format}")
	public ResponseEntity<Resource> doGet(@PathVariable("name") String name, @PathVariable("format") String format) {
		Optional<Image> optionalImage = imageService.findByName(name);
		if (optionalImage.isPresent()) {
			Image image = optionalImage.get();
			if (image.getFormat().equals(format)) {
				String path_url = image.getPath() + "/" + image.getName() + "." + image.getFormat();
				String mimeType;
				Resource resource = storage.get(path_url);
				try {
					Path path = resource.getFile().toPath();
					mimeType = Files.probeContentType(path);
					return ResponseEntity.ok()
						.contentLength(resource.contentLength())
						.contentType(MediaType.parseMediaType(mimeType))
						.body(new InputStreamResource(resource.getInputStream()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
	}
}
