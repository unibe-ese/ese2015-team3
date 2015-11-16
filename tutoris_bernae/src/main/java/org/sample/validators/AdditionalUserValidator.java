package org.sample.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sample.controller.pojos.ClassCourseList;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
//TODO to be implemetented: Should check if the username and email is unique in any form including it
@Component
public class AdditionalUserValidator implements Validator
{
	
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}
	
}