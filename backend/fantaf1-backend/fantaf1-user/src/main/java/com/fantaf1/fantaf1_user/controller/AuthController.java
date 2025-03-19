package com.fantaf1.fantaf1_user.controller;

import com.fantaf1.fantaf1_user.mapper.IntegrationToPresentationMapper;
import com.fantaf1.fantaf1_user.mapper.IntegrationToPresentationMapperImpl;
import com.fantaf1.fantaf1_user.service.AuthService;
import org.openapitools.api.AuthApi;
import org.openapitools.model.JwtResponse;
import org.openapitools.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    private AuthService authService;

    private IntegrationToPresentationMapper i2p = new IntegrationToPresentationMapperImpl();

    @Override
    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(i2p.map(authService.login(loginRequest)));
    }
}
