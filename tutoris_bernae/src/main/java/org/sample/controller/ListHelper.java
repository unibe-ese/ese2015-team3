package org.sample.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.sample.controller.pojos.CompletedClassesPrototype;
import org.sample.model.Classes;
import org.sample.model.StudyCourse;

public class ListHelper {

	public static List<StudyCourse> handleStudyCourseList(HttpServletRequest request, List<StudyCourse> studyCourseList) {
		if(studyCourseList == null)
			studyCourseList = new LinkedList<StudyCourse>();
		String removeCourse = request.getParameter("removeStudyCourse");
		if(removeCourse != null)
			studyCourseList.remove(Integer.parseInt(removeCourse));
		String addRow = request.getParameter("addStudyCourse");
		if(addRow != null)
			studyCourseList.add(new StudyCourse());
		return studyCourseList;
	}
	
	public static List<CompletedClassesPrototype> handleClassList(HttpServletRequest request, List<CompletedClassesPrototype> classList) {
		if(classList == null)
			classList = new LinkedList<CompletedClassesPrototype>();
		String removeClass = request.getParameter("removeClass");
		if(removeClass != null)
			classList.remove(Integer.parseInt(removeClass));
		String addRow = request.getParameter("addClass");
		if(addRow != null)
			classList.add(new CompletedClassesPrototype());
		return classList;
	}

	
}
