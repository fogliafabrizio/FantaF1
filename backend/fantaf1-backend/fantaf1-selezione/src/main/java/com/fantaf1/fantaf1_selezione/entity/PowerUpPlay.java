package com.fantaf1.fantaf1_selezione.entity;

import com.fantaf1.fantaf1_selezione.entity.enums.PowerUpType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "powerup_plays", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "gpWeekendId"}) // Impedisce ID duplicati
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerUpPlay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long gpWeekendId;

    @Column(nullable = false)
    private Long driverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PowerUpType powerUpType;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
