package com.example.login_auth_api.infra.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.login_auth_api.domainuser.User;
import com.example.login_auth_api.repositories.UserRepository;

// CustomUserDetailsService implementing UserDetailsService interface
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   
       // User returned by query "findbyemail" in user repository
       User user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
       // returning object UserDetails with user permissions and credentials and a role list
       return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
    
}
