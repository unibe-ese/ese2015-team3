package org.sample.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.sample.model.CompletedClasses;
import org.sample.model.StudyCourse;

/**
 * Process study and course lists after a post request containing them by
 * looking for parameters in the request and adapting the lists accordingly
 */
public class ListHelper {

	/**
	 * Use this after a post request to adapt the a studycourselist.
	 * removes a row given by the parameter "removeStudyCourse" in the request 
	 * andd adds a new empty row if the parameter "addStudyCourse" is present
	 * in the request
	 * @param request the HttpServletRequest which can contain parameters like "removeStudyCourse"
	 * or "addStudyCourse" to let the list be adapted
	 * @param studyCourseList the list which should be changed based on the parameters in the request
	 * @return a new List<StudyCourse> which was made of the old list by adding a new row and/or 
	 * removing a row
	 */
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
	
	/**
	 * Use this after a post request to adapt the a list of completedclasses
	 * removes a row given by the parameter "removeClass" in the request 
	 * andd adds a new empty row if the parameter "addClass" is present
	 * in the request
	 * @param request the HttpServletRequest which can contain parameters like "removeClass"
	 * or "addClass" to let the list be adapted
	 * @param studyCourseList the list which should be changed based on the parameters in the request
	 * @return a new List<CompletedClasses> which was made of the old list by adding a new row and/or 
	 * removing a row
	 */
	public static List<CompletedClasses> handleClassList(HttpServletRequest request, List<CompletedClasses> classList) {
		if(classList == null)
			classList = new LinkedList<CompletedClasses>();
		String removeClass = request.getParameter("removeClass");
		if(removeClass != null)
			classList.remove(Integer.parseInt(removeClass));
		String addRow = request.getParameter("addClass");
		if(addRow != null)
			classList.add(new CompletedClasses());
		return classList;
	}
	
}
