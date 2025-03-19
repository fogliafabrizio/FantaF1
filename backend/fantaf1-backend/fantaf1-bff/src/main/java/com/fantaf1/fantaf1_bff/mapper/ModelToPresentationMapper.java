package com.fantaf1.fantaf1_bff.mapper;

import com.fantaf1.fantaf1_bff.model.PilotaConCostoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.PilotaConCosto;

import java.util.List;

@Mapper
public interface ModelToPresentationMapper {

    @Mapping(target = "id", source = "pilota.id")
    @Mapping(target = "driverId", source = "pilota.driverId")
    @Mapping(target = "nome", source = "pilota.nome")
    @Mapping(target = "cognome", source = "pilota.cognome")
    @Mapping(target = "numero", source = "pilota.numero")
    @Mapping(target = "nazionalita", source = "pilota.nazionalita")
    @Mapping(target = "costo", source = "costo")
    @Mapping(target = "sigla", source = "pilota.sigla")
    PilotaConCosto map (PilotaConCostoDTO pilota);

    List<PilotaConCosto> map(PilotaConCostoDTO[] piloti);
}
