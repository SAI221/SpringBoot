package com.bridgelabz.springbootform.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.repository.UserRepository;
import com.bridgelabz.springbootform.service.UserService;
import com.bridgelabz.springbootform.token.TokenClass;

@RestController
public class MailController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	TokenClass tokenClass;

	@PostMapping(value = "/forgot/{token}")
	public String forgotPassword(UserDetails user, HttpServletRequest request, HttpServletResponse response,
			@PathVariable String token) {
		List<UserDetails> list = userService.findByEmailId(user.getEmail());
		if (!list.isEmpty()) {
			UserDetails userdetails = list.get(0);

			response.setHeader("token", token);
			String subject = "Password Reset Request";
			String appUrl = "request.getScheme() " + "://" + request.getServerName() + "/reset?token=" + token;
			return userService.sendmail(subject, userdetails, appUrl);

		}

		return "We didn't find an account for that e-mail address.";

	}

	@PutMapping(value = "/reset/{token}")
	public String changePassword(HttpServletRequest request, String password, @PathVariable String token) {

		int id = tokenClass.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setPassword(userService.securePassword(password));
			userRepository.save(userList.get());
			return "Changed";
		}
		return "Not changed";

	}

	@PostMapping(value = "/mail/{token}")
	public String mailForActivation(HttpServletRequest request, @PathVariable String token) {

		int userId = tokenClass.parseJWT(token);
		Optional<UserDetails> list = userService.findById(userId);
		if (list.isPresent()) {
			UserDetails userdetails = list.get();

			String appUrl = "http://localhost:8080" + "/active/token=" + token;
			String subject = "To active your status";
			return userService.sendmail(subject, userdetails, appUrl);
		}

		return "We didn't find an account for that e-mail address.";

	}

	@PutMapping(value = "/active/{token}")
	public String activeStatus(HttpServletRequest request, @PathVariable String token) {

		int id = tokenClass.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setActiveStatus(1);
			userRepository.save(userList.get());
			return "Changed";
		}
		return "Not changed";
	}

}