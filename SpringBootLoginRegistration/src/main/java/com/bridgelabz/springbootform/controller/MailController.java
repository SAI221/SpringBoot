package com.bridgelabz.springbootform.controller;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.service.UserService;
import com.bridgelabz.springbootform.token.TokenClass;

@RestController
public class MailController {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private UserService userService;

	@Autowired
	TokenClass tokenClass;

	@RequestMapping(value = "/sendMail")
	public String sendMail(@RequestBody UserDetails user) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(user.getEmail());
			helper.setText("Greetings :)");
			helper.setSubject("Mail From Spring Boot");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String forgotPassword(@RequestBody UserDetails user, HttpServletRequest request,
			HttpServletResponse response) {
		List<UserDetails> list = userService.findByEmailId(user.getEmail());
		if (list.size() == 0) {
			return "We didn't find an account for that e-mail address.";
		} else {
			UserDetails userdetails = list.get(0);
			String token = tokenClass.jwtToken(userdetails.getUserId());
			response.setHeader("token", token);
			String subject = "Password Reset Request";
			String appUrl = "request.getScheme() " + "://" + request.getServerName() + "/reset?token=" + token;
			return userService.sendmail(subject, userdetails, appUrl);
		}
	}

	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public String changePassword(HttpServletRequest request, @RequestBody String password) {
		String token = request.getHeader("token");

		int id = tokenClass.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setPassword(password);
			userService.UserRegistration(userList.get());
			return "Changed";
		} else
			return "Not changed";

	}

	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public String mailForActivation(HttpServletRequest request) {
		String token = request.getHeader("token");
		int userId = tokenClass.parseJWT(token);
		Optional<UserDetails> list = userService.findById(userId);
		if (list == null) {
			return "We didn't find an account for that e-mail address.";
		} else {
			UserDetails userdetails = list.get();

			String appUrl = "http://localhost:8080" + "/active/token=" + token;
			String subject = "To active your status";
			return userService.sendmail(subject, userdetails, appUrl);
		}

	}

	@RequestMapping(value = "/active", method = RequestMethod.PUT)
	public String activeStatus(HttpServletRequest request) {
		String token = request.getHeader("token");

		int id = tokenClass.parseJWT(token);
		if (id >= 0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setActiveStatus(1);
			userService.UserRegistration(userList.get());
			return "Changed";
		} else
			return "Not changed";
	}

}