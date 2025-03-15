package com.fantaf1.fantaf1_dati.model.ergast.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DriverTable {

    @JsonProperty("season")
    private String season;

    @JsonProperty("Drivers")
    private List<ErgastDriver> ergastDrivers;
}
