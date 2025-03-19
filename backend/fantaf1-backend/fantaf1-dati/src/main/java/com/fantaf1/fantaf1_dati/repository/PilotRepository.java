package com.fantaf1.fantaf1_dati.repository;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PilotRepository extends JpaRepository<Pilot, Long> {
    Optional<Pilot> findByDriverId(String driverId);

    Optional<Pilot> findByDriverIdAndSeason(String driverId, int season);

    List<Pilot> findAllBySeason(int season);

    Optional<Pilot> findById(Long id);
}
