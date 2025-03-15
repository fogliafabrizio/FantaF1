package com.fantaf1.fantaf1_dati.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pilots", uniqueConstraints = {
        @UniqueConstraint(columnNames = "driver_id") // Impedisce ID duplicati
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

    @Column(name = "driver_id", unique = true, nullable = false)
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
}

