package com.fantaf1.fantaf1_bff.service;

import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapper;
import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapperImpl;
import com.fantaf1.fantaf1_bff.model.LoginDTO;
import org.openapitools.model.JwtResponse;
import org.openapitools.model.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private ModelToPresentationMapper mapper = new ModelToPresentationMapperImpl();

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    @Value("${fantaf1.user.base-url}")
    private String baseUrl;


    public JwtResponse login(LoginRequest loginRequest) {
        ResponseEntity<LoginDTO> response = restTemplateUtil.postForObjectWithoutToken(baseUrl + "/auth/login", loginRequest, LoginDTO.class);
        return mapper.map(response.getBody());
    }
}
