package com.example.login_auth_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login_auth_api.domainuser.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    
}
