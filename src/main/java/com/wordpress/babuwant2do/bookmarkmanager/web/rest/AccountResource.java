package com.wordpress.babuwant2do.bookmarkmanager.web.rest;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordpress.babuwant2do.bookmarkmanager.domain.User;
import com.wordpress.babuwant2do.bookmarkmanager.repository.UserRepository;
import com.wordpress.babuwant2do.bookmarkmanager.service.UserService;
import com.wordpress.babuwant2do.bookmarkmanager.web.rest.vm.ManagedUserVM;


@RestController
@RequestMapping("/api")
public class AccountResource {
	
	private final UserRepository userRepository;
	private final UserService userService;
	
	public AccountResource(UserRepository userRepository, UserService userService){
		this.userRepository = userRepository;
		this.userService = userService;
	}
	
	@GetMapping("/secure/hello")
	public ResponseEntity<String> helloSecure(){
		return new ResponseEntity<String>("Secure Hello", HttpStatus.OK);
	}
	
	@GetMapping("/register/hello")
	public ResponseEntity<String> hello(){
		return new ResponseEntity<String>("Hello", HttpStatus.OK);
	}

	@PostMapping("/register/hello")
	public ResponseEntity<String> helloBack(String name){
		return new ResponseEntity<String>("Hello "+name, HttpStatus.OK);
	}
	
	
	/**
	 * URL: http://localhost:8080/api/register
	 * Method: Post
	 * Header: {"Content-Type" : "application/json"}
	 * PostData: {"login": "babu", "password": "babu", "email": "babu@abc.com"}
	 * @param managedUserVM
	 * @return
	 */
	@PostMapping(path = "/register",
	        produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE}
	)
    public ResponseEntity registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
		HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
        
		return userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
	            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
	            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
	                .map(user -> new ResponseEntity<>("email address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
	                .orElseGet(() -> {
	                    User user = userService
	                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
	                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
	                            managedUserVM.getEmail().toLowerCase());

	                    return new ResponseEntity(HttpStatus.CREATED);
	                })
	        );
	}
	
	

}
