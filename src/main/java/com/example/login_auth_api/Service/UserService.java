package com.example.login_auth_api.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.login_auth_api.domainuser.User;
import com.example.login_auth_api.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository user;

    public List<User> getAllUsers() {
        return user.findAll();
    }

}
