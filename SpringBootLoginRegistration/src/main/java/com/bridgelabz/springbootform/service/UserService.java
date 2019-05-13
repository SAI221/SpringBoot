package com.bridgelabz.springbootform.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.springbootform.model.UserDetails;

public interface UserService {

	public UserDetails UserRegistration(UserDetails user);

	public List<UserDetails> login(UserDetails user);

	public UserDetails updateUser(String token,UserDetails user);

	public boolean deleteUser(String token);

	public String jwtToken(int id);

	public int parseJWT(String jwt);
	
	public List<UserDetails> findByEmailId(String email);
	
	public Optional<UserDetails> findById(int id);
	
	
}
