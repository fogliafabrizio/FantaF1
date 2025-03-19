package com.fantaf1.fantaf1_dati.model.ergast.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErgastRace {

    private String season;
    private String round;
    private String url;
    private String raceName;
    @JsonProperty("Circuit")
    private ErgastCircuit circuit;
    private String date;
    private String time;
    @JsonProperty("FirstPractice")
    private ErgastSession firstPractice;
    @JsonProperty("SecondPractice")
    private ErgastSession secondPractice;
    @JsonProperty("ThirdPractice")
    private ErgastSession thirdPractice;
    @JsonProperty("Qualifying")
    private ErgastSession qualifying;
    @JsonProperty("Sprint")
    private ErgastSession sprint;
    @JsonProperty("SprintQualifying")
    private ErgastSession sprintQualifying;
}
