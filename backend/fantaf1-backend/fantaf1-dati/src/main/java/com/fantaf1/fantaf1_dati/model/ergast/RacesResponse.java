package com.fantaf1.fantaf1_dati.model.ergast;

import com.fantaf1.fantaf1_dati.model.ergast.data.RaceTable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RacesResponse extends MRData{

    @JsonProperty("RaceTable")
    private RaceTable raceTable;

}
