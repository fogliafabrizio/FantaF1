package com.fantaf1.fantaf1_dati.repository;

import com.fantaf1.fantaf1_dati.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RaceRepository extends JpaRepository<Race, Long> {

    Optional<Race> findBySeasonAndRound(int season, int round);

    Optional<Race> findBySeasonAndCircuitId(int season, String circuitId);

    Optional<Race> findFirstByDateTimeRaceAfterOrderByDateTimeRaceAsc(LocalDateTime now);
}
