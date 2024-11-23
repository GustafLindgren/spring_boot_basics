package com.gustaf.test.photoz.clone.web;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gustaf.test.photoz.clone.model.Photo;
import com.gustaf.test.photoz.clone.service.PhotozService;

@RestController
public class DownloadController {
	
	private final PhotozService photozService;
	
	public DownloadController(PhotozService photozService) {
		this.photozService = photozService;
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> download(@PathVariable Integer id) {
		
		Photo photo = photozService.get(id);
		if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		byte[] data = photo.getData();
		HttpHeaders headers = new HttpHeaders();
		// Content-Type header set to the appropriate media type based on photo's content type png jpeg etc
		headers.setContentType(MediaType.valueOf(photo.getContentType()));
		// ContentDisposition header created with attachment disposition type and photo's filename
		// Header instructs browser to prompt the user to save the file
		ContentDisposition build = ContentDisposition
				.builder("attachment")
				.filename(photo.getFileName())
				.build();
		headers.setContentDisposition(build);
		
		return new ResponseEntity<>(data, headers, HttpStatus.OK);
		
	}
}
