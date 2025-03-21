package com.fantaf1.fantaf1_selezione.repository;

import com.fantaf1.fantaf1_selezione.entity.UserSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSelectionRepository extends JpaRepository<UserSelection, Long> {
    void deleteByUserIdAndGpWeekendId(Long userIdFromContext, Integer gpWeekendId);

}
