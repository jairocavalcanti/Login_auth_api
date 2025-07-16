package com.example.login_auth_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.login_auth_api.domainuser.User;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    // Metodo Optional do tipo user que retorna usuario referente ao email
    // Optional method of user type which returns de user catched by String email 
    Optional<User> findByEmail(String email);
}
