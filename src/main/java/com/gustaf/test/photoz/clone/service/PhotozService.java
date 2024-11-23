package com.gustaf.test.photoz.clone.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gustaf.test.photoz.clone.model.Photo;
import com.gustaf.test.photoz.clone.repository.PhotozRepository;

// @Component Basically equivalent to @Service
@Service
public class PhotozService {
	
	private final PhotozRepository photozRepository;
	
	// dependency injection: injects objects into other objects => loose coupling
	public PhotozService(PhotozRepository photozRepository) {
		this.photozRepository = photozRepository;
	}

	/* private Map<String, Photo> db = new HashMap<>() {private static final long serialVersionUID = -3024050488562566095L;

	{
		put("1", new Photo());
	}}; */

	public Iterable<Photo> get() {
		
		return photozRepository.findAll();
	}

	public Photo get(Integer id) {
		
		return photozRepository.findById(id).orElse(null);
	}

	public void remove(Integer id) {
		
		photozRepository.deleteById(id);;
	}

	public Photo save(String fileName, String contentType, byte[] data) {
		
		Photo photo = new Photo();
		
		photo.setContentType(contentType);
		// photo.setId(UUID.randomUUID().toString());
		photo.setFileName(fileName);
		photo.setData(data);
		photozRepository.save(photo);
		
		return photo;
	}
}
