package com.fantaf1.fantaf1_bff.service;

import org.openapitools.model.Pilota;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    public RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<T> getForObject(String url, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                responseType
        );
    }

    public <T> ResponseEntity<T> postForObject(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                responseType
        );
    }

    public <T> ResponseEntity<T> postForObjectWithoutToken(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                responseType
        );
    }

    private static String getToken(){
        return ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getToken().getTokenValue();
    }
}
