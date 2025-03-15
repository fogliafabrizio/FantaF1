package com.fantaf1.fantaf1_dati.model.ergast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErgastResponse<T extends MRData> {

    @JsonProperty("MRData")
    private T mrData;
}
