package com.wordpress.babuwant2do.bookmarkmanager.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wordpress.babuwant2do.bookmarkmanager.domain.User;
import com.wordpress.babuwant2do.bookmarkmanager.repository.UserRepository;


@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetails = this.users.findOneByLogin(username);
        System.out.println("---- loadUserByUsername ---- "+ userDetails.get().getLogin());
        return userDetails.map(user -> {
        	List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        	return new org.springframework.security.core.userdetails.User(username,
                    user.getPassword(),
                    grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("Login: " + username + " not found"));
    }
}