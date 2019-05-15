package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(@RequestBody UserDetails user, HttpServletRequest request, HttpServletResponse response) {
		List<UserDetails> userList = userService.login(user);
		if (userList.size() != 0) {
			String token = tokenClass.jwtToken(userList.get(0).getUserId());
			response.setHeader("JwtToken", token);
			return "Welcome " + userList.get(0).getUserName() + " JWT--->" + token;
		} else
			return "Invalid Credentials";

	}

	// UPDATE
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String userUpdate(HttpServletRequest request, @RequestBody UserDetails user) {
		String token = request.getHeader("jwtToken");
		System.out.println(token);
		userService.updateUser(token, user);
			return "Updated";
		
	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String userDelete(HttpServletRequest request) {
		String token = request.getHeader("jwtToken");
		System.out.println(token);
		userService.deleteUser(token);
		return "Deleted";

	}
}
