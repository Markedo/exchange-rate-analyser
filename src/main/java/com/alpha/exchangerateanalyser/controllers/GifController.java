package com.alpha.exchangerateanalyser.controllers;

/*
This is the controller for Giphy Interface
 */

import com.alpha.exchangerateanalyser.intefraces.GiphyInterface;
import com.alpha.exchangerateanalyser.view.ExchangeRateAnalyserView;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class GifController {

    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Value("${GiphyID}")
    private String giphy_id; //API ID for Giphy 3rd party service

    @Autowired
    private GiphyInterface gifInterface;

    public String requestRandomGif(String keyword) {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method is started");
        String response = gifInterface.requestRandomGif(giphy_id, keyword);
        int urlBegin = response.indexOf("\"embed_url\"");
        log.debug("URL begins at: " + urlBegin);
        String url = response.substring(urlBegin);
        log.debug("URL substring formed successfully");
        int urlEnd = url.indexOf(',');
        log.debug("URL ends at: " + urlEnd);
        url = url.substring(13, urlEnd-1).replaceAll("\\\\",""); //BE CAREFUL! Actual URL begins at position 13 (because we need to remove <<"embed_url:" >> part) and ends at position {@link #urlEnd} -1 (because we need to remove ending <<,>>
        log.debug("GIF URL is: " + url);
        return url;
    }
}
