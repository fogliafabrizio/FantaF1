package com.fantaf1.fantaf1_dati.model.ergast;

import com.fantaf1.fantaf1_dati.model.ergast.data.DriverTable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PilotsReponse extends MRData {

    @JsonProperty("DriverTable")
    private DriverTable driverTable;
}
