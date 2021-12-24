package com.alpha.exchangerateanalyser.view;
/*
This class provides main functional of the app
 */
import com.alpha.exchangerateanalyser.controllers.GifController;
import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterface;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableFeignClients(basePackageClasses = OpenExchangeRatesInterface.class)
public class ExchangeRateAnalyserView {
    org.slf4j.Logger log = LoggerFactory.getLogger(ExchangeRateAnalyserView.class);

    @Autowired
    private com.alpha.exchangerateanalyser.controllers.CurrencyController currencyController;
    @Autowired
    private GifController gifController;
    /*
    This method provides a random GIF from Giphy(3rd party service)
    depending on change of exchange rate of the base currency to the target currency during one day
    in accordance with the information from Open Exchange Rates(3rd party service)
     */
    @GetMapping("/api/ratesgif")
    public String getRates(@RequestParam("currency") String currency) { //Target currency provided by query param "currency"
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method is started");
        String result = currencyController.calculateExRatesChange(currency);
        log.debug("ExRate change is: " + result);
        String gifURL = gifController.requestRandomGif(result);
        gifURL = "Your result is: " + result + "<br><div style=\"width:50%;height:0;padding-bottom:50%;position:relative;\"><iframe src=\""
                + gifURL +"\" width=\"50%\" height=\"50%\" style=\"position:absolute\" frameBorder=\"0\"class=\"giphy-embed\" allowFullScreen>";
        return gifURL;
    }
    /*
    This method is to check that app itself is still working. May be useful in case of any problems with 3rd party services
     */
    @GetMapping("/api/check")
    ResponseEntity<String> appCheck() {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + " method is started");
        return ResponseEntity.ok("The app is still working. If you have any issues, please check availability of 3rd party services.");
    }
}
