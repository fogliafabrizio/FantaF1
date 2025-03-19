package com.fantaf1.fantaf1_dati.mapper;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.entity.PilotCurrentCredit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.Pilota;
import org.openapitools.model.PilotaConCosto;

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

    default List<PilotaConCosto> map(Map<Pilot, PilotCurrentCredit> pilotCurrentCreditMap) {
        List<PilotaConCosto> pilotiConCosto = new ArrayList<>();
        for(Pilot pilot : pilotCurrentCreditMap.keySet()) {
            pilotiConCosto.add(map(pilot, pilotCurrentCreditMap.get(pilot)));
        }
        return pilotiConCosto;
    }
}
