package com.fantaf1.fantaf1_dati.model.ergast.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErgastLocation {
    private String lat;
    @JsonProperty("long")
    private String lon;
    private String locality;
    private String country;
}
