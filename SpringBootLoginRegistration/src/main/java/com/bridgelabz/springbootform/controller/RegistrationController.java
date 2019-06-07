package com.bridgelabz.springbootform.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.service.UserService;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@RestController

public class RegistrationController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public UserDetails createStudent(@RequestBody UserDetails user,HttpServletRequest request) {
		return userService.UserRegistration(user,request);
	}
	
	@RequestMapping(value="/fetching",method=RequestMethod.GET)
	public List<UserDetails> fetchingData(){
		return userService.fetchData();
	}
	
	@Cacheable(value = "users", key = "#UserDetails")
	@GetMapping("/testRedis/{email}")
	//@ApiResponse(response = String.class, message = "Test Redis	", code = 200)
	public String testRedis(@ApiParam("userId") @PathVariable String email) {
	List<UserDetails> user= userService.findByEmailId(email);
	return "Success" + user.get(0);
	}

	//@CachePut(key = "test")
	@Cacheable(value = "users", key = "#user")
	@PostMapping("/testRedis/{email}")
	@ApiResponse(response = String.class, message = "Test Redis post", code = 200)
	public String postRedis(@ApiParam("email") @PathVariable String email) {
		List<UserDetails> user= userService.findByEmailId(email);
		System.out.println(user.get(0));
		
	return "{}"+user;
	}

}
