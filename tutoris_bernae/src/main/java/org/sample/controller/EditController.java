package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.EditForm;
import org.sample.controller.service.EditFormService;
import org.sample.model.User;
import org.sample.validators.UserEmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Allows editing profiles for normal users. Tutors are automatically redirected to
 * the correct page.
 */
@Controller
public class EditController extends PageController {
	
	@Autowired
	private EditFormService editFormService;
	
	@InitBinder("editForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UserEmailValidator(userDao));
	}
	
	/**
	 * Creates a page with an editing form for a normal user depending on the logged in profile,
	 * and redirects tutors.
	 * @return ModelAndView with ViewName "edit" and ModelAttribute "editForm", a new EditForm if the calling user
	 * is a user, or the user gets redirected to "/editTutor" if he is a tutor
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView viewEditProfile() {
		User user = getCurrentUser();
		if(user.getTutor()!=null) {	
			return new ModelAndView("redirect:/editTutor");
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
    public ModelAndView editUserProfile(HttpServletRequest request, @Valid EditForm editForm, BindingResult result) {
    	ModelAndView model;    	
    	if (!result.hasErrors()) {
            try {
            	editFormService.saveFrom(editForm);
            	User user = userDao.findByEmailLike(editForm.getEmail());
            	authenticateUserAndSetSession(user,request);
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
   
}
