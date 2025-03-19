package com.fantaf1.fantaf1_dati.mapper;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.entity.Race;
import com.fantaf1.fantaf1_dati.entity.RaceSession;
import com.fantaf1.fantaf1_dati.model.SessionType;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastDriver;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastRace;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Mapper
public interface ErgastToEntity {

    Pilot map(ErgastDriver driver, int season);

    @Mapping(target = "id", source = "pilot.id")
    @Mapping(target = "driverId", source = "driver.driverId")
    @Mapping(target = "permanentNumber", source = "driver.permanentNumber")
    @Mapping(target = "code", source = "driver.code")
    @Mapping(target = "wikipediaUrl", source = "driver.wikipediaUrl")
    @Mapping(target = "firstName", source = "driver.firstName")
    @Mapping(target = "lastName", source = "driver.lastName")
    @Mapping(target = "birthDate", source = "driver.birthDate")
    @Mapping(target = "nationality", source = "driver.nationality")
    Pilot update(Pilot pilot, ErgastDriver driver);

    @Mapping(target = "season", source = "season")
    @Mapping(target = "round", source = "ergastRace.round")
    @Mapping(target = "raceName", source = "ergastRace.raceName")
    @Mapping(target = "circuitName", source = "ergastRace.circuit.circuitName")
    @Mapping(target = "circuitId", source = "ergastRace.circuit.circuitId")
    @Mapping(target = "location", source = "ergastRace.circuit.location.locality")
    @Mapping(target = "country", source = "ergastRace.circuit.location.country")
    @Mapping(target = "dateTimeRace", expression = "java(computeDateTime(ergastRace.getDate(), ergastRace.getTime()))")
    @Mapping(target = "urlWikipedia", source = "ergastRace.url")
    Race map(ErgastRace ergastRace, int season);

    @Mapping(target = "id", source = "race.id")
    @Mapping(target = "season", source = "ergastRace.season")
    @Mapping(target = "round", source = "ergastRace.round")
    @Mapping(target = "raceName", source = "ergastRace.raceName")
    @Mapping(target = "circuitName", source = "ergastRace.circuit.circuitName")
    @Mapping(target = "circuitId", source = "ergastRace.circuit.circuitId")
    @Mapping(target = "location", source = "ergastRace.circuit.location.locality")
    @Mapping(target = "country", source = "ergastRace.circuit.location.country")
    @Mapping(target = "dateTimeRace", expression = "java(computeDateTime(ergastRace.getDate(), ergastRace.getTime()))")
    @Mapping(target = "urlWikipedia", source = "ergastRace.url")
    Race update(Race race, ErgastRace ergastRace);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "race", source="race")
    @Mapping(target = "sessionType", source="sessionType")
    @Mapping(target = "sessionDateTime", expression="java(computeDateTime(session.getDate(), session.getTime()))")
    RaceSession map(Race race, ErgastSession session, SessionType sessionType);

    default LocalDateTime computeDateTime(String date, String time) {
        String dateTimeStr = date + "T" + time;
        OffsetDateTime utcDateTime = OffsetDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return utcDateTime.atZoneSameInstant(ZoneId.of("Europe/Rome")).toLocalDateTime();
    }

}
