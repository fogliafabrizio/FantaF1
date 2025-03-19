package com.fantaf1.fantaf1_dati.mapper;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.entity.PilotCurrentCredit;
import com.fantaf1.fantaf1_dati.entity.Race;
import com.fantaf1.fantaf1_dati.entity.RaceSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.NextRaceResponse;
import org.openapitools.model.Pilota;
import org.openapitools.model.PilotaConCosto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface EntityToPresentation {

    @Mapping(target = "nome", source = "firstName")
    @Mapping(target = "cognome", source = "lastName")
    @Mapping(target = "numero", source = "permanentNumber")
    @Mapping(target = "nazionalita", source = "nationality")
    @Mapping(target = "sigla", source = "code")
    Pilota map(Pilot pilot);

    List<Pilota> map(List<Pilot> pilots);

    @Mapping(target = "pilota", source = "pilota")
    @Mapping(target = "costo", source = "currentCredit.currentValue")
    PilotaConCosto map (Pilot pilota, PilotCurrentCredit currentCredit);

    @Mapping(target = "raceName", source = "race.raceName")
    @Mapping(target = "circuitName", source = "race.circuitName")
    @Mapping(target = "country", source = "race.country")
    @Mapping(target = "dateTimeRace", source ="race.dateTimeRace")
    @Mapping(target = "urlWikipedia", source ="race.urlWikipedia")
    @Mapping(target = "sessions", source = "sessions")
    NextRaceResponse map(Race race, List<RaceSession> sessions);

    default NextRaceResponse mapRace(Map<Race, List<RaceSession>> raceSessionMap) {
        if (raceSessionMap == null || raceSessionMap.isEmpty()) {
            return null;
        }
        Map.Entry<Race, List<RaceSession>> entry = raceSessionMap.entrySet().iterator().next();
        return map(entry.getKey(), entry.getValue());
    }

    default List<PilotaConCosto> mapPilot(Map<Pilot, PilotCurrentCredit> pilotCurrentCreditMap) {
        List<PilotaConCosto> pilotiConCosto = new ArrayList<>();
        for(Pilot pilot : pilotCurrentCreditMap.keySet()) {
            pilotiConCosto.add(map(pilot, pilotCurrentCreditMap.get(pilot)));
        }
        return pilotiConCosto;
    }

    default OffsetDateTime map(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of("Europe/Rome")).toOffsetDateTime(); // oppure usa ZoneId.of(\"Europe/Rome\") se vuoi il fuso locale
    }

}
