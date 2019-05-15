package com.bridgelabz.springbootform.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootform.model.UserDetails;
import com.bridgelabz.springbootform.repository.UserRepository;
import com.bridgelabz.springbootform.token.TokenClass;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	JavaMailSender sender;
	@Autowired
	TokenClass tokenClass;

	@Override
	public UserDetails UserRegistration(UserDetails user) {
		System.out.println(securePassword(user.getPassword()));
		user.setPassword(securePassword(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<UserDetails> login(UserDetails user) {
		List<UserDetails> userList = userRepository.findByEmailAndPassword(user.getEmail(),
				securePassword(user.getPassword()));
		return userList;
	}

	public String securePassword(String password) {
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

	/*
	 * private static final Key secret =
	 * MacProvider.generateKey(SignatureAlgorithm.HS256); private static final
	 * byte[] secretBytes = secret.getEncoded(); private static final String
	 * base64SecretBytes = Base64.getEncoder().encodeToString(secretBytes);
	 * 
	 * @Override public String jwtToken(int id) {
	 * 
	 * long nowMillis = System.currentTimeMillis(); Date now = new Date(nowMillis);
	 * 
	 * JwtBuilder builder =
	 * Jwts.builder().setSubject(String.valueOf(id)).setIssuedAt(now)
	 * .signWith(SignatureAlgorithm.HS256, base64SecretBytes);
	 * 
	 * return builder.compact(); }
	 * 
	 * @Override public int parseJWT(String jwt) {
	 * 
	 * // This line will throw an exception if it is not a signed JWS (as expected)
	 * Claims claims =
	 * Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(jwt).getBody();
	 * 
	 * System.out.println("Subject: " + claims.getSubject());
	 * 
	 * return Integer.parseInt(claims.getSubject()); }
	 */

	@Override
	public UserDetails updateUser(String token, UserDetails user) {
		int varifiedUserId = tokenClass.parseJWT(token);
		Optional<UserDetails> maybeUser = userRepository.findByUserId(varifiedUserId);
		UserDetails presentUser = maybeUser.map(existingUser -> {
			existingUser.setEmail(user.getEmail() != null ? user.getEmail() : maybeUser.get().getEmail());
			existingUser.setMobileNo(user.getMobileNo() != null ? user.getMobileNo() : maybeUser.get().getMobileNo());
			existingUser.setUserName(user.getUserName() != null ? user.getUserName() : maybeUser.get().getUserName());
			existingUser.setPassword(user.getPassword() != null ? user.getPassword() : maybeUser.get().getPassword());
			return existingUser;
		}).orElseThrow(() -> new RuntimeException("User Not Found"));

		return UserRegistration(presentUser);
	}

	@Override
	public boolean deleteUser(String token) {
		int varifiedUserId = tokenClass.parseJWT(token);

		// return userRep.deleteById(varifiedUserId);
		Optional<UserDetails> maybeUser = userRepository.findByUserId(varifiedUserId);
		return maybeUser.map(existingUser -> {
			userRepository.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public List<UserDetails> findByEmailId(String email) {

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

}
