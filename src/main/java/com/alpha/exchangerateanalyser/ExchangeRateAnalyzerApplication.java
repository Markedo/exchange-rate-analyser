package com.alpha.exchangerateanalyser;
/*
This app was originally made by @author Max "markedo"
 */

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExchangeRateAnalyzerApplication {

	public org.slf4j.Logger mainlog = LoggerFactory.getLogger(ExchangeRateAnalyzerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateAnalyzerApplication.class, args);
	}

}
