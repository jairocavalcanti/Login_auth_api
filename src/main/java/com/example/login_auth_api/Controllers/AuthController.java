package com.example.login_auth_api.Controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_auth_api.DTO.ErrorResponseDTO;
import com.example.login_auth_api.DTO.LoginRequestDTO;
import com.example.login_auth_api.DTO.RegisterRequestDTO;
import com.example.login_auth_api.DTO.ResponseDTO;
import com.example.login_auth_api.domainuser.User;
import com.example.login_auth_api.infra.security.TokenService;
import com.example.login_auth_api.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    @PostMapping("/login")
    // ? - generic type (classes, records , lists ...)
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO("unauthorized"));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());
        if (user.isEmpty()) {
            User newuser = new User();
            newuser.setPassword(passwordEncoder.encode(body.password()));
            newuser.setEmail(body.email());
            newuser.setName(body.name());
            this.repository.save(newuser);
            String token = this.tokenService.generateToken(newuser);
            return ResponseEntity.ok(new ResponseDTO(newuser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
