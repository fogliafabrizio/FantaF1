package com.fantaf1.fantaf1_selezione.repository;

import com.fantaf1.fantaf1_selezione.entity.SelectedDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedDriverRepository extends JpaRepository<SelectedDriver, Long> {
}
