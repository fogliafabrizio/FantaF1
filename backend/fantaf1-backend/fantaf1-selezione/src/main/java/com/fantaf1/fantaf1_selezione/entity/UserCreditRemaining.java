package com.fantaf1.fantaf1_selezione.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_credit_remaining", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "gpWeekendId"}) // Impedisce ID duplicati
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreditRemaining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long gpWeekendId;

    @Column(nullable = false)
    private int creditsRemaining = 0;

    @Column(nullable = false)
    private int season = LocalDate.now().getYear();

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
