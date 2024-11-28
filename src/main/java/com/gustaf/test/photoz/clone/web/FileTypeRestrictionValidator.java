package com.gustaf.test.photoz.clone.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

// ConstraintValidator interface: annotation to be validated, object type
public class FileTypeRestrictionValidator implements ConstraintValidator<FileTypeRestriction, Object>{

	private List<String> acceptedTypes;
	private String messageTemplate;
	
	/**
	 *  Retrieves allowed file types and error message template from the 
	 *  FileTypeRestriction annotation
	 *  @param annotation from controller
	 */
	@Override
	public void initialize(FileTypeRestriction annotation) {
		this.acceptedTypes = Arrays.asList(annotation.acceptedTypes());
		this.messageTemplate = annotation.message();
	}
	
	/**
	 *  Ensures that file is one of the types is one of the accepted types
	 *  @param value : the object to be validated
	 *  @param context : object used to interact with the validation framework e.g. build error messages
	 *  @return true if one of the accepted types or null
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		if (value == null) {
			return true;
		}
		
		if (value instanceof MultipartFile) {
			try {
				return validateFile((MultipartFile) value, context);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (value instanceof List || value instanceof Object[]) {
            List<Object> files = value instanceof List ? (List<Object>) value : Arrays.asList((Object[]) value);
            return files.stream().allMatch(file -> {
				try {
					return validateFile((MultipartFile) file, context);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			});
        }
		
		// No file or collection of files --> no file type restrictions to enforce 
		return true;
		
	}
	
	/**
	 *  Performs validation for a single MultiPartfile i.e. checks if contentType is present in the
	 *  acceptedTypes list. If not present, a constraint violation message is generated.
	 * @param file : the file to be checked
	 * @param context : object used to interact with the validation framework e.g. build error messages
	 * @return true if the file is of a valid format, otherwise false
	 * @throws IOException
	 */
	private boolean validateFile(MultipartFile file, ConstraintValidatorContext context) throws IOException {
		
		String contentType = detectContentType(new BufferedInputStream(file.getInputStream()));
		boolean isValid = acceptedTypes.contains(contentType);
		
		if (!isValid) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					String.format(messageTemplate, file.getOriginalFilename(), acceptedTypes))
					.addConstraintViolation();
		}
		
		return isValid;
	}
	/**
	 * Uses the Apache Tika library to detect the content type of the file based on the provided input stream
	 * @param stream : Input stream from the MultipartFile
	 * @return the detected media type as a string
	 * @throws IOException
	 */
	private String detectContentType(BufferedInputStream stream) throws IOException {
		Detector detector = new DefaultDetector();
		Metadata metadata = new Metadata();
		
		MediaType mediaType = detector.detect(stream, metadata);
		return mediaType.toString();
	}

}
