package com.bridgelabz.fundonoteapp.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundonoteapp.model.Login;
import com.bridgelabz.fundonoteapp.model.UserDetails;

public interface UserService {
	
	public UserDetails save(UserDetails user);

	public UserDetails UserRegistration(UserDetails user,HttpServletRequest request);

	public List<UserDetails> login(Login email);

	public UserDetails updateUser(String token,UserDetails user);

	public boolean deleteUser(String token);

	public Optional<UserDetails> findByEmailId(String email);
	
	public Optional<UserDetails> findById(int id);
	
	public String sendmail(String subject, UserDetails userdetails,String appUrl);

	public List<UserDetails> fetchData(); 
	
	public UserDetails update(String token,UserDetails user);
}
