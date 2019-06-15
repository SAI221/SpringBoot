package com.bridgelabz.springbootform.token;

public interface TokenClass {

	

	public String jwtToken(int id);

	public int parseJWT(String jwt);
}
