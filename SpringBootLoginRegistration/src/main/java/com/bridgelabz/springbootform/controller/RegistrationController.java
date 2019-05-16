package com.bridgelabz.springbootform.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.service.UserService;

@RestController

public class RegistrationController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public UserDetails createStudent(@RequestBody UserDetails user,HttpServletRequest request) {
		return userService.UserRegistration(user,request);
	}

}
