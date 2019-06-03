package com.bridgelabz.fundonoteapp.service.impl;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundonoteapp.model.Login;
import com.bridgelabz.fundonoteapp.model.UserDetails;
import com.bridgelabz.fundonoteapp.repository.UserRepository;
import com.bridgelabz.fundonoteapp.service.UserService;
import com.bridgelabz.fundonoteapp.util.JwtUtil;
import com.bridgelabz.fundonoteapp.util.PasswordEncryption;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender sender;

	@Override
	public UserDetails UserRegistration(UserDetails user, HttpServletRequest request) {
		System.out.println(PasswordEncryption.securePassword(user.getPassword()));
		user.setPassword(PasswordEncryption.securePassword(user.getPassword()));
		userRepository.save(user);
		Optional<UserDetails> user1 = userRepository.findByEmail(user.getEmail());
		if (user1 != null) {
			System.out.println("Sucessfull reg");
			// Optional<User> maybeUser = userRep.findById(user.getId());
			String tokenGen = JwtUtil.jwtToken(user1.get().getUserId());
			UserDetails u = user1.get();
			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String appUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			appUrl = appUrl + "/activestatus/" + "token=" + tokenGen;
			System.out.println(appUrl);
			String subject = "User Activation";

			String s = sendmail(subject, u, appUrl);
			System.out.println(s);
			// return "Mail Sent Successfully";
			return u;

		} else {
			System.out.println("Not sucessful reg");
		}
		return user;
	}

	@Override
	public List<UserDetails> login(Login user) {
		List<UserDetails> userList = userRepository.findByEmailAndPassword(user.getEmail(),
				PasswordEncryption.securePassword(user.getPassword()));
		return userList;
	}

	@Override
	public UserDetails updateUser(String token, UserDetails user) {
		int varifiedUserId = JwtUtil.parseJWT(token);
		Optional<UserDetails> maybeUser = userRepository.findByUserId(varifiedUserId);
		UserDetails presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setMobileNo(user.getMobileNo() != null ? user.getMobileNo() : maybeUser.get().getMobileNo());
			existingUser.setUserName(user.getUserName() != null ? user.getUserName() : maybeUser.get().getUserName());
			existingUser.setPassword(user.getPassword() != null ? PasswordEncryption.securePassword(user.getPassword())
					: PasswordEncryption.securePassword(maybeUser.get().getPassword()));
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));

		return userRepository.save(presentUser);
	}

	@Override
	public boolean deleteUser(String token) {
		int varifiedUserId = JwtUtil.parseJWT(token);

		// return userRep.deleteById(varifiedUserId);
		Optional<UserDetails> maybeUser = userRepository.findByUserId(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRepository.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public Optional<UserDetails> findByEmailId(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	public Optional<UserDetails> findById(int id) {
		return userRepository.findByUserId(id);
	}

	public String sendmail(String subject, UserDetails userdetails, String appUrl) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {

			helper.setTo(userdetails.getEmail());
			helper.setText("To reset your password, click the link below:\n" + appUrl);
			helper.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	@Override
	public List<UserDetails> fetchData() {

		return userRepository.findAll();
	}

	@Override
	public UserDetails save(UserDetails user) {

		return userRepository.save(user);
	}

	@Override
	public UserDetails update(String token,UserDetails user) {
		int varifiedUserId = JwtUtil.parseJWT(token);
		if(varifiedUserId==user.getUserId()) 
			return userRepository.save(user);
		return user;
		
		
		
		
	}

}
