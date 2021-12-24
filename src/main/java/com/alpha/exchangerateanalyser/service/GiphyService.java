package com.alpha.exchangerateanalyser.service;

/*
This is the controller for Giphy Interface
 */

import com.alpha.exchangerateanalyser.intefraces.GiphyInterface;
import com.alpha.exchangerateanalyser.view.ExchangeRateAnalyserView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GiphyService {

    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Value("${GiphyID}")
    private String giphy_id; //API ID for Giphy 3rd party service

    @Autowired
    private GiphyInterface gifInterface;

    public String requestRandomGif(String keyword) throws JsonProcessingException {
        ResponseEntity<String> response = gifInterface.requestRandomGif(giphy_id, keyword);
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readTree(response.getBody()).findPath("embed_url");
        String GIFurl = result.toString().replaceAll("\"","");
        log.debug("Giphy URL: " + GIFurl);
        return GIFurl;
    }
}
