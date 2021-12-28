package com.alpha.exchangerateanalyser.models;
/*
This class is required to process responses containing exchange rates from the Open Exchange Rates service through API as of 21.12.2021
*/
import java.math.BigDecimal;
import java.util.HashMap;

public class OERExchangeRates {

    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private HashMap<String, BigDecimal> rates;

    public OERExchangeRates() {
    }

    public OERExchangeRates(String disclaimer, String license, Integer timestamp, String base, HashMap<String, BigDecimal> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public String getLicense() {
        return license;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public String getBase() {
        return base;
    }

    public HashMap<String, BigDecimal> getRates() {
        return rates;
    }

}
