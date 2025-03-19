package com.fantaf1.fantaf1_dati.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pilot_current_credit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PilotCurrentCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id", nullable = false, unique = true)
    private Pilot pilot;

    @Column(name = "season", nullable = false)
    private Integer season;

    @Column(name = "current_value", nullable = false)
    private Integer currentValue = 0;

    @UpdateTimestamp
    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

