package com.fantaf1.fantaf1_dati.controller;

import com.fantaf1.fantaf1_dati.mapper.EntityToPresentation;
import com.fantaf1.fantaf1_dati.service.RaceService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.GareApi;
import org.openapitools.model.NextRaceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RaceController implements GareApi {

    private final RaceService raceService;

    private final EntityToPresentation e2p;

    @Override
    public ResponseEntity<NextRaceResponse> getNextRaceWithSessions() {
        return ResponseEntity.ok(e2p.mapRace(raceService.getNextRaceWithSessions()));
    }
}
