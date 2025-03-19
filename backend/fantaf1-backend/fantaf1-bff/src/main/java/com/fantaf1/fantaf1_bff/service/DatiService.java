package com.fantaf1.fantaf1_bff.service;

import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapper;
import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapperImpl;
import com.fantaf1.fantaf1_bff.model.PilotaConCostoDTO;
import com.fantaf1.fantaf1_bff.model.ProssimaGaraDTO;
import com.fantaf1.fantaf1_bff.model.SessionDTO;
import com.fantaf1.fantaf1_bff.model.enums.SessionType;
import org.openapitools.model.LimiteSceltaResponse;
import org.openapitools.model.Pilota;
import org.openapitools.model.PilotaConCosto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DatiService {

    private ModelToPresentationMapper mapper = new ModelToPresentationMapperImpl();

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${fantaf1.dati.base-url}")
    private String baseUrl;

    public List<Pilota> getPilotiByAnno(int anno) {
        String url = baseUrl + "/piloti?anno=" + anno;
        ResponseEntity<Pilota[]> response = restTemplate.getForEntity(url, Pilota[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<PilotaConCosto> getPilotaByAnnoConCosto(int anno){
        String url = baseUrl + "/piloti/con-costo?anno=" + anno;
        ResponseEntity<PilotaConCostoDTO[]> response = restTemplate.getForEntity(url, PilotaConCostoDTO[].class);
        return mapper.map(response.getBody());
    }

    public LimiteSceltaResponse getLimiteScelta() {
        String url = baseUrl + "/gare/prossima-gara";
        ProssimaGaraDTO nextRace = restTemplate.getForObject(url, ProssimaGaraDTO.class);
        assert nextRace != null;
        SessionDTO[] sessions = nextRace.getSessions();
        OffsetDateTime scadenza = getScadenza(sessions);
        return mapper.map(nextRace, scadenza, isSelectionOpen(nextRace.getDateTimeRace(), scadenza));
    }

    private Boolean isSelectionOpen(OffsetDateTime dateTimeRace, OffsetDateTime scadenza) {
        OffsetDateTime now = OffsetDateTime.now();
        return scadenza != null && (now.isBefore(scadenza.minusHours(3)) || now.isAfter(dateTimeRace.plusHours(6)));
    }

    private OffsetDateTime getScadenza(SessionDTO[] sessions) {
        for(SessionDTO session : sessions) {
            if(session.getSessionType().equals(SessionType.FIRST_PRACTICE)){
                return session.getSessionDateTime();
            }
        }
        return null;
    }
}
