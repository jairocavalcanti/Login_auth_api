package com.example.login_auth_api.infra.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.login_auth_api.domainuser.User;
import com.example.login_auth_api.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    // abstract obrigatory method from oncePerRequestFilter
    //HTTP ServletRequest: incoming request from the client
    //HTTP ServletResponse: send the response back to client
    //Filter Chain: send the request and response to controller
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth") || path.equals("/user")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoverToken(request);

        if (token != null) {
            String login = tokenService.validateToken(token);

            if (login != null) {
                User user = userRepository.findByEmail(login)
                        .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        var token = this.recoverToken(request);
        var login = tokenService.validateToken(token);

        if (login != null) {
            //search the users on database
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found"));
            //creating roles for users
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            //creating auth object contenting user and roles
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
            //set auth for the objects
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
    */
   
    private String recoverToken(HttpServletRequest request) {
        // Campo referente ao valor "Authorization" é equivalente ao valor do token
        // criado pelo metodo generate token
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer", "");
    }

}
