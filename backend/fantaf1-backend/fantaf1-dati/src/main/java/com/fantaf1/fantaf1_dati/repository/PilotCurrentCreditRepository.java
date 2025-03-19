package com.fantaf1.fantaf1_dati.repository;

import com.fantaf1.fantaf1_dati.entity.PilotCurrentCredit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PilotCurrentCreditRepository extends JpaRepository<PilotCurrentCredit, Long> {
    Optional<PilotCurrentCredit> findByPilotIdAndSeason(Long pilotId, int season);

    Optional<PilotCurrentCredit> findByPilotId(Long pilotId);

    Optional<PilotCurrentCredit> findById(Long id);
}
