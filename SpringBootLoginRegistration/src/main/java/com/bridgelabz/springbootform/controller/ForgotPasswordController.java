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

@RestController
public class ForgotPasswordController {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private UserService userService;

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
	public String forgotPassword(@RequestBody UserDetails user, HttpServletRequest request,HttpServletResponse response) {
		List<UserDetails> list = userService.findByEmailId(user.getEmail());
		if (list.size() == 0) {
			return "We didn't find an account for that e-mail address.";
		} else {
			UserDetails userdetails = list.get(0);
			String token=userService.jwtToken(userdetails.getUserId());
			response.setHeader("token",token );

			/*
			 * String appUrl = request.getScheme() + "://" + request.getServerName();
			 * 
			 * // Email message SimpleMailMessage passwordResetEmail = new
			 * SimpleMailMessage();
			 * passwordResetEmail.setFrom("saipravallika2097@gmail.com");
			 * passwordResetEmail.setTo(userdetails.getEmail());
			 * passwordResetEmail.setSubject("Password Reset Request");
			 * passwordResetEmail.setText("To reset your password, click the link below:\n"
			 * + appUrl + "/reset?token=");
			 * 
			 * sender.send(passwordResetEmail); return
			 * "A password reset link has been sent to " + user.getEmail();
			 */
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			String appUrl = request.getScheme() + "://" + request.getServerName();
			try {

				helper.setTo(userdetails.getEmail());
				helper.setText("To reset your password, click the link below:\n"
						 + appUrl + "/reset?token="+token);
				helper.setSubject("Password Reset Request");
			} catch (MessagingException e) {
				e.printStackTrace();
				return "Error while sending mail ..";
			}
			sender.send(message);
			return "Mail Sent Success!";
		}
	}

	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public String changePassword(HttpServletRequest request, @RequestBody String password) {
		String token = request.getHeader("token");
		
			int id = userService.parseJWT(token);
			if (id >=0) {
			Optional<UserDetails> userList = userService.findById(id);
			userList.get().setPassword(password);
			userService.UserRegistration(userList.get());
			return "Changed";
		}
		else
			return "Not changed";
		

	}
	 

}