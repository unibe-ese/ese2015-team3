package org.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sample.model.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
/*	public FileUploadController(){
		setCommandClass(FileUpload.class);
		setCommandName("fileUploadForm");
	}*/
 
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	protected ModelAndView onSubmit(HttpServletRequest request,
		HttpServletResponse response, Object command, BindException errors)
		throws Exception {
 
		FileUpload file = (FileUpload)command;
		
		MultipartFile multipartFile = file.getFile();
		
		String fileName="";

		if(multipartFile!=null){
			fileName = multipartFile.getOriginalFilename();
			//do whatever you want
		}
		
		return new ModelAndView("fileUploadSuccess", "fileName", fileName);
	}
}
