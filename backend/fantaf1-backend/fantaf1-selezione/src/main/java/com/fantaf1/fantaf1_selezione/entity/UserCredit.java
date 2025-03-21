package com.fantaf1.fantaf1_selezione.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_credits", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "gpWeekendId"}) // Impedisce ID duplicati
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long gpWeekendId;

    @Column(nullable = false)
    private int creditsUsed = 0;

    @Column(nullable = false)
    private boolean creditPenaltyApplied = false;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
