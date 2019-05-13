package com.bridgelabz.springbootlogin.service;

import com.bridgelabz.springbootlogin.model.User;

public interface UserService {
	public User userReg(User user);
	//public int login(User user);
	public String login(User user);
	public String securePassword(User user);
	public User saveUser(User user);
	public  User forgotPassword(User user);
	
}
