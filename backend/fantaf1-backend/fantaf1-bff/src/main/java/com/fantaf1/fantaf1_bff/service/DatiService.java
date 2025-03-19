package com.fantaf1.fantaf1_bff.service;

import org.openapitools.model.Pilota;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DatiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${fantaf1.dati.base-url}")
    private String baseUrl;

    public List<Pilota> getPilotiByAnno(int anno) {
        String url = baseUrl + "/piloti?anno=" + anno;
        ResponseEntity<Pilota[]> response = restTemplate.getForEntity(url, Pilota[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public Pilota getPilotaById(Long id) {
        String url = baseUrl + "/piloti/" + id;
        return restTemplate.getForObject(url, Pilota.class);
    }
}
