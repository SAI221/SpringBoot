package com.bridgelabz.fundonoteapp.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.UserDetails;
import com.bridgelabz.fundonoteapp.service.UserService;
import com.bridgelabz.fundonoteapp.util.JwtUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class RegistrationController {

	@Autowired
	UserService userService;

	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public UserDetails createStudent(@RequestBody UserDetails user,HttpServletRequest request) {
		return userService.UserRegistration(user,request);
	}
	
	@RequestMapping(value="/fetching",method=RequestMethod.GET)
	public List<UserDetails> fetchingData(){
		return userService.fetchData();
	}
	@RequestMapping(value="/reg",method=RequestMethod.GET)
	public String active(HttpServletRequest request) {
		String token = request.getHeader("token");

		int id = JwtUtil.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setActiveStatus(1);
			userService.save(userList.get());
			return "changed";
		}
			else
				return "not changed";
	}

}
