package com.alpha.exchangerateanalyser;

import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterfImpl;
import com.alpha.exchangerateanalyser.models.ExchangeRatesStatus;
import com.alpha.exchangerateanalyser.models.OERExchangeRates;
import com.alpha.exchangerateanalyser.service.OpenExchangeRateService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class OpenExchangeRateServiceTest {

    private static final String TEST_CURRENCY_LOW = "LOW";
    private static final String TEST_CURRENCY_HIGH = "HIGH";
    private static final String TEST_CURRENCY_BASE = "BASE";

    @Autowired
    private OpenExchangeRateService OERService;

    @MockBean
    OpenExchangeRatesInterfImpl OERInterfaceImpl;

    @Test
    void currencyValidatorTest(){ //test of currency validation service
        HashMap<String, String> testMap = new HashMap<>(); //generate HashMap to return in Mockito
        testMap.put(TEST_CURRENCY_LOW, "Test currency description"); //put testing currency in HashMap
        Mockito.when(OERInterfaceImpl.requestCurrenciesList()).thenReturn(testMap);
        boolean checkTestCurrency = OERService.validateCurrency(TEST_CURRENCY_LOW); //check validity of currency in HashMap
        boolean checkRandomCurrency = OERService.validateCurrency("XYZ"); //check validity of random currency
        Mockito.verify(OERInterfaceImpl, Mockito.times(2)).requestCurrenciesList();
        assertThat(checkTestCurrency).isTrue();
        assertThat(checkRandomCurrency).isFalse();
    }

    @Test
    void exchangeRatesTest(){ //test of exchange rate analyser service
        //Form background objects for test purposes
        String yesterdayDate = LocalDate.now().minusDays(1).toString();
        HashMap<String, BigDecimal> testMapCurrent = new HashMap<>();
        testMapCurrent.put(TEST_CURRENCY_LOW, new BigDecimal(20));
        testMapCurrent.put(TEST_CURRENCY_BASE, new BigDecimal(1));
        testMapCurrent.put(TEST_CURRENCY_HIGH, new BigDecimal(0.5));

        HashMap<String, BigDecimal> testMapYesterday = new HashMap<>();
        testMapYesterday.put(TEST_CURRENCY_LOW, new BigDecimal(10));
        testMapYesterday.put(TEST_CURRENCY_BASE, new BigDecimal(1));
        testMapYesterday.put(TEST_CURRENCY_HIGH, new BigDecimal(1));
        OERExchangeRates ratesObjCurr = new OERExchangeRates("null", "null", 1, "null", testMapCurrent);
        OERExchangeRates ratesObjYest = new OERExchangeRates("null", "null", 2, "null", testMapYesterday);

        //Handle service requests
        Mockito.doReturn(ratesObjCurr).when(OERInterfaceImpl).requestRates();
        Mockito.doReturn(ratesObjYest).when(OERInterfaceImpl).requestRatesOnDate(yesterdayDate);

        //Perform actions with tested service
        ExchangeRatesStatus resultLowCurrency = OERService.calculateExRatesChange(TEST_CURRENCY_LOW);
        ExchangeRatesStatus resultBase = OERService.calculateExRatesChange(TEST_CURRENCY_BASE);
        ExchangeRatesStatus resultHighCurrency = OERService.calculateExRatesChange(TEST_CURRENCY_HIGH);

        //Verify request of actual and yesterday exchange rates
        Mockito.verify(OERInterfaceImpl, Mockito.times(3)).requestRates();
        Mockito.verify(OERInterfaceImpl, Mockito.times(3)).requestRatesOnDate(yesterdayDate);

        //verify results
        assertThat(resultLowCurrency).isEqualTo(ExchangeRatesStatus.BROKE);
        assertThat(resultBase).isEqualTo(ExchangeRatesStatus.EQUAL);
        assertThat(resultHighCurrency).isEqualTo(ExchangeRatesStatus.RICH);
    }
}
