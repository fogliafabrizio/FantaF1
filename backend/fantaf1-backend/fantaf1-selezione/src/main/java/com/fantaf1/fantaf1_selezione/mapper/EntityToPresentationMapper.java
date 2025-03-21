package com.fantaf1.fantaf1_selezione.mapper;

import com.fantaf1.fantaf1_selezione.entity.SelectedDriver;
import com.fantaf1.fantaf1_selezione.entity.UserSelection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.SelezioneResponse;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper
public interface EntityToPresentationMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "gpWeekendId", source = "gpWeekendId")
    @Mapping(target = "driverIds", source = "selectedDrivers")
    SelezioneResponse map(UserSelection userSelection);

    default List<Integer> map(List<SelectedDriver> selectedDrivers) {
        if (selectedDrivers == null) {
            return List.of();
        }
        return selectedDrivers.stream()
                .map(sd -> sd.getDriverId().intValue()) // conversione Long -> Integer
                .toList();
    }

    default OffsetDateTime map(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.of("Europe/Rome")).toOffsetDateTime(); // oppure usa ZoneId.of(\"Europe/Rome\") se vuoi il fuso locale
    }
}
