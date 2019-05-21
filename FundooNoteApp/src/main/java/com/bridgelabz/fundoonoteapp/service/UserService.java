package com.bridgelabz.fundoonoteapp.service;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoonoteapp.model.UserDetails;

public interface UserService {

	public UserDetails UserRegistration(UserDetails user,HttpServletRequest request);

	public List<UserDetails> login(UserDetails user);

	public UserDetails updateUser(String token,UserDetails user);

	public boolean deleteUser(String token);
	
	public String securePassword(String password);

	public List<UserDetails> findByEmailId(String email);
	
	public Optional<UserDetails> findById(int id);
	
	public String sendmail(String subject, UserDetails userdetails,String appUrl);

	public List<UserDetails> fetchData(); 
}
