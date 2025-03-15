package com.fantaf1.fantaf1_dati.util;

import com.fantaf1.fantaf1_dati.model.ergast.ErgastResponse;
import com.fantaf1.fantaf1_dati.model.ergast.MRData;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T extends MRData> ErgastResponse<T> call(
            String url,
            ParameterizedTypeReference<ErgastResponse<T>> typeReference
    ) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                typeReference
        ).getBody();
    }

}
