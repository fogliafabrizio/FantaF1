package com.fantaf1.fantaf1_bff.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SelezioneRequestDTO {

    private Integer gpWeekendId;

    private List<Integer> driverIds = new ArrayList<>();

    private Integer totalCost;
}
