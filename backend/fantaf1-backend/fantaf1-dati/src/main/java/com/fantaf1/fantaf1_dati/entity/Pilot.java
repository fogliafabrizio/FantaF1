package com.fantaf1.fantaf1_dati.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pilots", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"driver_id", "season"}) // Impedisce ID duplicati
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pilot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chiave primaria automatica

    @Column(name = "driver_id", nullable = false)
    private String driverId; // ID ufficiale del pilota

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "nationality", nullable = false)
    private String nationality;

    @Column(name = "permanent_number")
    private Integer permanentNumber; // Numero di gara del pilota

    @Column(name = "code", length = 10)
    private String code; // Sigla pilota (es. VER per Verstappen)

    @Column(name = "wikipedia_url", columnDefinition = "TEXT")
    private String wikipediaUrl; // URL Wikipedia del pilota

    @Column(name = "season", nullable = false)
    private Integer season; // Stagione di appartenenza

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}

