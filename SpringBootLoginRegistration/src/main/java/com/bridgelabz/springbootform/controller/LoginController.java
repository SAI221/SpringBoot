package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.service.UserService;

@RestController

public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(@RequestBody UserDetails user, HttpServletRequest request, HttpServletResponse response) {
		List<UserDetails> userList = userService.login(user);
		if (userList.size() != 0) {
			String token = userService.jwtToken(userList.get(0).getUserName());
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
		String username = userService.parseJWT(token);
		System.out.println(username);

		if (username.equals(user.getUserName())) {
			userService.updateUser(user);
			return "Updated";
		} else
			return "Not Updated";

	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String userDelete(String JwtToken, HttpServletRequest request,
			@RequestBody UserDetails user) {
		String token = request.getHeader("jwtToken");
		System.out.println(token);

		if (token != null) {
			userService.deleteUser(user.getEmail());
			return "Deleted";
		} else
			return "Not Deleted";
	}

}
