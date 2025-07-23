package com.example.login_auth_api.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_auth_api.Service.UserService;
import com.example.login_auth_api.domainuser.User;

@RestController
@RequestMapping("/user")
public class UserController {
   
    @Autowired
    private UserService service;

    @GetMapping("/get")
    public List<User> getUser(){
        return service.getAllUsers();
    }
}
