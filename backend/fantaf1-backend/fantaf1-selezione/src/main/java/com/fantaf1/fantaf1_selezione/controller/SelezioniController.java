package com.fantaf1.fantaf1_selezione.controller;

import com.fantaf1.fantaf1_selezione.service.SelezioniService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.openapitools.api.SelezioniApi;
import org.openapitools.model.SelezioneRequest;
import org.openapitools.model.SelezioneResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SelezioniController implements org.openapitools.api.SelezioniApi {

    private final SelezioniService selezioniService;

    @Override
    public ResponseEntity<SelezioneResponse> getSelezione(Integer gpWeekendId) {
        return SelezioniApi.super.getSelezione(gpWeekendId);
    }

    @Override
    public ResponseEntity<SelezioneResponse> salvaSelezione(SelezioneRequest selezioneRequest) {
        return ResponseEntity.ok(selezioniService.saveSelezione(selezioneRequest, getUserIdFromContext()));
    }

    private Long getUserIdFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("userId");
        }
        throw new RuntimeException("UserId not found in JWT");
    }
}
