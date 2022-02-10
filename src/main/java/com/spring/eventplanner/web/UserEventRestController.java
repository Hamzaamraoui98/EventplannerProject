package com.spring.eventplanner.web;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.eventplanner.entities.User;
import com.spring.eventplanner.entities.UserEvent;
import com.spring.eventplanner.entities.UserEventId;
import com.spring.eventplanner.repositories.UserEventRepository;
@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class UserEventRestController {
	
	@Autowired
	private UserEventRepository usereventrepository;
	 @PutMapping(path = "/events/visit")
	 @Transactional
	    public UserEvent addNewUser(@RequestBody UserEventId id ){
		 	UserEvent u = usereventrepository.getById(id);
		 	u.setShowed(true);
		 	System.out.println(id);
		 	return usereventrepository.save(u);
	    }

}
