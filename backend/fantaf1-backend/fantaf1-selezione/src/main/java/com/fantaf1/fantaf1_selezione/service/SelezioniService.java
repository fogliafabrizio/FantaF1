package com.fantaf1.fantaf1_selezione.service;

import com.fantaf1.fantaf1_selezione.entity.SelectedDriver;
import com.fantaf1.fantaf1_selezione.entity.UserCredit;
import com.fantaf1.fantaf1_selezione.entity.UserCreditRemaining;
import com.fantaf1.fantaf1_selezione.entity.UserSelection;
import com.fantaf1.fantaf1_selezione.mapper.EntityToPresentationMapper;
import com.fantaf1.fantaf1_selezione.mapper.EntityToPresentationMapperImpl;
import com.fantaf1.fantaf1_selezione.repository.SelectedDriverRepository;
import com.fantaf1.fantaf1_selezione.repository.UserCreditRemainingRepository;
import com.fantaf1.fantaf1_selezione.repository.UserCreditRepository;
import com.fantaf1.fantaf1_selezione.repository.UserSelectionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.InfoSelezioneResponse;
import org.openapitools.model.SelezioneRequest;
import org.openapitools.model.SelezioneResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SelezioniService {

    private final UserCreditRepository userCreditRepository;
    private final UserCreditRemainingRepository userCreditRemainingRepository;
    private final UserSelectionRepository userSelectionRepository;
    private final SelectedDriverRepository selectedDriverRepository;

    private final EntityToPresentationMapper e2p = new EntityToPresentationMapperImpl();



    @Transactional
    public SelezioneResponse saveSelezione(SelezioneRequest selezioneRequest, Long userIdFromContext) {

        // 1. Recupera i crediti disponibili per quel weekend
        UserCredit userCredit = getUserCredit(selezioneRequest.getGpWeekendId(), userIdFromContext);

        //  Recupera i crediti rimasti nella stagione

        // 2. Verifica se ha abbastanza crediti
        int budget = 100 + getExtraCreditsUser(userIdFromContext, Long.valueOf(selezioneRequest.getGpWeekendId()));
        if(selezioneRequest.getTotalCost() > budget && selezioneRequest.getTotalCost() <= 150){
            throw new RuntimeException("Non hai abbastanza crediti");
        }

        // 3. Salva selezione
        UserCreditRemaining userCreditRemaining = userCreditRemainingRepository.findByUserIdAndGpWeekendId(userIdFromContext, selezioneRequest.getGpWeekendId())
                .orElseGet(() -> userCreditRemainingRepository.save(UserCreditRemaining.builder()
                        .userId(userIdFromContext)
                        .gpWeekendId(Long.valueOf(selezioneRequest.getGpWeekendId()))
                        .creditsRemaining(100 - selezioneRequest.getTotalCost())
                                .season(LocalDateTime.now().getYear())
                        .build()));
        userCredit.setCreditsUsed(selezioneRequest.getTotalCost());
        userCreditRepository.save(userCredit);
        userCreditRepository.flush();

        userCreditRemaining.setCreditsRemaining(100 - selezioneRequest.getTotalCost());
        userCreditRemainingRepository.save(userCreditRemaining);


        // Cancella eventuali selezioni precedenti per quel GP
        userSelectionRepository.deleteByUserIdAndGpWeekendId(userIdFromContext, selezioneRequest.getGpWeekendId());
        userSelectionRepository.flush();
        UserSelection userSelection = UserSelection.builder()
                .userId(userIdFromContext)
                .gpWeekendId(Long.valueOf(selezioneRequest.getGpWeekendId()))
                .build();

        List<SelectedDriver> selectedDrivers = selezioneRequest.getDriverIds().stream()
                .map(driverId -> SelectedDriver.builder()
                        .userSelection(userSelection) // importante per la relazione bidirezionale
                        .driverId(Long.valueOf(driverId))
                        .isJolly(false)
                        .build())
                .toList();

        userSelection.setSelectedDrivers(selectedDrivers);
        userSelectionRepository.save(userSelection);

        // 6. Risposta
        return e2p.map(userSelection);

    }

    private int getExtraCreditsUser(Long userIdFromContext, Long gpWeekendId) {
        List<UserCreditRemaining> userCredits = userCreditRemainingRepository.findAllByUserIdAndSeason(userIdFromContext, LocalDate.now().getYear());
        // Somma tutti i crediti tranne quelli del weekendId
        //  TODO: Potresti aggiungere la data e aggiungere solo quelli PRIMA del giorno della gara!
        //  TODO: Rendere ad esempio le scelte immutibili dopo il giorno della gara.
        return userCredits.stream()
                .filter(uc -> !uc.getGpWeekendId().equals(gpWeekendId))
                .mapToInt(UserCreditRemaining::getCreditsRemaining)
                .sum();
    }

    public InfoSelezioneResponse getSelezione(Integer gpWeekendId, Long userIdFromContext) {
        UserSelection selection = userSelectionRepository.findByUserIdAndGpWeekendId(userIdFromContext, Long.valueOf(gpWeekendId)).orElse(new UserSelection());
        return e2p.map(gpWeekendId, selection, getExtraCreditsUser(userIdFromContext, Long.valueOf(gpWeekendId)));
    }

    private UserCredit getUserCredit(Integer gpWeekendId, Long userIdFromContext) {
        return userCreditRepository.findByUserIdAndGpWeekendId(userIdFromContext, gpWeekendId)
                .orElseGet(() -> userCreditRepository.save(UserCredit.builder()
                        .userId(userIdFromContext)
                        .gpWeekendId(Long.valueOf(gpWeekendId))
                        .creditsUsed(0)
                        .build()));
    }
}
