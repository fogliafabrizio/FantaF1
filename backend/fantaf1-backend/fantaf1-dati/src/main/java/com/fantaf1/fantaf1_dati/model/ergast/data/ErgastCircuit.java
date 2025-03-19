package com.fantaf1.fantaf1_dati.model.ergast.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErgastCircuit {

    private String circuitId;
    private String url;
    private String circuitName;
    @JsonProperty("Location")
    private ErgastLocation location;
}
