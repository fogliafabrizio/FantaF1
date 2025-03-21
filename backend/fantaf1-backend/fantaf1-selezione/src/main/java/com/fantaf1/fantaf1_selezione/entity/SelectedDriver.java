package com.fantaf1.fantaf1_selezione.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "selected_drivers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectedDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_selection_id", nullable = false)
    private UserSelection userSelection;

    @Column(nullable = false)
    private Long driverId;

    @Column(nullable = false)
    private boolean isJolly;
}

