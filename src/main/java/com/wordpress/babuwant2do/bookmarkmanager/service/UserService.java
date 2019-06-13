package com.wordpress.babuwant2do.bookmarkmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wordpress.babuwant2do.bookmarkmanager.domain.User;
import com.wordpress.babuwant2do.bookmarkmanager.repository.UserRepository;


@Service
public class UserService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	
	public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository){
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	public User createUser(String login, String password, 
			String firstName, String lastName, String email ) {

	        User newUser = new User();
	        String encryptedPassword = passwordEncoder.encode(password);
	        
	        newUser.setLogin(login);
	        newUser.setPassword(encryptedPassword);
	        newUser.setFirstName(firstName);
	        newUser.setLastName(lastName);
	        newUser.setEmail(email);
//	        newUser.setActivated(true);
	        	        
	        userRepository.save(newUser);
	        
	        //insert Log	        
	        return newUser;
	    }
	
	public User getUser(Long id){
		return this.userRepository.findById(id).orElse(null);
	}
	
	public User getUserByLogin(String login){
		return this.userRepository.findOneByLogin(login).orElse(null);
	}

	
}
