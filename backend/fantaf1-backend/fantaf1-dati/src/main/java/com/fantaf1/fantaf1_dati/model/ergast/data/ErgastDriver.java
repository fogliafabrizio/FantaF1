package com.fantaf1.fantaf1_dati.model.ergast.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErgastDriver {

    @JsonProperty("driverId")
    private String driverId;

    @JsonProperty("permanentNumber")
    private Integer permanentNumber;

    @JsonProperty("code")
    private String code;

    @JsonProperty("url")
    private String wikipediaUrl;

    @JsonProperty("givenName")
    private String firstName;

    @JsonProperty("familyName")
    private String lastName;

    @JsonProperty("dateOfBirth")
    private String birthDate;

    @JsonProperty("nationality")
    private String nationality;
}
