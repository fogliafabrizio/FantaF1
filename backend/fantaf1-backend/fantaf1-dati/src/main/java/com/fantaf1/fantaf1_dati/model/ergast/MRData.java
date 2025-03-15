package com.fantaf1.fantaf1_dati.model.ergast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public abstract class MRData {

    @JsonProperty("series")
    private String series;

    @JsonProperty("xmlns")
    private String xmlns;

    @JsonProperty("url")
    private String url;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("offset")
    private int offset;

    @JsonProperty("total")
    private int total;
}
