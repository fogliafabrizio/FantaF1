package com.fantaf1.fantaf1_bff.controller;

import com.fantaf1.fantaf1_bff.service.DatiService;
import com.fantaf1.fantaf1_bff.service.SelezioneService;
import com.fantaf1.fantaf1_bff.service.UserService;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.Fantaf1BffApi;
import org.openapitools.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BFFController implements Fantaf1BffApi {

    private final DatiService datiService;
    private final UserService userService;
    private final SelezioneService selezioneService;

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

    @Override
    public ResponseEntity<Void> aggiornaCostoPilota(Integer id, AggiornaCostoRequest aggiornaCostoRequest) {
        return ResponseEntity.ok(datiService.aggiornaCostoPilota(id, aggiornaCostoRequest));
    }

    @Override
    public ResponseEntity<SelezioneResponse> selezionePiloti(SelezioneRequest selezioneRequest) {
        if(datiService.isScelteAperte(selezioneRequest.getGpWeekendId())){
            return ResponseEntity.ok(selezioneService.selezionePiloti(selezioneRequest, datiService.getTotalCosto(selezioneRequest)));
        } else {
            throw new RuntimeException("Scelte non aperte");
        }
    }

    @Override
    public ResponseEntity<SelezioneConCreditiResponse> getSelezionePiloti(Integer gpWeekendId) {
        return ResponseEntity.ok(selezioneService.getInfoSelezione(gpWeekendId));
    }
}
