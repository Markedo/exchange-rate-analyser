package com.alpha.exchangerateanalyser.services;
/*
This class provides main functional of the app
 */
import com.alpha.exchangerateanalyser.intefraces.GiphyInterface;
import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterface;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@EnableFeignClients(basePackageClasses = OpenExchangeRatesInterface.class)
public class CurrencyService {
    org.slf4j.Logger log = LoggerFactory.getLogger(CurrencyService.class);

    @Value("${OpenExchangeRateID}")
    private String OERapp_id; //API ID for Open Exchange Rates 3rd party service

    @Value("${GiphyID}")
    private String Giphyapp_id; //API ID for Giphy 3rd party service

    @Value("${OpenExchangeRateBASE}")
    private String baseCurrency; //Base currency to compare with

    @Autowired
    private OpenExchangeRatesInterface OERInterface;

    @Autowired
    private GiphyInterface GIFInterface;

    /*
    A method that provides a random GIF from Giphy(3rd party service)
    depending on the change in the exchange rate in one day of the base currency to the target currency
    in accordance with the information from Open Exchange Rates(3rd party service)
     */
    @GetMapping("/ratesgif")
    public String getRates(@RequestParam("currency") String currency) { //Target currency provided by query param "currency"
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method is started");
        String result = null;
        LocalDate date = LocalDate.now().minusDays(1); //Determine with which day to compare, currently settled as tomorrow
        BigDecimal currentRate = OERInterface.requestRates(OERapp_id, baseCurrency).getRates().get(currency); //Receive current exchange rate for specified currency
        BigDecimal yesterdayRate = OERInterface.requestRatesOnDate(date.toString(), OERapp_id, baseCurrency).getRates().get(currency); //Receive yesterday exchange rate for specified currency
        log.debug("current rate: " + currentRate.toString() + ", yesterday rate: " + yesterdayRate.toString());
        switch (currentRate.compareTo(yesterdayRate)) {
            case -1 -> result="ExRate increased";
            case 0 -> result="ExRate wasn't changed";
            case 1 -> result="ExRate decreased";
            default -> result="Error";
        }
        log.debug(String.valueOf(currentRate.compareTo(yesterdayRate)));
        return result;
    }

    @GetMapping("/check")
    public String appCheck() {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method is started");
        log.debug("App works fine");
        return "Today is " + LocalDate.now().toString();
    }

    @GetMapping("/getrandgif")
    public ResponseEntity<Map> getrandgif() {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method is started");
        ResponseEntity<Map> response = GIFInterface.requestRandonGif(Giphyapp_id);
        return response;
    }
}
