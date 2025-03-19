package com.fantaf1.fantaf1_dati.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "pilot_cost_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PilotCostHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pilot_id", nullable = false)
    private Pilot pilot;

    @Column(name = "season", nullable = false)
    private Integer season;

    @Column(name = "previous_value", nullable = false)
    private Integer previousValue = 0;

    @Column(name = "new_value", nullable = false)
    private Integer newValue;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}