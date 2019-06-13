package com.wordpress.babuwant2do.bookmarkmanager.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordpress.babuwant2do.bookmarkmanager.repository.UserRepository;
import com.wordpress.babuwant2do.bookmarkmanager.security.jwt.JwtTokenProvider;
import com.wordpress.babuwant2do.bookmarkmanager.web.rest.vm.LoginVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository users;
    
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider
    		,  UserRepository users){
    	this.users = users;
    	this.authenticationManager = authenticationManager;
    	this.jwtTokenProvider = jwtTokenProvider;	
    }
    
    

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody LoginVM data) {
    	System.out.println("------ signin -------");
        try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, new ArrayList<String>());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            System.out.println("------ signin 2-------");
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
}