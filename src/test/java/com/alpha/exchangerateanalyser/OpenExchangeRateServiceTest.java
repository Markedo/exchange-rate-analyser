package com.alpha.exchangerateanalyser;

import com.alpha.exchangerateanalyser.intefraces.OpenExchangeRatesInterface;
import com.alpha.exchangerateanalyser.models.ExchangeRatesStatus;
import com.alpha.exchangerateanalyser.service.OpenExchangeRateService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
public class OpenExchangeRateServiceTest {

    private static final String TEST_CURRENCY_LOW = "LOW";
    private static final String TEST_CURRENCY_HIGH = "HIGH";
    private static final String TEST_APP_ID = "TESTIDTESTIDTESTID";
    private static final String TEST_CURRENCY_BASE = "BASE";

    @Autowired
    private OpenExchangeRateService OERService;

    @MockBean
    OpenExchangeRatesInterface OERInterface;

    HashMap<String, BigDecimal> testMapCurrent = new HashMap<>();
    HashMap<String, BigDecimal> testMapYesterday = new HashMap<>();
    String yesterdayDate = LocalDate.now().minusDays(1).toString();

    /*
    @BeforeEach
    public void init() {
        //HashMap<String, BigDecimal> testMapCurrent = new HashMap<>(); //generate HashMap to return in Mockito
        testMapCurrent.put(TEST_CURRENCY_LOW, new BigDecimal(10));
        testMapCurrent.put(TEST_CURRENCY_BASE, new BigDecimal(1));
        testMapCurrent.put(TEST_CURRENCY_HIGH, new BigDecimal(1));

        //generate HashMap to return in Mockito
        testMapYesterday.put(TEST_CURRENCY_LOW, new BigDecimal(20));
        testMapYesterday.put(TEST_CURRENCY_BASE, new BigDecimal(1));
        testMapYesterday.put(TEST_CURRENCY_HIGH, new BigDecimal(0.5));
    }

    @Test
    void currencyValidatorTest(){ //test of currency validation service
        HashMap<String, String> testMap = new HashMap<>(); //generate HashMap to return in Mockito
        testMap.put(TEST_CURRENCY_LOW, TEST_CURRENCY_LOW); //put testing currency in HashMap
        Mockito.when(OERInterface.requestCurrenciesList()).thenReturn(testMap);
        boolean checkTestCurrency = OERService.validateCurrency(TEST_CURRENCY_LOW); //check validity of currency in HashMap
        boolean checkRandomCurrency = OERService.validateCurrency("XYZ"); //check validity of random currency
        Mockito.verify(OERInterface, Mockito.times(2)).requestCurrenciesList();
        assertThat(checkTestCurrency).isTrue();
        assertThat(checkRandomCurrency).isFalse();
    }

    @Test
    void exchangeRatesTest(){ //test of exchange rate analyser service

        Mockito.when(OERInterface.requestRates(TEST_APP_ID, TEST_CURRENCY_HIGH).getRates()).thenReturn(testMapCurrent);
        Mockito.when(OERInterface.requestRates(TEST_APP_ID, TEST_CURRENCY_BASE).getRates()).thenReturn(testMapCurrent);
        Mockito.when(OERInterface.requestRates(TEST_APP_ID, TEST_CURRENCY_LOW).getRates()).thenReturn(testMapCurrent);

        Mockito.when(OERInterface.requestRatesOnDate(any(), any(), any()).getRates()).thenReturn(testMapYesterday);

        ExchangeRatesStatus resultLowCurrency = OERService.calculateExRatesChange(TEST_CURRENCY_LOW);
        ExchangeRatesStatus resultBase = OERService.calculateExRatesChange(TEST_CURRENCY_BASE);
        ExchangeRatesStatus resultHighCurrency = OERService.calculateExRatesChange(TEST_CURRENCY_HIGH);
        //Verify request of actual exchange rates
        Mockito.verify(OERInterface, Mockito.times(1)).requestRates(TEST_APP_ID, TEST_CURRENCY_LOW);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRates(TEST_APP_ID, TEST_CURRENCY_BASE);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRates(TEST_APP_ID, TEST_CURRENCY_HIGH);
        //Verify request of yesterday exchange rates
        Mockito.verify(OERInterface, Mockito.times(1)).requestRatesOnDate(TEST_APP_ID, TEST_CURRENCY_LOW, yesterdayDate);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRatesOnDate(TEST_APP_ID, TEST_CURRENCY_BASE, yesterdayDate);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRatesOnDate(TEST_APP_ID, TEST_CURRENCY_HIGH, yesterdayDate);

        assertThat(resultLowCurrency).isEqualTo(ExchangeRatesStatus.BROKE);
        assertThat(resultBase).isEqualTo(ExchangeRatesStatus.EQUAL);
        assertThat(resultHighCurrency).isEqualTo(ExchangeRatesStatus.RICH);
    }



    void testTest(){
        String yesterdayDate = LocalDate.now().minusDays(1).toString();

        HashMap<String, BigDecimal> testMapCurrent = new HashMap<>(); //generate HashMap to return in Mockito
        testMapCurrent.put(TEST_CURRENCY_LOW, new BigDecimal(10));
        testMapCurrent.put(TEST_CURRENCY_BASE, new BigDecimal(1));
        testMapCurrent.put(TEST_CURRENCY_HIGH, new BigDecimal(1));
        HashMap<String, BigDecimal> testMapYesterday = new HashMap<>(); //generate HashMap to return in Mockito
        testMapYesterday.put(TEST_CURRENCY_LOW, new BigDecimal(20));
        testMapYesterday.put(TEST_CURRENCY_BASE, new BigDecimal(1));
        testMapYesterday.put(TEST_CURRENCY_HIGH, new BigDecimal(0.5));

        Mockito.doReturn(testMapCurrent).when(OERInterface).requestRates(TEST_APP_ID, TEST_CURRENCY_HIGH);
        Mockito.doReturn(testMapYesterday).when(OERInterface).requestRatesOnDate(any(), any(), any());

        //Mockito.when(OERInterface.requestRatesOnDate(any(), any(), any()).getRates()).thenReturn(testMapYesterday);

        ExchangeRatesStatus resultLowCurrency = OERService.calculateExRatesChange(TEST_CURRENCY_LOW);
        ExchangeRatesStatus resultBase = OERService.calculateExRatesChange(TEST_CURRENCY_BASE);
        ExchangeRatesStatus resultHighCurrency = OERService.calculateExRatesChange(TEST_CURRENCY_HIGH);
        //Verify request of actual exchange rates
        Mockito.verify(OERInterface, Mockito.times(1)).requestRates(TEST_APP_ID, TEST_CURRENCY_LOW);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRates(TEST_APP_ID, TEST_CURRENCY_BASE);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRates(TEST_APP_ID, TEST_CURRENCY_HIGH);
        //Verify request of yesterday exchange rates
        Mockito.verify(OERInterface, Mockito.times(1)).requestRatesOnDate(TEST_APP_ID, TEST_CURRENCY_LOW, yesterdayDate);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRatesOnDate(TEST_APP_ID, TEST_CURRENCY_BASE, yesterdayDate);
        Mockito.verify(OERInterface, Mockito.times(1)).requestRatesOnDate(TEST_APP_ID, TEST_CURRENCY_HIGH, yesterdayDate);

        assertThat(resultLowCurrency).isEqualTo(ExchangeRatesStatus.BROKE);
        assertThat(resultBase).isEqualTo(ExchangeRatesStatus.EQUAL);
        assertThat(resultHighCurrency).isEqualTo(ExchangeRatesStatus.RICH);
    }
     */
}
