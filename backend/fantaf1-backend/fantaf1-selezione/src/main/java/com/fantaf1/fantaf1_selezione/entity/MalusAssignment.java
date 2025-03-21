package com.fantaf1.fantaf1_selezione.entity;

import com.fantaf1.fantaf1_selezione.entity.enums.MalusType;
import com.fantaf1.fantaf1_selezione.entity.enums.PowerUpType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "malus_assignments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"assignerUserId", "gpWeekendId"}) // Impedisce ID duplicati
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MalusAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long assignerUserId;

    @Column(nullable = false)
    private Long receiverUserId;

    @Column(nullable = false)
    private Long gpWeekendId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MalusType malusType;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
