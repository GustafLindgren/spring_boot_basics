package com.gustaf.test.photoz.clone.web;


import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.gustaf.test.photoz.clone.model.Photo;
import com.gustaf.test.photoz.clone.service.PhotozService;


import jakarta.validation.Valid;

@RestController
public class PhotozController {
	
	private final PhotozService photozService;
	
	public PhotozController(PhotozService photozService) {
		this.photozService = photozService;
	}
	
	@GetMapping("/")
	public String helloWorld() {
		return "Hello World";
	}
	
	@GetMapping("/photoz")
	public Iterable<Photo> get() {
		
		return photozService.get();
	}
	
	@GetMapping("/photoz/{id}")
	public Photo get(@PathVariable Integer id) {
		Photo photo = photozService.get(id);
		
		if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return photo;
	}
	
	@DeleteMapping("/photoz/{id}")
	public void delete(@PathVariable Integer id) {
		photozService.remove(id);
		
	}
	
	// RequestBody: convert JSON to Photo object
	// RequestPart tells Springboot which part contains the file
	// A form is sent from the client where the file is stored under the key "data"
	@PostMapping("/photoz")
	public Photo create(
			@Valid 
			@FileTypeRestriction(
		            acceptedTypes = {
		                MediaType.IMAGE_PNG_VALUE,
		                MediaType.IMAGE_JPEG_VALUE
		            }
		        )
			@RequestPart
			("data") MultipartFile file) throws IOException {
		
		
		Photo photo = photozService.save(file.getOriginalFilename(),
				file.getContentType(), file.getBytes());
		return photo;
	}
	
}
