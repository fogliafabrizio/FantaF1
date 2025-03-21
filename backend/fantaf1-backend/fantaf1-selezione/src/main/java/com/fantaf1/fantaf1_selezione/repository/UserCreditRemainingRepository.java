package com.fantaf1.fantaf1_selezione.repository;

import com.fantaf1.fantaf1_selezione.entity.UserCreditRemaining;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCreditRemainingRepository extends JpaRepository<UserCreditRemaining, Long> {
    Optional<UserCreditRemaining> findByUserId(Long userIdFromContext);

    Optional<UserCreditRemaining> findByUserIdAndGpWeekendId(Long userIdFromContext, @NotNull Integer gpWeekendId);

    List<UserCreditRemaining> findAllByUserIdAndSeason(Long userIdFromContext, int year);
}
