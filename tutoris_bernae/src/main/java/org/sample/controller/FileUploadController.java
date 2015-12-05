package org.sample.controller;

import java.io.File;

import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
	
	@Autowired
	private UserDao userDao;


    private String saveDir = "C:/Users/gc/Downloads/";
    
    @RequestMapping(value = "/fileupload", method = RequestMethod.GET)
    protected ModelAndView view() throws Exception {
    	ModelAndView model = new ModelAndView("fileUploadForm");
    	return model;
    }

	@RequestMapping(value = "/fileuploadpage", method = RequestMethod.POST)
	protected ModelAndView fileuploadpage(@RequestParam MultipartFile file) throws Exception {
		
		String workingDir = System.getProperty("user.dir");
		saveDir = workingDir + "/src/main/webapp/img/profile_pics/"; 
		File dir = new File(saveDir);//workingDir + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "img" + File.separator + "profile_pics");
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		String fileName="";
		if (file != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String name = authentication.getName();
			User user = userDao.findByEmailLike(name);
			String currentProfilePictureName = user.getProfilePicture();
			
			fileName = file.getOriginalFilename();
			String[] splittedName = fileName.split("\\.");
			String fileNameEnding = splittedName[splittedName.length-1]; // get the last part of the name which should be the ending

			if(currentProfilePictureName == null) { //no profile picture is set yet
				fileName = "pp" + user.getId().toString() + "_0." + fileNameEnding.toLowerCase();				
			} else{ //if  a profile picture is already set: increment the profile picture counter.
				splittedName = currentProfilePictureName.split("_");
				splittedName = splittedName[splittedName.length-1].split("\\.");
				Long profPicCounter = Long.parseLong(splittedName[0]);
				profPicCounter = profPicCounter + 1;
				fileName = "pp" + user.getId().toString() + "_" + profPicCounter.toString() + "." + fileNameEnding.toLowerCase();				
			}
			
			if(!fileName.equals("")){
				file.transferTo(new File(saveDir + fileName));
				user.setProfilePicture(fileName);
				userDao.save(user);
			}
		}
 
		ModelAndView model = new ModelAndView("fileUploadSuccess", "fileName", fileName);
		return model;
	}
}
