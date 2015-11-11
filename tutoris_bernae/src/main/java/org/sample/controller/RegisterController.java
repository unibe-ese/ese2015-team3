package org.sample.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;

import org.sample.controller.pojos.RegisterForm;
import org.sample.controller.pojos.TutorForm;
import org.sample.controller.service.RegisterFormService;
import org.sample.controller.service.TutorFormService;
import org.sample.model.Classes;
import org.sample.model.dao.ClassesDao;
import org.sample.model.dao.StudyCourseDao;
import org.sample.model.dao.UserDao;
import org.sample.wrapper.ClassesListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class RegisterController {
	public static final String PAGE_SUBMIT = "submitPage";
	public static final String PAGE_REGISTER = "register";
		
    @Autowired
    private StudyCourseDao studyCourseDao;
    @Autowired
    private ClassesDao classesDao;
	@Autowired
	private RegisterFormService registerFormService;
	@Autowired
	private TutorFormService tutorFormService;
	@Autowired
	private UserDao userDao;
	
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView registerBasePage() {  	
    	ModelAndView model = new ModelAndView(PAGE_REGISTER);
    	model.addObject("registerForm", new RegisterForm());
        return model;
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ModelAndView create(HttpSession session,HttpServletRequest request,@Valid RegisterForm registerForm, BindingResult result, 
    						@RequestParam(value = "registerastutor", required = false) String registerastutor) {
    	ModelAndView model;  
    	
    	if (!result.hasErrors()) {
            try {
            	registerForm = registerFormService.saveFrom(registerForm);
            	model = new ModelAndView(PAGE_SUBMIT);
            } catch (InvalidUserException e) {
            	model = new ModelAndView(PAGE_REGISTER);
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView(PAGE_REGISTER);
        }   
    	if(registerastutor!=null)
    	{
    		model = new ModelAndView("tutorregistration");
        	TutorForm tutorForm = new TutorForm();
        	tutorForm.setUserId(registerForm.getId());
        	model.addObject("tutorForm", tutorForm);
            model.addObject("studyCourseList", studyCourseDao.findAll());
            model.addObject("classesList", classesDao.findAll());
    	}
    	return model;
    }
        
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST)
    public ModelAndView create(HttpSession session,HttpServletRequest request,
    		@ModelAttribute TutorForm tutorForm, @RequestParam(value="classesList", required=false) List<Classes> classesList,
    		@RequestParam(value="selectedClassId", required=false) long selectedClassId) {
    	ModelAndView model = new ModelAndView("tutorregistration");
//    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	// get the current classes that have already been added.
    	List<Classes> classList = new LinkedList<Classes>();
    	classList = tutorForm.getClassList();
    	
		String removeClass = request.getParameter("removeClass");
		if(removeClass != null){
			classList.remove(Integer.parseInt(removeClass));
		}
		
		String addRow = request.getParameter("addClass");
		if(addRow != null){
	    	// add the new classes
			Classes selectedClass = classesDao.findOne(selectedClassId);
			//check if it is already contained in the list
			if(!classList.contains(selectedClass))
				classList.add(selectedClass);			
		}

    	// remove classes that have already been added from the drop-down
    	List<Classes> selectableClasses = (List<Classes>) classesDao.findAll();
    	for(Classes tmpClass : selectableClasses){
    		if(classList.contains(tmpClass))
    			selectableClasses.remove(tmpClass);
    	}
    	tutorForm.setClassList(classList);
//    	tutorForm.setClassList(ListHelper.handleClassList(request,classList));
    	model.addObject("tutorForm", tutorForm);
        model.addObject("studyCourseList", studyCourseDao.findAll());
        model.addObject("classesList", selectableClasses);
    	model.addObject("classList", classList);
    	return model;
    }
    
    @RequestMapping(value = "/submitastutor", method = RequestMethod.POST, params = { "save" })
    public ModelAndView create(@Valid TutorForm tutorForm, HttpSession session,
    		@RequestParam Boolean save,HttpServletRequest request) {
    	ModelAndView model = new ModelAndView(PAGE_SUBMIT);
    	tutorForm.setStudyCourseList(ListHelper.handleStudyCourseList(request,tutorForm.getStudyCourseList()));
    	tutorForm.setClassList(ListHelper.handleClassList(request,tutorForm.getClassList()));
    	tutorFormService.saveFrom(tutorForm);
    	return model;
    }
    
}
