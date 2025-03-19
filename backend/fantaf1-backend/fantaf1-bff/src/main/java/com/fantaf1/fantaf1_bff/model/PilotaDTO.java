package com.fantaf1.fantaf1_bff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PilotaDTO {

    private Long id;

    @JsonProperty("driver_id")
    private String driverId;

    private String sigla;

    private String nome;

    private String cognome;

    private Integer numero;

    private String nazionalita;


}
