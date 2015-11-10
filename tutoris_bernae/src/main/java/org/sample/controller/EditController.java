
package org.sample.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.pojos.TutorEditForm;
import org.sample.controller.service.EditFormService;
import org.sample.controller.service.RegisterFormService;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class EditController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EditFormService editFormService;
	
	/**
	 * Creates a page with an editing form for a normal user or a tutor,
	 * depending on the logged in profile
	 * @return ModelAndView with ViewName "editTutor" and ModelAttribute "tutorForm", a new TutorEditForm if the calling user
	 * is a tutor, or ModelAndView with ViewName "edit" and ModelAttribute "editForm", a new EditForm if the calling user
	 * is a normal user
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView viewEditProfile() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String name = authentication.getName();
		User user = userDao.findByUsername(name);
		if(user.isTutor()) {
			ModelAndView model = new ModelAndView("editTutor");
			model.addObject("tutorForm", new TutorEditForm(user, user.getTutor()));
			return model;
		}
		else {
			ModelAndView model = new ModelAndView("edit");
			model.addObject("editForm", new EditForm(user));
			return model;
		}
	}
	
    /**
     * Saves the edited Profile informations for a user, and shows a success page ("editDone"). If the 
     * entered information weren't complete or wrong the user is directed back to the edit page.
     * @param editForm a valid EditForm
     * @param result
     * @param redirectAttributes
     * @return if the the form was successfully filled a new ModelAndView with ViewName "editDone" or else
     * again a new ModelAndView with ViewName "edit" and ModelAttribute "editForm", a new EditForm
     */
    @RequestMapping(value = "/editSubmit", method = RequestMethod.POST)
    public ModelAndView editUserProfile(@Valid EditForm editForm, BindingResult result) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	
            	editFormService.saveFrom(editForm);
            	model = new ModelAndView("editDone");
            } catch (InvalidUserException e) {
            	
            	model = new ModelAndView("edit");
            	model.addObject("editForm", editForm);
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("edit");
        	model.addObject("editForm", editForm);
        }   	
    	return model;
    }
    
    /**
     * A helping method for adding or deleting elements on the course and class list
     * @param tutorForm a TutorForm which must not be valid yet
     * @param result
     * @param redirectAttributes
     * @param request
     * @return ModelAndView with ViewName "editTutor" and ModelAttribute "tutorForm", the given TutorEditForm
     * with updated lists
     * 
     */
    @RequestMapping(value = "/editTutorsubmit", method = RequestMethod.POST)
    public ModelAndView editTutorProfile(@ModelAttribute TutorEditForm tutorForm, BindingResult result, 
    						RedirectAttributes redirectAttributes, HttpServletRequest request) {
    	ModelAndView model = new ModelAndView("editTutor");
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	model.addObject("tutorForm", tutorForm);
    	return model;
    }

    /**
     * Saves the edited Profile informations for a tutor, and shows a success page ("editDone"). If the 
     * entered information weren't complete or wrong the tutor is directed back to the editTutor page.
     * @param tutorForm
     * @param result
     * @param redirectAttributes
     * @param save a Boolean request parameter so that the controller knows the profile needs to be saved,
     * and not only that the lists needs updating
     * @param request
     * @return if the the form was successfully filled a new ModelAndView with ViewName "editDone" or else
     * again a new ModelAndView with ViewName "editTutor" and ModelAttribute "tutorForm", the given TutorEditForm
     */
    @RequestMapping(value = "/editTutorsubmit", method = RequestMethod.POST, params = { "save" })
    public ModelAndView editTutorProfile(@Valid TutorEditForm tutorForm, BindingResult result, 
    						RedirectAttributes redirectAttributes,@RequestParam Boolean save , HttpServletRequest request) {
    	ModelAndView model;
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	if (!result.hasErrors()) {
            try {
            	editFormService.saveFrom(tutorForm);
            	model = new ModelAndView("editDone");
            } catch (InvalidUserException e) {
            	model = new ModelAndView("editTutor");
            	model.addObject("tutorForm", tutorForm);
            	model.addObject("page_error", e.getMessage());
            }
        } else {
          	model = new ModelAndView("editTutor");
        	model.addObject("tutorForm", tutorForm);
        }   	
    	return model;
    }
}
