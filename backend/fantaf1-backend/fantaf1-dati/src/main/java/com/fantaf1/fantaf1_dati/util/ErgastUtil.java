package com.fantaf1.fantaf1_dati.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ErgastUtil implements DriversApi, RaceApi {

    private final String API_URL = "https://api.jolpi.ca/ergast/f1";

    @Override
    public String getDrivers() {
        return API_URL + "/drivers";
    }

    @Override
    public String getDrivers(Integer year) {
        return API_URL + "/" + year + "/drivers";
    }

    @Override
    public String getDrivers(Integer year, Integer round) {
        return API_URL + "/" + year + "/" + round + "/drivers";
    }

    @Override
    public String getRaces() {
        return API_URL + "/races";
    }

    @Override
    public String getRaces(Integer year) {
        return API_URL + "/" + year + "/races";
    }

    @Override
    public String getRaces(Integer year, Integer round) {
        return API_URL + "/" + year + "/" + round + "/races";
    }
}
