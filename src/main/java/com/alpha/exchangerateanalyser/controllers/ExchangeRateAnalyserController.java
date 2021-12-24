package com.alpha.exchangerateanalyser.controllers;
/*
This class handle requests from {@Link com.alpha.exchangerateanalyser.view.ExchangeRateAnalyserView.Class}
 */

import com.alpha.exchangerateanalyser.service.GiphyService;
import com.alpha.exchangerateanalyser.service.OpenExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExchangeRateAnalyserController {

    @Autowired
    GiphyService gifController;

    @Autowired
    OpenExchangeRateService OERController;

    public String getExRateComparisonResult(String currency) {
        return OERController.calculateExRatesChange(currency);
    }

    public String getExRateGIF(String gif) throws JsonProcessingException {
        return gifController.requestRandomGif(gif);
    }

    public boolean validateCurrency(String currency) {
        boolean answer = OERController.validateCurrency(currency);
        return answer;
    }


}
