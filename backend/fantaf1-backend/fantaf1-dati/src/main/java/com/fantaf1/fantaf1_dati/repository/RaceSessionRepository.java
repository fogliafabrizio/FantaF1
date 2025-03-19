package com.fantaf1.fantaf1_dati.repository;

import com.fantaf1.fantaf1_dati.entity.RaceSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface RaceSessionRepository extends JpaRepository<RaceSession, Long> {

    @Modifying
    @Transactional
    void deleteByRaceId(Long id);

    List<RaceSession> findAllByRaceIdOrderBySessionDateTimeAsc(Long id);
}
