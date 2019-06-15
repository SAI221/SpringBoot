package com.bridgelabz.springbootform.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.service.UserService;
import com.bridgelabz.springbootform.token.TokenClass;

@RestController

public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	TokenClass tokenClass;
	private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	@PostMapping(value = "/login")
	public String userLogin(UserDetails user, HttpServletRequest request, HttpServletResponse response) {
		List<UserDetails> userList = userService.login(user);
		if (!userList.isEmpty()) {
			String token = tokenClass.jwtToken(userList.get(0).getUserId());
			response.setHeader("JwtToken", token);
			return "Welcome " + userList.get(0).getUserName() + " JWT--->" + token;
		} 
			return "Invalid Credentials";

	}

	// UPDATE
	@PutMapping(value = "/update/{token}")
	public String userUpdate(HttpServletRequest request, UserDetails user, @PathVariable String token) {
		
		LOGGER.info(token);
		userService.updateUser(token, user);
		return "Updated";

	}

	// DELETE
	@DeleteMapping(value = "/delete/{token}")
	public String userDelete(HttpServletRequest request, @PathVariable String token) {
		 
		
		LOGGER.info(token);
		userService.deleteUser(token);
		return "Deleted";

	}
}
