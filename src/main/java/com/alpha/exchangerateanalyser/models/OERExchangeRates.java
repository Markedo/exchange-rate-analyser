package com.alpha.exchangerateanalyser.models;
/*
This class is required to process responses from the Open Exchange Rates service through API as of 21.12.2021
 */
import java.math.BigDecimal;
import java.util.Map;

public class OERExchangeRates {

    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, BigDecimal> rates;

    public OERExchangeRates() {
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

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

}
