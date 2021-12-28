package com.alpha.exchangerateanalyser.intefraces;

import com.alpha.exchangerateanalyser.models.OERExchangeRates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class OpenExchangeRatesInterfImpl implements OpenExchangeRatesInterface {

    @Value("${OpenExchangeRateID}")
    private String OER_id; //API ID for Open Exchange Rates 3rd party service
    @Value("${OpenExchangeRateBASE}")
    private String baseCurrency; //Base currency to compare with

    @Autowired
    private OpenExchangeRatesInterface OERInterface;

    public OERExchangeRates requestRates() {
        return OERInterface.requestRates(OER_id, baseCurrency);
    }

    public OERExchangeRates requestRatesOnDate(String date) {
        return OERInterface.requestRatesOnDate(date, OER_id, baseCurrency);
    }

    @Override
    public OERExchangeRates requestRates(String app_id, String base) {
        return OERInterface.requestRates(app_id, base);
    }

    @Override
    public OERExchangeRates requestRates(String app_id, String base, String symbols, boolean prettyprint, boolean show_alternative) {
        return OERInterface.requestRates(app_id, base, symbols, prettyprint, show_alternative);
    }

    @Override
    public OERExchangeRates requestRatesOnDate(String date, String app_id, String base) {
        return OERInterface.requestRatesOnDate(date, app_id, base);
    }

    @Override
    public OERExchangeRates requestRatesOnDate(String date, String app_id, String base, String symbols, boolean show_alternative, boolean prettyprint) {
        return OERInterface.requestRatesOnDate(date, app_id, base, symbols, show_alternative, prettyprint);
    }

    @Override
    public HashMap<String, String> requestCurrenciesList() {
        return OERInterface.requestCurrenciesList();
    }

    @Override
    public HashMap<String, String> requestCurrenciesList(boolean prettyprint, boolean show_alternative, boolean show_inactive) {
        return OERInterface.requestCurrenciesList(prettyprint, show_alternative, show_inactive);
    }

    @Override
    public Object requestUsageStats(String app_id, boolean prettyprint) {
        return OERInterface.requestUsageStats(app_id, prettyprint);
    }

}
