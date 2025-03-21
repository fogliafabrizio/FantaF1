package com.fantaf1.fantaf1_bff.service;

import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapper;
import com.fantaf1.fantaf1_bff.mapper.ModelToPresentationMapperImpl;
import com.fantaf1.fantaf1_bff.mapper.PresentationToModelMapper;
import com.fantaf1.fantaf1_bff.mapper.PresentationToModelMapperImpl;
import com.fantaf1.fantaf1_bff.model.GetInfoSelezioneDTO;
import com.fantaf1.fantaf1_bff.model.SelezioneRequestDTO;
import com.fantaf1.fantaf1_bff.model.SelezioneResponseDTO;
import org.openapitools.model.SelezioneConCreditiResponse;
import org.openapitools.model.SelezioneRequest;
import org.openapitools.model.SelezioneResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SelezioneService {

    private ModelToPresentationMapper m2p = new ModelToPresentationMapperImpl();
    private PresentationToModelMapper p2m = new PresentationToModelMapperImpl();


    RestTemplateUtil restTemplateUtil = new RestTemplateUtil();

    @Value("${fantaf1.selezione.base-url}")
    private String baseUrl;

    public SelezioneResponse selezionePiloti(SelezioneRequest selezioneRequest, int totalCost) {
        String url = baseUrl + "/selezioni";
        SelezioneRequestDTO request = p2m.map(selezioneRequest, totalCost);
        ResponseEntity<SelezioneResponseDTO> response = restTemplateUtil.postForObject(url, request, SelezioneResponseDTO.class);
        return m2p.map(response.getBody());
    }

    public SelezioneConCreditiResponse getInfoSelezione(Integer gpWeekendId) {
        String url = baseUrl + "/selezioni/" + gpWeekendId;
        ResponseEntity<GetInfoSelezioneDTO> response = restTemplateUtil.getForObject(url, GetInfoSelezioneDTO.class);
        return m2p.map(response.getBody());
    }
}
