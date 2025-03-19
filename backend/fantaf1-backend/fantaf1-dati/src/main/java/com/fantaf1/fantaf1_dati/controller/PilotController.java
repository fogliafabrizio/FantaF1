package com.fantaf1.fantaf1_dati.controller;

import com.fantaf1.fantaf1_dati.mapper.EntityToPresentation;
import com.fantaf1.fantaf1_dati.service.PilotService;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.PilotiApi;
import org.openapitools.model.AggiornaCostoRequest;
import org.openapitools.model.AggiornaCostoSeasonDriverRequest;
import org.openapitools.model.Pilota;
import org.openapitools.model.PilotaConCosto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PilotController implements PilotiApi {

    private final EntityToPresentation e2p;

    private final PilotService pilotService;

    @Override
    public ResponseEntity<Pilota> getPilotaById(Integer id) {
        return ResponseEntity.ok(e2p.map(pilotService.getDriverById(Long.valueOf(id))));
    }

    @Override
    public ResponseEntity<Pilota> getPilotaBySeasonAndDriverId(Integer anno, String driverId) {
        return ResponseEntity.ok(e2p.map(pilotService.getDriverBySeasonAndDriverId(anno, driverId)));
    }

    @Override
    public ResponseEntity<List<Pilota>> getPilotiByAnno(Integer anno) {
        return ResponseEntity.ok(e2p.map(pilotService.getDriversBySeason(anno)));
    }

    @Override
    public ResponseEntity<Void> updatePilotCostById(Integer id, AggiornaCostoRequest aggiornaCostoRequest) {
        return ResponseEntity.ok(pilotService.updateDriverCostById(Long.valueOf(id), aggiornaCostoRequest.getNuovoValore()));
    }

    @Override
    public ResponseEntity<Void> updatePilotCostBySeasonAndDriver(AggiornaCostoSeasonDriverRequest aggiornaCostoSeasonDriverRequest) {
        return ResponseEntity.ok(pilotService.updateDriverCostBySeasonAndDriver(aggiornaCostoSeasonDriverRequest));
    }

    @Override
    public ResponseEntity<List<PilotaConCosto>> getPilotiConCostoByAnno(Integer anno) {
        return ResponseEntity.ok(e2p.map(pilotService.getDriversWithCostBySeason(anno)));
    }
}
