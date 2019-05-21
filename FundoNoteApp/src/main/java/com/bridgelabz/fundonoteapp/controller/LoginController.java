package com.bridgelabz.fundonoteapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.Login;
import com.bridgelabz.fundonoteapp.model.UserDetails;
import com.bridgelabz.fundonoteapp.service.UserService;
import com.bridgelabz.fundonoteapp.util.JwtUtil;

@RestController

public class LoginController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
		List<UserDetails> userList = userService.login(login);
		if (userList.size() != 0) {
			String token = JwtUtil.jwtToken(userList.get(0).getUserId());
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
