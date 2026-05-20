package com.smemanager.service;

import com.smemanager.dto.AuthDto;
import com.smemanager.entity.User;
import com.smemanager.enums.UserRole;
import com.smemanager.repository.UserRepository;
import com.smemanager.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    // Use ApplicationContext to get AuthenticationManager lazily
    // — avoids another circular dependency with SecurityConfig
    @Autowired
    private ApplicationContext applicationContext;

    private AuthenticationManager getAuthManager() {
        return applicationContext.getBean(AuthenticationManager.class);
    }

    public AuthDto.AuthResponse login(AuthDto.LoginRequest req) {

        getAuthManager().authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(user);

        AuthDto.AuthResponse response = new AuthDto.AuthResponse();
        response.setToken(token);
        response.setRole(user.getRole().name());
        response.setFullName(user.getFullName());
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());

        return response;
    }

    public AuthDto.AuthResponse register(AuthDto.RegisterRequest req) {

        if (userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setFullName(req.getFullName());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(UserRole.valueOf(req.getRole().toUpperCase()));
        user.setDepartment(req.getDepartment());
        user.setPhone(req.getPhone());
        user.setTeamsId(req.getTeamsId());
        user.setActive(true);
        user = userRepo.save(user);

        String token = jwtService.generateToken(user);

        AuthDto.AuthResponse response = new AuthDto.AuthResponse();
        response.setToken(token);
        response.setRole(user.getRole().name());
        response.setFullName(user.getFullName());
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());

        return response;
    }
}