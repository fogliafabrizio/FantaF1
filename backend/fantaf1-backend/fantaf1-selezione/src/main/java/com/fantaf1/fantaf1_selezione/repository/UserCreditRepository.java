package com.fantaf1.fantaf1_selezione.repository;

import com.fantaf1.fantaf1_selezione.entity.UserCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCreditRepository extends JpaRepository<UserCredit, Long> {
    Optional<UserCredit> findByUserIdAndGpWeekendId(Long userIdFromContext, Integer gpWeekendId);

    Optional<UserCredit> findByUserId(Long userIdFromContext);

    void deleteByUserIdAndGpWeekendId(Long userIdFromContext, Integer gpWeekendId);

}
