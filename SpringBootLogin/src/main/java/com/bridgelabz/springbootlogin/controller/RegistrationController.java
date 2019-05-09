package com.bridgelabz.springbootlogin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootlogin.model.User;
import com.bridgelabz.springbootlogin.service.UserService;

@RestController
public class RegistrationController {
@Autowired
UserService userservice;
//registration
@RequestMapping(value = "/registration", method = RequestMethod.POST)
public User createStudent(@RequestBody User user)
{
    return userservice.saveUser(user);
}
}
