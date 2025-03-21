package com.fantaf1.fantaf1_bff.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SelezioneResponseDTO {

    private Long id;
    private Long gpWeekendId;
    private List<Long> driverIds;
    private String createdAt;
}
