package com.alpha.exchangerateanalyser.view;
/*
This class receives commands from the user and returns answers
 */
import com.alpha.exchangerateanalyser.controllers.ExchangeRateAnalyserController;
import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients(basePackageClasses = OpenExchangeRatesInterface.class)
public class ExchangeRateAnalyserView {
    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Autowired
    private ExchangeRateAnalyserController ERcontroller;

    /*
    This method provides a random GIF from Giphy(3rd party service) depending on change of exchange rate of the base currency to the target
    currency during one day in accordance with the information from Open Exchange Rates(3rd party service).

    Since the use of 3rd party libraries is restricted, response forms in HTML code.
    */
    @GetMapping("/api/ratesgif")
    ResponseEntity<String> getRates(@RequestParam("currency") String currency) throws JsonProcessingException { //Target currency provided by query param "currency"
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method requested");
        ResponseEntity response = new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        if(ERcontroller.validateCurrency(currency)) {
            String result = ERcontroller.getExRateComparisonResult(currency); //Contains result of Exchange Rates compare
            String gifURL = ERcontroller.getExRateGIF(result); //Contains URL to GIF
            response = ResponseEntity.ok() //Forms HTML page with results
                    .body(
                    "<title>Exchange Rate Analyser</title>" //Set title for a response page
                    + "Your result is: " + result + "<br>"
                    + "<br><div style=\"width:50%;height:0;padding-bottom:50%;position:relative;\"><iframe src=\""
                    + gifURL + "\" width=\"50%\" height=\"50%\" style=\"position:absolute\" frameBorder=\"0\"class=\"giphy-embed\" allowFullScreen>"
            );
        }
        else {
            response = ResponseEntity.badRequest().body( //Forms HTML page if currency is not valid
                    "<title>Exchange Rate Analyser: ERROR</title>"
                    + "Invalid currency");
        }
        return response;
    }
    /*
    This method is to check that app itself is still working. May be useful in case of any problems with 3rd party services
     */
    @GetMapping("/api/check")
    ResponseEntity<String> appCheck() {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method requested");
        return ResponseEntity.ok("The app is still working. If you have any issues, please check availability of 3rd party services.");
    }
}