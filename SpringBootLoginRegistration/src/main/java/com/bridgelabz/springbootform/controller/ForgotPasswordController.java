package com.bridgelabz.springbootform.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.springbootform.model.UserDetails;

public class ForgotPasswordController {
	 @Autowired
	    private JavaMailSender sender;

	@RequestMapping(value="/sendmail")
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
}
