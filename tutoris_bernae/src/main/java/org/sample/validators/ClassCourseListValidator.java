package org.sample.validators;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sample.controller.pojos.FormWithClassCourseList;
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

@Component
public class ClassCourseListValidator implements Validator
{
	public void validate(Object target, Errors errors) {
		FormWithClassCourseList formWithLists = (FormWithClassCourseList) target;
		if(!checkCourseUniqueness(formWithLists.getStudyCourseList())) 
			errors.rejectValue("studyCourseList","studyCourses.Duplication","You can select any StudyCourse only once");
		if(!checkClassesUniqueness(formWithLists.getClassList()))
			errors.rejectValue("classList","classes.Duplication","You can select any Class only once");
	}

	public boolean supports(Class<?> clazz) {
		return TutorEditForm.class.equals(clazz) || TutorForm.class.equals(clazz);
	}

	private boolean checkCourseUniqueness(List<StudyCourse> studyCourseList) {
		Set<StudyCourse> courseSet = new HashSet<StudyCourse>();
		for(StudyCourse c : studyCourseList){
			if(!courseSet.add(c)) return false;
		}
		return true;
	}

	private boolean checkClassesUniqueness(List<CompletedClasses> classList) {
		//We have to check uniqueness via name, because ids arent set before saving,
		// and there could be two times the same class, but with different grades, so they wouldn't be
		//equals, but still the class is two times in the list
		Set<String> classNameSet = new HashSet<String>();
		for(CompletedClasses c : classList){
			if(!classNameSet.add(c.getClasses().getName())) return false;
		}
		return true;
	}

}