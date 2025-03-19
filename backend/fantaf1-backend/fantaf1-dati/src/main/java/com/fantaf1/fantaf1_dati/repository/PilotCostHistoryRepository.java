package com.fantaf1.fantaf1_dati.repository;

import com.fantaf1.fantaf1_dati.entity.PilotCostHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PilotCostHistoryRepository extends JpaRepository<PilotCostHistory, Long> {
    List<PilotCostHistory> findByPilotIdAndSeason(Long pilotId, int season);

    Optional<PilotCostHistory> findByPilotId(Long pilotId);
}
