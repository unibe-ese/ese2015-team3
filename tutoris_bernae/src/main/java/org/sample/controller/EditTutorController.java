package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.service.EditFormService;
import org.sample.model.Classes;
import org.sample.model.ClassesEditor;
import org.sample.model.StudyCourse;
import org.sample.model.StudyCourseEditor;
import org.sample.model.User;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.UserDao;
import org.sample.validators.ClassCourseListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Allows editing profiles for normal users and tutor. The 
 * edit page automatically knows if the user is a tutor or not and returns fitting editing site.
 * The submit-sites depend on whetever a tutor or a normal user changed his profile informations.
 * @author pf15ese
 */
@Controller
public class EditTutorController {

    @Autowired
    private ClassesDao classesDao;
    
    @Autowired
    private StudyCourseDao studyCourseDao;
    
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EditFormService editFormService;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Classes.class, new ClassesEditor(classesDao));
		binder.registerCustomEditor(StudyCourse.class, new StudyCourseEditor(studyCourseDao));
		binder.addValidators(new ClassCourseListValidator());
	}
	
	
	/**
	 * Creates a page with an editing form for a normal user or a tutor,
	 * depending on the logged in profile
	 * @return ModelAndView with ViewName "editTutor" and ModelAttribute "tutorEditForm", a new TutorEditForm if the calling user
	 * is a tutor, or ModelAndView with ViewName "edit" and ModelAttribute "editForm", a new EditForm if the calling user
	 * is a normal user
	 */
	@RequestMapping(value = "/editTutor", method = RequestMethod.GET)
	public ModelAndView viewEditProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String name = authentication.getName();
		User user = userDao.findByUsername(name);
		if(user.isTutor()) {	
			return createTutorEditFormPage(new TutorEditForm(user, user.getTutor()));
		}
		else {
			return new ModelAndView("redirect:/edit");
		}
	}
	
	public ModelAndView createTutorEditFormPage(TutorEditForm form)
	{
		ModelAndView model = new ModelAndView("editTutor");
		model.addObject("tutorEditForm", form);
		model.addObject("allClasses", classesDao.findAll());
		model.addObject("allCourses", studyCourseDao.findAll());
		return model;
	}
    
    /**
     * A helping method for adding or deleting elements on the course and class list
     * @param tutorEditForm a tutorEditForm which must not be valid yet
     * @param result
     * @param redirectAttributes
     * @param request
     * @return ModelAndView with ViewName "editTutor" and ModelAttribute "tutorEditForm", the given TutorEditForm
     * with updated lists
     * 
     */
    @RequestMapping(value = "/editTutorSubmit", method = RequestMethod.POST)
    public ModelAndView editTutorProfile(@ModelAttribute TutorEditForm tutorEditForm, BindingResult result, 
    						RedirectAttributes redirectAttributes, HttpServletRequest request) {
    	tutorEditForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorEditForm.getStudyCourseList()));
    	tutorEditForm.setClassList(ListHelper.handleClassList(request,tutorEditForm.getClassList()));
    	return createTutorEditFormPage(tutorEditForm);
    }

    /**
     * Saves the edited Profile informations for a tutor, and shows a success page ("editDone"). If the 
     * entered information weren't complete or wrong the tutor is directed back to the editTutor page.
     * @param tutorEditForm
     * @param result
     * @param redirectAttributes
     * @param save a Boolean request parameter so that the controller knows the profile needs to be saved,
     * and not only that the lists needs updating
     * @param request
     * @return if the the form was successfully filled a new ModelAndView with ViewName "editDone" or else
     * again a new ModelAndView with ViewName "editTutor" and ModelAttribute "tutorEditForm", the given TutorEditForm
     */
    @RequestMapping(value = "/editTutorSubmit", method = RequestMethod.POST, params = { "save" })
    public ModelAndView editTutorProfile(@Valid TutorEditForm tutorEditForm, BindingResult result, 
    						RedirectAttributes redirectAttributes,@RequestParam Boolean save , HttpServletRequest request) {
    	ModelAndView model;
    	tutorEditForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorEditForm.getStudyCourseList()));
    	tutorEditForm.setClassList(ListHelper.handleClassList(request,tutorEditForm.getClassList()));
    	if (!result.hasErrors()) {
            try {
            	editFormService.saveFrom(tutorEditForm);
            	model = new ModelAndView("editDone");
            } catch (InvalidUserException e) {
            	model = createTutorEditFormPage(tutorEditForm);
            	model.addObject("page_error", e.getMessage());
            	return model;
            }
        } else {
        	for(ObjectError o : result.getAllErrors())
        	{
        		System.out.println(o);
        	}
        	
        	model = createTutorEditFormPage(tutorEditForm);
        }   	
    	return model;
    }
}
