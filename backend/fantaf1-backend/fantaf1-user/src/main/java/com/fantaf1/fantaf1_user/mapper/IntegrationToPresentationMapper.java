package com.fantaf1.fantaf1_user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.JwtResponse;

@Mapper
public interface IntegrationToPresentationMapper {

    JwtResponse map(String token);
}
