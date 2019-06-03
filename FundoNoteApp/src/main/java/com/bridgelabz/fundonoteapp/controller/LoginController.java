package com.bridgelabz.fundonoteapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.Login;
import com.bridgelabz.fundonoteapp.model.UserDetails;
import com.bridgelabz.fundonoteapp.service.UserService;
import com.bridgelabz.fundonoteapp.util.JwtUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") 
@RequestMapping("/")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/login")
	public ResponseEntity<?> userLogin(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
		List<UserDetails> userList = userService.login(login);
		if (userList.size() != 0) {
			String token = JwtUtil.jwtToken(userList.get(0).getUserId());
			response.setHeader("token", token);
			return new ResponseEntity<>(
					/*"Welcome "+ userList.get(0).getUserName() + "   Jwt Token--->" + response.getHeader("token")*/HttpStatus.OK);
		} else
			return new ResponseEntity<String>("{Invalid Credentials}",HttpStatus.BAD_REQUEST);

	}

	// UPDATE
	@PutMapping(value = "/update")
	public ResponseEntity<String> userUpdate(HttpServletRequest request, @RequestBody UserDetails user) {
		String token = request.getHeader("token");
		System.out.println(token);
		userService.update(token, user);
			return new ResponseEntity<String>("Updated",HttpStatus.ACCEPTED);
		
	}

	// DELETE
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<String> userDelete(HttpServletRequest request) {
		String token = request.getHeader("token");
		System.out.println(token);
		userService.deleteUser(token);
		return new ResponseEntity<String>("Deleted",HttpStatus.OK);

	}
}
