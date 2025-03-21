package com.fantaf1.fantaf1_bff.mapper;

import com.fantaf1.fantaf1_bff.model.SelezioneRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.SelezioneRequest;

@Mapper
public interface PresentationToModelMapper {

    @Mapping(source = "request.gpWeekendId", target = "gpWeekendId")
    @Mapping(source = "request.driverIds", target = "driverIds")
    @Mapping(source = "totalCost", target = "totalCost")
    SelezioneRequestDTO map(SelezioneRequest request, int totalCost);
}
