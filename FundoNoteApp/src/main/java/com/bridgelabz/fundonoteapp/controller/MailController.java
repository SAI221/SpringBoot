package com.bridgelabz.fundonoteapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundonoteapp.model.UserDetails;
import com.bridgelabz.fundonoteapp.service.UserService;
import com.bridgelabz.fundonoteapp.util.JwtUtil;
import com.bridgelabz.fundonoteapp.util.PasswordEncryption;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") 
public class MailController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<String> forgotPassword(@RequestBody UserDetails user, HttpServletRequest request,
			HttpServletResponse response) {
		Optional<UserDetails> list = userService.findByEmailId(user.getEmail());
		if (list.isPresent()) {
			return new ResponseEntity<String>("We didn't find an account for that e-mail address.",
					HttpStatus.NOT_FOUND);
		} else {
			UserDetails userdetails = list.get();
			String token = JwtUtil.jwtToken(userdetails.getUserId());
			response.setHeader("token", token);
			String subject = "Password Reset Request";
			String appUrl = "request.getScheme() " + "://" + request.getServerName() + "/reset?token=" + token;
			return new ResponseEntity<String>(userService.sendmail(subject, userdetails, appUrl), HttpStatus.ACCEPTED);
		}
	}

	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public ResponseEntity<String> changePassword(HttpServletRequest request, @RequestBody String password) {
		String token = request.getHeader("token");

		int id = JwtUtil.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setPassword(PasswordEncryption.securePassword(password));
			userService.save(userList.get());
			return new ResponseEntity<String>("Changed", HttpStatus.RESET_CONTENT);
		} else
			return new ResponseEntity<String>("Not changed", HttpStatus.NOT_MODIFIED);

	}

	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public ResponseEntity<String> mailForActivation(HttpServletRequest request) {
		String token = request.getHeader("token");
		int userId = JwtUtil.parseJWT(token);
		Optional<UserDetails> list = userService.findById(userId);
		if (list == null) {
			return new ResponseEntity<String>("We didn't find an account for that e-mail address.",
					HttpStatus.NOT_FOUND);
		} else {
			UserDetails userdetails = list.get();

			String appUrl = "http://localhost:8080" + "/active/token=" + token;
			String subject = "To active your status";
			return new ResponseEntity<String>(userService.sendmail(subject, userdetails, appUrl), HttpStatus.ACCEPTED);
		}

	}

	@RequestMapping(value = "/active", method = RequestMethod.PUT)
	public ResponseEntity<String> activeStatus(HttpServletRequest request) {
		String token = request.getHeader("token");

		int id = JwtUtil.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setActiveStatus(1);
			userService.save(userList.get());
			return new ResponseEntity<String>("Changed", HttpStatus.OK);
		} else
			return new ResponseEntity<String>("Not changed", HttpStatus.NOT_MODIFIED);
	}

}