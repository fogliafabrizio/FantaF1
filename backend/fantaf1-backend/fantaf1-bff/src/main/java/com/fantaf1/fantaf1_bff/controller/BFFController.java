package com.fantaf1.fantaf1_bff.controller;

import com.fantaf1.fantaf1_bff.service.DatiService;
import com.fantaf1.fantaf1_bff.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.Fantaf1BffApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BFFController implements Fantaf1BffApi {

    private final DatiService datiService;
    private final UserService userService;

    @Override
    public ResponseEntity<LimiteSceltaResponse> getLimiteScelta() {
        return ResponseEntity.ok(datiService.getLimiteScelta());
    }

    @Override
    public ResponseEntity<List<PilotaConCosto>> getPilotiConCostoFromDati(Integer anno) {
        return ResponseEntity.ok(datiService.getPilotaByAnnoConCosto(anno));
    }

    @Override
    public ResponseEntity<List<Pilota>> getPilotiFromDati(Integer anno) {
        return ResponseEntity.ok(datiService.getPilotiByAnno(anno));
    }

    @Override
    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
