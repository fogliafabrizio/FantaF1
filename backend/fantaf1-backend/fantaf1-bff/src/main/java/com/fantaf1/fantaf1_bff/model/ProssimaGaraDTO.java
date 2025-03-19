package com.fantaf1.fantaf1_bff.model;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ProssimaGaraDTO {

    private Long id;
    private String raceName;
    private String circuitName;
    private String country;
    private OffsetDateTime dateTimeRace;
    private String urlWikipedia;
    private SessionDTO[] sessions;
}
