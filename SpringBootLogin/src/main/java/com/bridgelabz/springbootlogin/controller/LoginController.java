package com.bridgelabz.springbootlogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String getUserByLogin(@RequestBody User user) {

		/*
		 * int result=userservice.login(user); if(result>0) {
		 * System.out.println(user.getFirstName()); return
		 * "Welcome"+user.getFirstName(); } jwtToken =
		 * Jwt.builder().setSubject(user.getUserName()).claim( "user").setIssuedAt(new
		 * Date()) .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		 * 
		 * else return "Invalid User Details";
		 */
		return userservice.login(user);
	}

}
