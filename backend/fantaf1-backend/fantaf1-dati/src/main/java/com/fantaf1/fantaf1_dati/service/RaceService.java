package com.fantaf1.fantaf1_dati.service;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.entity.Race;
import com.fantaf1.fantaf1_dati.entity.RaceSession;
import com.fantaf1.fantaf1_dati.mapper.ErgastToEntity;
import com.fantaf1.fantaf1_dati.model.SessionType;
import com.fantaf1.fantaf1_dati.model.ergast.ErgastResponse;
import com.fantaf1.fantaf1_dati.model.ergast.RacesResponse;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastRace;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastSession;
import com.fantaf1.fantaf1_dati.repository.RaceRepository;
import com.fantaf1.fantaf1_dati.repository.RaceSessionRepository;
import com.fantaf1.fantaf1_dati.util.ErgastUtil;
import com.fantaf1.fantaf1_dati.util.RestTemplateUtil;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class RaceService {

    @Autowired
    private ErgastUtil ergastUtil;

    @Autowired
    private ErgastToEntity e2e;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private RaceSessionRepository sessionRepository;

    @PostConstruct
    public void init() {
        updateRacesFromApi(LocalDate.now().getYear());
    }

    public Map<Race, List<RaceSession>> getNextRaceWithSessions() {
        LocalDateTime now = LocalDateTime.now();
        Optional<Race> nextRaceOpt = raceRepository.findFirstByDateTimeRaceAfterOrderByDateTimeRaceAsc(now);

        if (nextRaceOpt.isEmpty()) {
            return Collections.emptyMap();
        }

        Race nextRace = nextRaceOpt.get();
        List<RaceSession> sessions = sessionRepository.findAllByRaceIdOrderBySessionDateTimeAsc(nextRace.getId());

        return Map.of(nextRace, sessions);
    }

    /**
     * 🔄 Metodo che chiamerà l'API per aggiornare le gare nel database.
     */
    @Scheduled(cron = "0 0 2 * * ?") // Aggiornamento ogni giorno alle 02:00
    @Transactional
    public void updateRacesFromApi() {
        updateRacesFromApi(LocalDate.now().getYear());
    }

    private void updateRacesFromApi(int season) {
        String url = ergastUtil.getRaces(season);
        try {
            log.info("🔄 Aggiornamento delle gare per la stagione {}...", season);
            ErgastResponse<RacesResponse> response = RestTemplateUtil.call(url, new ParameterizedTypeReference<ErgastResponse<RacesResponse>>() {});

            if (response == null || response.getMrData() == null || response.getMrData().getRaceTable() == null) {
                log.warn("⚠️ Nessun dato ricevuto dall'API!");
                return;
            }

            // Logghiamo tutti i piloti ricevuti
            List<ErgastRace> ergastDrivers = response.getMrData().getRaceTable().getErgastRaces();
            log.info("📢 Gare ricevute: {}", ergastDrivers.size());

            for(ErgastRace ergastRace : ergastDrivers) {
                log.info("🏁 {}", ergastRace.getRaceName());

                Optional<Race> existingRace = raceRepository.findBySeasonAndCircuitId(season, ergastRace.getCircuit().getCircuitId());
                Race race;
                if(existingRace.isPresent()){
                    // 🔄 Aggiorniamo la gara esistente
                    race = existingRace.get();
                    log.info("🔄 Aggiornamento gara: {}", race.getRaceName());
                    race = e2e.update(race, ergastRace);
                } else {
                    // ✅ Creiamo una nuova gara
                    race = e2e.map(ergastRace, season);
                    log.info("✅ Nuova gara: {}", race.getRaceName());
                }

                race.setSeason(season);
                raceRepository.save(race);
                raceRepository.flush();

                // 🗑️ Elimina sessioni esistenti
                sessionRepository.deleteByRaceId(race.getId());

                // ➕ Inserisci sessioni nuove se presenti
                saveSessionIfPresent(race, ergastRace.getFirstPractice(), SessionType.FIRST_PRACTICE);
                saveSessionIfPresent(race, ergastRace.getSecondPractice(), SessionType.SECOND_PRACTICE);
                saveSessionIfPresent(race, ergastRace.getThirdPractice(), SessionType.THIRD_PRACTICE);
                saveSessionIfPresent(race, ergastRace.getQualifying(), SessionType.QUALIFYING);
                saveSessionIfPresent(race, ergastRace.getSprint(), SessionType.SPRINT);
                saveSessionIfPresent(race, ergastRace.getSprintQualifying(), SessionType.SPRINT_QUALIFYING);
            }

            log.info("✅ Lista gare aggiornata con successo!");

        } catch (Exception e) {
            log.error("❌ Errore durante l'aggiornamento delle gare: {}", e.getMessage(), e);
        }
    }

    private void saveSessionIfPresent(Race race, ErgastSession session, SessionType sessionType) {
        if(session != null) {
            sessionRepository.save(e2e.map(race, session, sessionType));
        }

    }
}
