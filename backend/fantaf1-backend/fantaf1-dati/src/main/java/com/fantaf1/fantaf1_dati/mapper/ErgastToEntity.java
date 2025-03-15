package com.fantaf1.fantaf1_dati.mapper;

import com.fantaf1.fantaf1_dati.entity.Pilot;
import com.fantaf1.fantaf1_dati.model.ergast.data.ErgastDriver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
