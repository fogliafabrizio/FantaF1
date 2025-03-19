package com.fantaf1.fantaf1_bff.model;

import com.fantaf1.fantaf1_bff.model.enums.SessionType;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class SessionDTO {

    private SessionType sessionType;
    private OffsetDateTime sessionDateTime;
}
