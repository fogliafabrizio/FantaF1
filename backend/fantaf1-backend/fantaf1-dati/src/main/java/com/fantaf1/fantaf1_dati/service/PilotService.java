package com.fantaf1.fantaf1_dati.service;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.mapper.ErgastToEntity;
import com.fantaf1.fantaf1_dati.model.ergast.ErgastResponse;
import com.fantaf1.fantaf1_dati.model.ergast.PilotsReponse;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastDriver;
import com.fantaf1.fantaf1_dati.repository.PilotRepository;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PilotService {

    @Autowired
    private PilotRepository pilotRepository;

    @Autowired
    private ErgastUtil ergastUtil;

    @Autowired
    private ErgastToEntity ergastToEntity;

    @PostConstruct
    public void init() {
        updateDriversFromApi();
    }

    /**
     * 🔄 Metodo che chiamerà l'API per aggiornare i piloti nel database.
     */
    @Scheduled(cron = "0 0 12 1 * ?") // Aggiornamento ogni primo del mese alle 12:00
    @Transactional
    protected void updateDriversFromApi() {
        log.info("🔄 Avvio aggiornamento piloti da API...");

        try {
            int season = LocalDate.now().getYear();
            String url = ergastUtil.getDrivers(season);
            ErgastResponse<PilotsReponse> response = RestTemplateUtil.call(url, new ParameterizedTypeReference<ErgastResponse<PilotsReponse>>() {});

            if (response == null || response.getMrData() == null || response.getMrData().getDriverTable() == null) {
                log.warn("⚠️ Nessun dato ricevuto dall'API!");
                return;
            }

            // Logghiamo tutti i piloti ricevuti
            List<ErgastDriver> ergastDrivers = response.getMrData().getDriverTable().getErgastDrivers();
            log.info("📢 Piloti ricevuti: {}", ergastDrivers.size());

            for (ErgastDriver ergastDriver : ergastDrivers) {
                Optional<Pilot> existingPilot = pilotRepository.findByDriverId(ergastDriver.getDriverId());

                Pilot pilot;
                if (existingPilot.isPresent()) {
                    // 🔄 Aggiorniamo il pilota esistente
                    pilot = existingPilot.get();
                    log.info("🔄 Aggiornamento pilota: {} {}", pilot.getFirstName(), pilot.getLastName());
                    ergastToEntity.update(pilot, ergastDriver);
                } else {
                    // ✅ Creiamo un nuovo pilota
                    pilot = ergastToEntity.map(ergastDriver, season);
                    log.info("✅ Nuovo pilota: {} {}", pilot.getFirstName(), pilot.getLastName());
                }

                pilot.setSeason(season); // Aggiorniamo sempre la stagione
                pilotRepository.save(pilot);
            }

            log.info("✅ Lista piloti aggiornata con successo!");

        } catch (Exception e) {
            log.error("❌ Errore durante l'aggiornamento dei piloti: {}", e.getMessage(), e);
        }
    }


}
