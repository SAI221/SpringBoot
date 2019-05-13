package com.bridgelabz.springbootform.service;

import java.util.List;

import com.bridgelabz.springbootform.model.UserDetails;

public interface UserService {
	
	public UserDetails UserRegistration(UserDetails user);

	public List<UserDetails> login(UserDetails user);
	
	public UserDetails updateUser(UserDetails user);
	
	public void deleteUser(String userName);
	
	public String jwtToken(String subject) ;

	public String parseJWT(String jwt);
}
