package com.fantaf1.fantaf1_dati.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "races", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"season", "circuit_id", "round"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer season;

    @Column(nullable = false)
    private Integer round;

    @Column(name = "race_name", nullable = false)
    private String raceName;

    @Column(name = "circuit_name", nullable = false)
    private String circuitName;

    @Column(name = "circuit_id", nullable = false)
    private String circuitId;

    @Column(name = "location")
    private String location;

    @Column(name = "country")
    private String country;

    @Column(name = "date_time_race", nullable = false)
    private LocalDateTime dateTimeRace;

    @Column(name = "url_wikipedia", columnDefinition = "TEXT")
    private String urlWikipedia;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}