
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

@Controller
public class EditController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EditFormService editFormService;
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView viewEditProfile(Principal pricipal) {
		
		User user = userDao.findByUsername(pricipal.getName());
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
	
    @RequestMapping(value = "/submitEdit", method = RequestMethod.POST)
    public ModelAndView editUserProfile(@Valid EditForm editForm, BindingResult result, RedirectAttributes redirectAttributes) {
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
        }   	
    	return model;
    }
    @RequestMapping(value = "/submitTutorEdit", method = RequestMethod.POST)
    public ModelAndView editTutorProfile(@ModelAttribute TutorEditForm tutorForm, BindingResult result, 
    						RedirectAttributes redirectAttributes, HttpServletRequest request) {
    	ModelAndView model = new ModelAndView("editTutor");
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	model.addObject("tutorForm", tutorForm);
    	return model;
    }

    @RequestMapping(value = "/submitTutorEdit", method = RequestMethod.POST, params = { "save" })
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
        	model = new ModelAndView("edit");
        }   	
    	return model;
    }

	
}
