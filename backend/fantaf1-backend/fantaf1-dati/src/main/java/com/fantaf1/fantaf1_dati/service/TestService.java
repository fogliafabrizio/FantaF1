package com.fantaf1.fantaf1_dati.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Scheduled(fixedRate = 10000) // Ogni 10 secondi
    public void printTestMessage() {
        System.out.println("✅ Il servizio è attivo e sta eseguendo operazioni!");
    }
}
