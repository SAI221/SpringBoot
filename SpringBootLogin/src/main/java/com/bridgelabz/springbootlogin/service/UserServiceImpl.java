package com.bridgelabz.springbootlogin.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springbootlogin.model.User;
import com.bridgelabz.springbootlogin.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	public UserRepository userRepository;

	@Override
	public String login(User user) {
		System.out.println(user.getUserName());
		String encryptedPassword=securePassword(user);
		System.out.println(encryptedPassword);

		List<User> userList = userRepository.findByUserNameAndPassword(user.getUserName(),encryptedPassword );
		System.out.println(userList);

		if (userList.size() != 0) {
			System.out.println(user.getEmail());
			return "Welcome  " + user.getUserName();
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
		String password=user.getPassword();
		String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
    
		return generatedPassword;
	}

	@Override
	public User saveUser(User user) {
		User givenUser=new User();
		givenUser.setEmail(user.getEmail());
		givenUser.setFirstName(user.getFirstName());
		givenUser.setLastName(user.getLastName());
		givenUser.setMobileNo(user.getMobileNo());
		givenUser.setPassword(securePassword(user));;
		givenUser.setUserName(user.getUserName());
		userRepository.save(givenUser);
		return givenUser;
	}

}
