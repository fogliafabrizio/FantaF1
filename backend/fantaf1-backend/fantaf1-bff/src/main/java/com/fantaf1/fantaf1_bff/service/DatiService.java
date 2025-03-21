package com.fantaf1.fantaf1_bff.service;

import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapper;
import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapperImpl;
import com.fantaf1.fantaf1_bff.model.PilotaConCostoDTO;
import com.fantaf1.fantaf1_bff.model.ProssimaGaraDTO;
import com.fantaf1.fantaf1_bff.model.SessionDTO;
import com.fantaf1.fantaf1_bff.model.enums.SessionType;
import jakarta.validation.constraints.NotNull;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DatiService {

    private ModelToPresentationMapper mapper = new ModelToPresentationMapperImpl();

    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();


    @Value("${fantaf1.dati.base-url}")
    private String baseUrl;

    public List<Pilota> getPilotiByAnno(int anno) {
        String url = baseUrl + "/piloti?anno=" + anno;
        ResponseEntity<Pilota[]> response = restTemplateUtil.getForObject(url, Pilota[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<PilotaConCosto> getPilotaByAnnoConCosto(int anno){
        String url = baseUrl + "/piloti/con-costo?anno=" + anno;
        ResponseEntity<PilotaConCostoDTO[]> response = restTemplateUtil.getForObject(url, PilotaConCostoDTO[].class);
        return mapper.map(response.getBody());
    }

    public LimiteSceltaResponse getLimiteScelta() {
        String url = baseUrl + "/gare/prossima-gara";
        ProssimaGaraDTO nextRace = restTemplateUtil.getForObject(url, ProssimaGaraDTO.class).getBody();
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

    public Void aggiornaCostoPilota(Integer id, AggiornaCostoRequest nuovoValore) {
        String url = baseUrl + "/piloti/" + id;
        ResponseEntity<Void> response = restTemplateUtil.postForObject(url, nuovoValore, Void.class);
        return response.getBody();
    }

    public boolean isScelteAperte(@NotNull Integer gpWeekendId) {
        return true; // TODO
    }

    public int getTotalCosto(SelezioneRequest selezioneRequest) {
        String url = baseUrl + "/piloti/totale-costo";
        ResponseEntity<Integer> response = restTemplateUtil.postForObject(url, selezioneRequest.getDriverIds(), Integer.class);
        return response.getBody();
    }
}
