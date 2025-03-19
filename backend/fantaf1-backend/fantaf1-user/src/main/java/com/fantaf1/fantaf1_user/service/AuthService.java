package com.fantaf1.fantaf1_user.service;

import com.fantaf1.fantaf1_user.entity.User;
import com.fantaf1.fantaf1_user.mapper.IntegrationToPresentationMapper;
import com.fantaf1.fantaf1_user.repository.UserRepository;
import com.fantaf1.fantaf1_user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.LoginRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if(bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            return jwtUtil.generateJwt(user.getUsername(), user.getRole().getRole());
        } else {
            throw new RuntimeException("Password not valid");
        }
    }
}
