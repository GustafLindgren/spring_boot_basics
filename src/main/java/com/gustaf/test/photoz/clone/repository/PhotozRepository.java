package com.gustaf.test.photoz.clone.repository;

import org.springframework.data.repository.CrudRepository;

import com.gustaf.test.photoz.clone.model.Photo;

public interface PhotozRepository extends CrudRepository<Photo, Integer> {
		
}

