package org.sample.controller.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.pojos.TeamForm;
import org.sample.model.Address;
import org.sample.model.Team;
import org.sample.model.User;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.TeamDao;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service
public class SampleServiceImpl implements SampleService {

    @Autowired    UserDao userDao;
    @Autowired    AddressDao addDao;
    @Autowired 	  TeamDao teamDao;
    
    @Transactional
    public SignupForm saveFrom(SignupForm signupForm) throws InvalidUserException{

        String firstName = signupForm.getFirstName();

        if(!StringUtils.isEmpty(firstName) && "ESE".equalsIgnoreCase(firstName)) {
            throw new InvalidUserException("Sorry, ESE is not a valid name");   // throw exception
        }


        Address address = new Address();
        address.setStreet("TestStreet-foo");
        
        User user = new User();
        user.setFirstName(signupForm.getFirstName());
        user.setEmail(signupForm.getEmail());
        user.setLastName(signupForm.getLastName());
        
        user = userDao.save(user);   // save object to DB
        
        
        // Iterable<Address> addresses = addDao.findAll();  // find all 
        // Address anAddress = addDao.findOne((long)3); // find by ID
        
        
        signupForm.setId(user.getId());

        return signupForm;

    }
    

	public TeamForm saveFrom(TeamForm teamForm) throws InvalidUserException {
		  String teamName = teamForm.getTeamName();
		     if(!StringUtils.isEmpty(teamName) && "ESE".equalsIgnoreCase(teamName)) {
		         throw new InvalidUserException("Sorry, ESE is not a valid name");   // throw exception
		     }
		     
		     Team team = new Team();
		     team.setTeamName(teamForm.getTeamName());
		     
		     team.setDate(new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()));
		     
		     team = teamDao.save(team);  //save object to DB
		     
		     // Iterable<Address> addresses = addDao.findAll();  // find all 
		     // Address anAddress = addDao.findOne((long)3); // find by ID
		    
		     teamForm.setId(team.getId());

		     return teamForm;
	}
 
}
