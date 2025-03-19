package com.fantaf1.fantaf1_dati.service;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.entity.PilotCostHistory;
import com.fantaf1.fantaf1_dati.entity.PilotCurrentCredit;
import com.fantaf1.fantaf1_dati.mapper.ErgastToEntity;
import com.fantaf1.fantaf1_dati.model.ergast.ErgastResponse;
import com.fantaf1.fantaf1_dati.model.ergast.PilotsReponse;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastDriver;
import com.fantaf1.fantaf1_dati.repository.PilotCostHistoryRepository;
import com.fantaf1.fantaf1_dati.repository.PilotCurrentCreditRepository;
import com.fantaf1.fantaf1_dati.repository.PilotRepository;
import com.fantaf1.fantaf1_dati.util.ErgastUtil;
import com.fantaf1.fantaf1_dati.util.RestTemplateUtil;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AggiornaCostoSeasonDriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PilotService {

    @Autowired
    private PilotRepository pilotRepository;

    @Autowired
    private PilotCurrentCreditRepository pilotCurrentCreditRepository;

    @Autowired
    private PilotCostHistoryRepository pilotCostHistoryRepository;

    @Autowired
    private ErgastUtil ergastUtil;

    @Autowired
    private ErgastToEntity ergastToEntity;

    @PostConstruct
    public void init() {
        updateDriversFromApi();
    }

    /**
     * 🔄 Metodo che ritorna la lista di piloti dato l'anno
     */
    public List<Pilot> getDriversBySeason(int season) {
        return pilotRepository.findAllBySeason(season);
    }

    /**
     * 🔄 Metodo che ritorna il pilota dato l'anno e l'id del pilota
     */
    public Pilot getDriverBySeasonAndDriverId(int season, String driverId) {
        updateDriversFromApi(season);
        return pilotRepository.findByDriverIdAndSeason(driverId, season).orElseThrow(() -> new RuntimeException("Pilota non trovato"));
    }

    /**
     * 🔄 Metodo che ritorna il pilota dato l'id del pilota
     */
    public Pilot getDriverById(Long id) {
        return pilotRepository.findById(id).orElseThrow(() -> new RuntimeException("Pilota non trovato"));
    }

    /**
     * 🔄 Metodo che aggiorna il costo di un pilota dato l'id del pilota
     */
    @Transactional
    public Void updateDriverCostById(Long id, int newCost) {
        Pilot pilot = getDriverById(id);
        PilotCurrentCredit pilotCurrentCredit = pilotCurrentCreditRepository.findByPilotId(pilot.getId()).orElse(new PilotCurrentCredit());

        /** Storicizza il costo del pilota */
        PilotCostHistory pilotCostHistory = new PilotCostHistory();
        pilotCostHistory.setPilot(pilot);
        pilotCostHistory.setPreviousValue(pilotCurrentCredit.getCurrentValue() != null ? pilotCurrentCredit.getCurrentValue() : 0);
        pilotCostHistory.setNewValue(newCost);
        pilotCostHistory.setSeason(pilot.getSeason());
        pilotCostHistoryRepository.save(pilotCostHistory);

        /** Aggiorna il costo del pilota */
        pilotCurrentCredit.setPilot(pilot);
        pilotCurrentCredit.setCurrentValue(newCost);
        pilotCurrentCredit.setSeason(pilot.getSeason());
        pilotCurrentCreditRepository.save(pilotCurrentCredit);

        return null;
    }

    /**
     * 🔄 Metodo che aggiorna il costo di un pilota dato l'anno e l'id del pilota
     */
    @Transactional
    public Void updateDriverCostBySeasonAndDriver(AggiornaCostoSeasonDriverRequest aggiornaCostoSeasonDriverRequest) {
        Pilot pilot = getDriverBySeasonAndDriverId(aggiornaCostoSeasonDriverRequest.getAnno(), aggiornaCostoSeasonDriverRequest.getDriverId());
        return updateDriverCostById(pilot.getId(), aggiornaCostoSeasonDriverRequest.getNuovoValore());
    }

    /**
     * 🔄 Metodo che ritorno la lista di piloti con costo dato l'anno
     */
    public Map<Pilot, PilotCurrentCredit> getDriversWithCostBySeason(int season) {
        List<Pilot> pilots = getDriversBySeason(season);
        Map<Pilot, PilotCurrentCredit> pilotCosts = new HashMap<>();
        for (Pilot pilot : pilots) {
            PilotCurrentCredit pilotCurrentCredit = pilotCurrentCreditRepository.findByPilotId(pilot.getId()).orElse(new PilotCurrentCredit());
            pilotCosts.put(pilot, pilotCurrentCredit);
        }
        return pilotCosts;
    }

    /**
     * 🔄 Metodo che chiamerà l'API per aggiornare i piloti nel database.
     */
    @Scheduled(cron = "0 0 2 * * ?") // Aggiornamento ogni giorno alle 02:00
    @Transactional
    public void updateDriversFromApi() {
        updateDriversFromApi(LocalDate.now().getYear());
    }

    private void updateDriversFromApi(int season) {
        String url = ergastUtil.getDrivers(season);
        try {
            log.info("🔄 Avvio aggiornamento piloti da API...");
            ErgastResponse<PilotsReponse> response = RestTemplateUtil.call(url, new ParameterizedTypeReference<ErgastResponse<PilotsReponse>>() {});

            if (response == null || response.getMrData() == null || response.getMrData().getDriverTable() == null) {
                log.warn("⚠️ Nessun dato ricevuto dall'API!");
                return;
            }

            // Logghiamo tutti i piloti ricevuti
            List<ErgastDriver> ergastDrivers = response.getMrData().getDriverTable().getErgastDrivers();
            log.info("📢 Piloti ricevuti: {}", ergastDrivers.size());

            for (ErgastDriver ergastDriver : ergastDrivers) {
                Optional<Pilot> existingPilot = pilotRepository.findByDriverIdAndSeason(ergastDriver.getDriverId(), season);

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
