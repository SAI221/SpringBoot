package com.bridgelabz.springbootlogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootlogin.model.User;
import com.bridgelabz.springbootlogin.service.UserService;

@RestController

public class LoginController {
	@Autowired
	UserService userservice;

//find
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> getUserByLogin(@RequestBody User user) {
		String result=userservice.login(user);
		System.out.println(result);
		if(result.equals("Welcome")) 
		return new ResponseEntity<>("Logged In Sucessfully!", HttpStatus.OK);
		else 
			return new ResponseEntity<>("BadRequest!", HttpStatus.BAD_REQUEST);
	}
	@RequestMapping(value="/forgot",method=RequestMethod.POST)
	public String forgot(@RequestBody User user) {
		userservice.forgotPassword(user);
		return "Sent";
		
	}

}
