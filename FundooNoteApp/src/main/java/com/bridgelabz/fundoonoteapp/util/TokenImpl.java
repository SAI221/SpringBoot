package com.bridgelabz.fundoonoteapp.util;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenImpl implements TokenClass {
	

	public String jwtToken(int id) {

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder().setSubject(String.valueOf(id)).setIssuedAt(now).signWith(SignatureAlgorithm.HS256,
				TokenClass.base64SecretBytes);

		return builder.compact();
	}
	
	
	public int parseJWT(String jwt) {

		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(TokenClass.base64SecretBytes).parseClaimsJws(jwt).getBody();

		System.out.println("Subject: " + claims.getSubject());

		return Integer.parseInt(claims.getSubject());
	}

}
