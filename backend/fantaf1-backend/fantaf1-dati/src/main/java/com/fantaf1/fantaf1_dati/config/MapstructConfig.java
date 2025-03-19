package com.fantaf1.fantaf1_dati.config;

import com.fantaf1.fantaf1_dati.mapper.EntityToPresentation;
import com.fantaf1.fantaf1_dati.mapper.EntityToPresentationImpl;
import com.fantaf1.fantaf1_dati.mapper.ErgastToEntity;
import com.fantaf1.fantaf1_dati.mapper.ErgastToEntityImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapstructConfig {

    @Bean
    public ErgastToEntity ergastToEntity() {
        return new ErgastToEntityImpl();
    }

    @Bean
    public EntityToPresentation ergastToEntityImpl() {
        return new EntityToPresentationImpl();
    }
}
