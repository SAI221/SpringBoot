package com.bridgelabz.springbootlogin.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootlogin.model.User;
import com.bridgelabz.springbootlogin.repository.UserRepository;
import com.sun.mail.smtp.SMTPTransport;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepository userRepository;

	@Override
	public String login(User user) {
		System.out.println(user.getUserName());
		/*
		 * String encryptedPassword = securePassword(user);
		 * System.out.println(encryptedPassword);
		 */

		List<User> userList = userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword()/*encryptedPassword*/);
		System.out.println(userList);

		if (userList.size() != 0) {
			System.out.println(user.getEmail());

			return "Welcome";
		} else
			return "invalid User Details";

		// return userList.size();
	}

	@Override
	public User userReg(User user) {
		userRepository.save(user);
		return user;
	}

	@Override
	public String securePassword(User user) {
		String password = user.getPassword();
		String generatedPassword = null;
		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			// Add password bytes to digest
			md.update(password.getBytes());
			// Get the hash's bytes
			byte[] bytes = md.digest();
			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println(generatedPassword);

		return generatedPassword;
	}

	@Override
	public User saveUser(User user) {
		/*
		 * User givenUser = new User(); givenUser.setEmail(user.getEmail());
		 * givenUser.setFirstName(user.getFirstName());
		 * givenUser.setLastName(user.getLastName());
		 * givenUser.setMobileNo(user.getMobileNo());
		 * givenUser.setPassword(securePassword(user));
		 * givenUser.setUserName(user.getUserName());
		 */
		user.setPassword(securePassword(user));
		userRepository.save(user);
		return user;
	}

	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
	private static final byte[] secretBytes = secret.getEncoded();
	private static final String base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);

	public String jwtToken(String subject) {

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(now).signWith(SignatureAlgorithm.HS256,
				base64SecretBytes);

		return builder.compact();
		// return
		// Jwts.parser().setSigningKey(signingKey).parseClaimsJws(user.getPassword()).getBody().getSubject();
	}

	// Sample method to validate and read the JWT
	public void parseJWT(String jwt) {

		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(jwt).getBody();

		System.out.println("Subject: " + claims.getSubject());
	}
	@Override
	public  User forgotPassword(User user) {
		
		String to="saipravallika221@gmail.com";
		
		String from="divyaprasannakumari01@gmail.com";
		
		
		
		//Get the system properties
		  final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		Properties props= System.getProperties();
		
		// set up the mail server
		
		 props.setProperty("mail.smtp.host", "smtp.gmail.com");
	     props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	     props.setProperty("mail.smtp.socketFactory.fallback", "false");
	     props.setProperty("mail.smtp.port", "465");
	     props.setProperty("mail.smtp.socketFactory.port", "465");
	     props.put("mail.smtp.auth", "true");
	     props.put("mail.debug", "true");
	     props.put("mail.store.protocol", "pop3");
	     props.put("mail.transport.protocol", "smtp");
	     String protocol = props.getProperty("mail.transport.protocol");
	     String mailhost = props.getProperty("mail.smtp.host");
		//get the default Session Object
		
		Session session=Session.getDefaultInstance(props);
		
		try {
			
			//Create a default  MimeMEssage Object
			
			MimeMessage message= new MimeMessage(session);
			
			//set FROM: header field of the header
			
			message.setFrom(new InternetAddress(from));
			
			// Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	         SMTPTransport t = (SMTPTransport)session.getTransport(protocol);

	            

	         // Set Subject: header field
	         message.setSubject("JMS!");

	         // Now set the actual message
	         message.setText("Hi!!!");
	         try {
	                t.connect(mailhost, from, "divya@12345");
	                t.sendMessage(message, message.getAllRecipients());
	            } finally {
	                    t.close();
	            }
	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
			
			
			
		}
		return user;
		
		
		
	}



}
