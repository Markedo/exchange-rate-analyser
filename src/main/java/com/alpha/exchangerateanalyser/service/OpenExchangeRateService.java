package com.alpha.exchangerateanalyser.service;
/*
This is the controller for Open Exchange Rates Interface
 */

import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterfImpl;
import com.alpha.exchangerateanalyser.models.ExchangeRatesStatus;
import com.alpha.exchangerateanalyser.models.OERExchangeRates;
import com.alpha.exchangerateanalyser.view.ExchangeRateAnalyserView;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OpenExchangeRateService {

    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Autowired
    private OpenExchangeRatesInterfImpl OERInterfaceImpl;

    public ExchangeRatesStatus calculateExRatesChange(String targetCurrency) { //calculate change of the exchange rate since yesterday
        LocalDate date = LocalDate.now().minusDays(1); //Determine with which day to compare, currently settled as tomorrow(now minus one day)
        OERExchangeRates currentRates = OERInterfaceImpl.requestRates();
        OERExchangeRates yesterdayRates = OERInterfaceImpl.requestRatesOnDate(date.toString());
        BigDecimal currentCurrencyRate = currentRates.getRates().get(targetCurrency); //Receive current exchange rate for specified currency
        BigDecimal yesterdayCurrencyRate = yesterdayRates.getRates().get(targetCurrency); //Receive yesterday exchange rate for specified currency
        log.debug("Target currency: " + targetCurrency + ", yesterday date: " + date);
        log.debug("Current rates: " + currentCurrencyRate.toString() + " timestamp: " + currentRates.getTimestamp());
        log.debug("Yesterday rate: " + yesterdayCurrencyRate.toString() + " timestamp: " + yesterdayRates.getTimestamp());
        ExchangeRatesStatus result;
        switch (currentCurrencyRate.compareTo(yesterdayCurrencyRate)) {
            case -1 -> result = ExchangeRatesStatus.RICH; //In case ExRate increased
            case 0 -> result =  ExchangeRatesStatus.EQUAL; //In case ExRate wasn't changed
            case 1 -> result =  ExchangeRatesStatus.BROKE; //In case ExRate decreased
            default -> result =  ExchangeRatesStatus.ERROR; //In other case
        }
        return result;
    }

    public boolean validateCurrency(String currency) { //validate provided currency
        boolean result = OERInterfaceImpl.requestCurrenciesList().containsKey(currency);
        log.debug("Currency validation result: " + result);
        return result;
    }
}
