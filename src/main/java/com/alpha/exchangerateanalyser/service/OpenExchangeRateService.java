package com.alpha.exchangerateanalyser.service;

/*
This is the controller for Open Exchange Rates Interface
 */

import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterface;
import com.alpha.exchangerateanalyser.models.OERExchangeRates;
import com.alpha.exchangerateanalyser.view.ExchangeRateAnalyserView;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class OpenExchangeRateService {

    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Value("${OpenExchangeRateID}")
    private String OER_id; //API ID for Open Exchange Rates 3rd party service
    @Value("${OpenExchangeRateBASE}")
    private String baseCurrency; //Base currency to compare with

    @Autowired
    private OpenExchangeRatesInterface OERInterface;

    public String calculateExRatesChange(String targetCurrency) {
        LocalDate date = LocalDate.now().minusDays(1); //Determine with which day to compare, currently settled as tomorrow
        OERExchangeRates currentRates = OERInterface.requestRates(OER_id, baseCurrency);
        OERExchangeRates yesterdayRates = OERInterface.requestRatesOnDate(date.toString(), OER_id, baseCurrency);
        BigDecimal currentCurrencyRate = currentRates.getRates().get(targetCurrency); //Receive current exchange rate for specified currency
        BigDecimal yesterdayCurrencyRate = yesterdayRates.getRates().get(targetCurrency); //Receive yesterday exchange rate for specified currency
        log.debug("Target currency: " + targetCurrency + ", yesterday date: " + date);
        log.debug("Current rates: " + currentCurrencyRate.toString() + " timestamp: " + currentRates.getTimestamp());
        log.debug("Yesterday rate: " + yesterdayCurrencyRate.toString() + " timestamp: " + yesterdayRates.getTimestamp());
        String result;
        switch (currentCurrencyRate.compareTo(yesterdayCurrencyRate)) {
            case -1 -> result = "rich"; //In case ExRate increased
            case 0 -> result = "equal"; //In case ExRate wasn't changed
            case 1 -> result = "broke"; //In case ExRate decreased
            default -> result = "error"; //In other case
        }
        return result;
    }

    public boolean validateCurrency(String currency) {
        Map<String, String> map = OERInterface.requestCurrenciesList();
        boolean result = OERInterface.requestCurrenciesList().containsKey(currency);
        log.debug("Currency validation result: " + result);
        return result;
    }
}
