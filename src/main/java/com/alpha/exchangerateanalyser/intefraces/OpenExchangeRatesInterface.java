package com.alpha.exchangerateanalyser.intefraces;

/*
This interface provides interaction with the Open Exchange Rates service through API as of 21.12.2021
 */

import com.alpha.exchangerateanalyser.models.OERExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Currency;
import java.util.HashMap;

@FeignClient(name = "OpenExchangeRates", url = "${OpenExchangeRateURL}")
public interface OpenExchangeRatesInterface {

    @GetMapping("/api/latest.json")
    OERExchangeRates requestRates(@RequestParam("app_id") String app_id,
                                  @RequestParam(name="base", required = false) String base);


    @GetMapping("/api/latest.json") //method with all possible parameters(as of 21.12.2021), not used properly in current version of the app
    OERExchangeRates requestRates(@RequestParam("app_id") String app_id,
                                  @RequestParam(name="base", required = false) String base,
                                  @RequestParam(name="symbols", required = false) String symbols,
                                  @RequestParam(name="prettyprint", required = false) boolean prettyprint,
                                  @RequestParam(name="show_alternative", required = false) boolean show_alternative);

    @GetMapping("/api/historical/{date}.json")
    OERExchangeRates requestRatesOnDate(@PathVariable String date,
                                        @RequestParam("app_id") String app_id,
                                        @RequestParam(name="base", required = false) String base);

    @GetMapping("/api/historical/{date}.json") //method with all possible parameters(as of 21.12.2021), not used properly in current version of the app
    OERExchangeRates requestRatesOnDate(@PathVariable String date,
                                        @RequestParam("app_id") String app_id,
                                        @RequestParam(name="base", required = false) String base,
                                        @RequestParam(name="symbols", required = false) String symbols,
                                        @RequestParam(name="show_alternative", required = false) boolean show_alternative,
                                        @RequestParam(name="prettyprint", required = false) boolean prettyprint);

    @GetMapping("/api/currencies.json")//this method is not used in current version of the app
    HashMap<String, String> requestCurrenciesList();

    @GetMapping("/api/currencies.json")//this method is not used in current version of the app
    HashMap<String, String> requestCurrenciesList(@RequestParam(name="prettyprint", required = false) boolean prettyprint,
                                        @RequestParam(name="show_alternative", required = false) boolean show_alternative,
                                        @RequestParam(name="show_inactive", required = false) boolean show_inactive);

    @GetMapping("/api/usage.json")//this method is not used in current version of the app
    Object requestUsageStats(@RequestParam("app_id") String app_id,
                                    @RequestParam(name="prettyprint", required = false) boolean prettyprint);

}
