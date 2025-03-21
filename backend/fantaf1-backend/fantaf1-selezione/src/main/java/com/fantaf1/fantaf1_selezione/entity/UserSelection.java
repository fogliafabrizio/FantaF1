package com.fantaf1.fantaf1_selezione.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_selections", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "gpWeekendId"}) // Impedisce ID duplicati
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long gpWeekendId;

    @OneToMany(mappedBy = "userSelection", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<SelectedDriver> selectedDrivers = new ArrayList<>();

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}