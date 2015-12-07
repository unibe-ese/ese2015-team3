package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.TutorFormService;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controls the creating of tutor profiles, by directly upgrade a normal user
 * during the normal register process or later via upgrade.
 */
@Controller
public class TutorRegisterController extends PageController {
	public static final String PAGE_SUBMIT = "submitPage";
	public static final String PAGE_REGISTER = "register";
	
    @Autowired
    private StudyCourseDao studyCourseDao;
    @Autowired
    private ClassesDao classesDao;
	@Autowired
	private TutorFormService tutorFormService;
	@Autowired
	private UserDao userDao;
    
	@InitBinder("tutorForm")
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Classes.class, new ClassesEditor(classesDao));
		binder.registerCustomEditor(StudyCourse.class, new StudyCourseEditor(studyCourseDao));
		binder.addValidators(new ClassCourseListValidator());
	}
    /**
     * Creates a tutor registration page for logged in users. This allows existing users
     * to upgrade their profile to a tutor profile
     * @return a ModelAndView with ViewName "tutorregistration" and attributes "tutorForm", a new TutorForm
     * and "studyCourseList", a list of all StudyCourses (for a dropdown-menu on the page)
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.GET)
    public ModelAndView upgradePage() { 
		User user = getCurrentUser();
    	TutorForm tutorForm = new TutorForm();
    	tutorForm.setUserId(user.getId());
        return createTutorFormPage(tutorForm);
    }
    
    private ModelAndView createTutorFormPage(TutorForm form)
    {
    	ModelAndView model = new ModelAndView("tutorregistration");
    	model.addObject("tutorForm", form);
    	model.addObject("allClasses", classesDao.findAll());
        model.addObject("allCourses", studyCourseDao.findAll());
        return model;
    }
        
    /**
     * A helping method for adding or deleting elements on the course and class list
     * @param session
     * @param request
     * @param tutorForm a TutorForm, must not be valid
     * @return ModelAndView with ViewName "tutorregistration" and ModelAttribute "tutorForm", the given TutortForm
     * with updated lists
     */
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST)
    public ModelAndView updateListsForTutorForm(HttpSession session,HttpServletRequest request,@ModelAttribute TutorForm tutorForm) {
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	return createTutorFormPage(tutorForm);
    }
    
    /**
     * Saves the tutor details in the database and displays a succes page, or again the tutor register page if the
     * form was not filled out correctly
     * @param tutorForm a Valid TutorForm
     * @param save a required Boolean RequestParam, indicating that the form needs to be saved and not only updated
     * @param request
     * @return if the tutorform was successfully filled a new ModelAndView with ViewName "submitPage" or else
     * again a new ModelAndView with ViewName "tutorregistration" and ModelAttribute "tutorForm", the given TutorForm
     */
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST, params = { "save" })
    public ModelAndView submitTutorForm(@Valid TutorForm tutorForm, BindingResult result,
            @RequestParam Boolean save,HttpServletRequest request, HttpSession session) {
        ModelAndView model;
        tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	if (!result.hasErrors()){
    		try{
    			tutorFormService.saveFrom(tutorForm);
    			User user = userDao.findOne(tutorForm.getUserId());
    			authenticateUserAndSetSession(user,request);
    			model = new ModelAndView(PAGE_SUBMIT);
    			model.addObject("message", "Tutor registration complete!");
    		}
    		catch(InvalidUserException e){
    			model = new ModelAndView(PAGE_SUBMIT);
    			model.addObject("page_error", "You are already a tutor!");
    		}
    	}
    	else { 
    		model = createTutorFormPage(tutorForm);
    	}
    	return model;

    }
    
}
