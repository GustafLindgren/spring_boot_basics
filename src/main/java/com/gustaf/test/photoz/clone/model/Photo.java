package com.gustaf.test.photoz.clone.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;

// Map java class to db table
@Table("PHOTOZ")
public class Photo {
	
	@Id
	private Integer id;
	
	@NotEmpty
	private String fileName;
	// raw data
	
	// So we don't see the data in the front-end i.e. no long string
	@JsonIgnore
	private byte[] data;
	
	private String contentType;
	// Empty constructor to avoid problems when converting the Java class
	// to JSON
	public Photo() {
		
	}
	
	public String getFileName() {
		return fileName;
	}
	
	// JSON name is dependent on setter name?
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	
}
