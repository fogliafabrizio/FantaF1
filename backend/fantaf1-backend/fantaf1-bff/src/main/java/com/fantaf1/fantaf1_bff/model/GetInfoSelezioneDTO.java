package com.fantaf1.fantaf1_bff.model;

import lombok.Data;

import java.util.List;

@Data
public class GetInfoSelezioneDTO {

    private Long id;
    private Long gpWeekendId;
    private List<Long> driverIds;
    private Integer creditsRemaining;
}
