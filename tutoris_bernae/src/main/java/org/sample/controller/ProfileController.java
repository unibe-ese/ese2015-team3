package org.sample.controller;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.service.SampleService;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ProfileController {

  private final UserDao userRepository;

  @Autowired
  public ProfileController(UserDao userRepository) {
    this.userRepository = userRepository;
  }
  
  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public ModelAndView profile() {
  	  	
  	ModelAndView model = new ModelAndView("profile");
      return model;
  }
  

  @RequestMapping(value = "/userId={id}", method = RequestMethod.GET)
  public ModelAndView showUser(@PathVariable("id") Long id) {
	  ModelAndView model = new ModelAndView("profile");
	  User user = null;
    if (id != null){// Do null check for id
    user = userRepository.findOne(id);
    // Do null check for user
    }
    if (user != null){
        model.addObject("user", user);
    }
    model.addObject("signupForm", new SignupForm());
    
    return model;
  }
}

