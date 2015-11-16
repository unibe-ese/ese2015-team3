package org.sample.controller.pojos;

import java.util.List;

import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;

public interface ClassCourseList {
	public List<StudyCourse> getStudyCourseList();

	public void setStudyCourseList(List<StudyCourse> studyCourseList);

	public List<CompletedClasses> getClassList();

	public void setClassList(List<CompletedClasses> classList);

}
