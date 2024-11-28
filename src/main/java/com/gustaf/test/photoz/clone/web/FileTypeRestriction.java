package com.gustaf.test.photoz.clone.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Target is used to specify the elements that can be annotated e.g. classes (TYPE), methods, method params.
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})

// Specifies validator class to be used
@Constraint(validatedBy = FileTypeRestrictionValidator.class)

// Specifies that annotation is available during runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface FileTypeRestriction {

	// array of accepted file types
	String[] acceptedTypes();
	
	// Default error message if file validation fails
	String message() default "File type is not allowed";
	
	// Validation groups for constraints. E.g. can have different rules for "image files" and "document files" etc.
	Class<?>[] groups() default {};
	
	// If file is not allowed, could add payload message e.g. "file type is not allowed"
	// Could be useful if wants more detailed error messages
	Class <? extends Payload>[] payload() default {};
	
}
