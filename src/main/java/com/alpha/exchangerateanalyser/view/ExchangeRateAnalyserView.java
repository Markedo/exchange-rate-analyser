package com.alpha.exchangerateanalyser.view;
/*
This class receives commands from the user and returns answers
 */
import com.alpha.exchangerateanalyser.controllers.ExchangeRateAnalyserController;
import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterface;
import com.alpha.exchangerateanalyser.models.ExchangeRatesStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableFeignClients(basePackageClasses = OpenExchangeRatesInterface.class)
public class ExchangeRateAnalyserView {

    private final static String PAGE_TITLE = "<title>Exchange Rate Analyser</title>"; //title of the page
    private final static String GET_RATES_PATH = "/api/v1/rates"; //mapping for main functional that compares currencies exchange rates
    private final static String CURRENCY_PARAM = "currency"; //the name of the parameter that specify currency to compare
    private final static String APP_CHECK_PATH = "/api/v1/check"; //mapping for optional method that check app availability

    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Autowired
    private ExchangeRateAnalyserController ERcontroller;

    /*
    This method provides a random GIF from Giphy(3rd party service) depending on change of exchange rate of the base currency to the target
    currency during one day in accordance with the information from Open Exchange Rates(3rd party service).

    Since the use of 3rd party libraries is restricted, response forms in HTML code.
    */
    @GetMapping(GET_RATES_PATH)
    ResponseEntity<String> getRates(@RequestParam(CURRENCY_PARAM) String currency) throws JsonProcessingException { //Target currency provided by query param "currency"
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method requested");
        ResponseEntity response;
        if(ERcontroller.validateCurrency(currency)) {
            ExchangeRatesStatus result = ERcontroller.getExRateComparisonResult(currency); //Contains result of exchange rates comparison
            String gifURL = ERcontroller.getExRateGIF(result.toString()); //Contains URL of GIF
            response = ResponseEntity.ok() //Forms HTML page with results
                    .body(
                    PAGE_TITLE
                    + "Your result is: " + result + "<br>"
                    + "<br><div style=\"width:50%;height:0;padding-bottom:50%;position:relative;\"><iframe src=\""
                    + gifURL + "\" width=\"50%\" height=\"50%\" style=\"position:absolute\" frameBorder=\"0\"class=\"giphy-embed\" allowFullScreen>"
            );
        }
        else {
            response = ResponseEntity.badRequest().body(PAGE_TITLE + "Currency invalid or missing."); //Forms HTML page if currency is not valid
        }
        return response;
    }

    /*
    This method is to check that app itself is still working. May be useful in case of any problems with 3rd party services
     */
    @GetMapping(APP_CHECK_PATH)
    ResponseEntity<String> appCheck() {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method requested");
        return ResponseEntity.ok(PAGE_TITLE + "Seems like app works fine. If you have any issues, please check availability of 3rd party services.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)  //This method handle requests without params
    public ResponseEntity<String> missingParams(MissingServletRequestParameterException ex) {
        String missingParam = ex.getParameterName();
        log.debug("Request without obligatory param \""+ missingParam +"\" received");
        return ResponseEntity.badRequest().body(PAGE_TITLE + "Request parameter is missing. Please specify \"" + missingParam + "\" parameter.");
    }
}