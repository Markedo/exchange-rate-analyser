package com.alpha.exchangerateanalyser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class ExchangeRateAnalyzerApplicationTests {

	private static final String GIF_URL =  "/api/v1/rates";
	private static final String CHECK_URL ="/api/v1/check";
	private static final String CURRENCY_PARAM = "currency";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noParamTest() throws Exception {
		this.mockMvc.perform(get(GIF_URL)).andDo(print()).
				andExpect(status().isBadRequest()).
				andExpect(content().string(containsString("Request parameter is missing.")));
	}

	@Test
	public void currencyTest() throws Exception {
		this.mockMvc.perform(get(GIF_URL).param(CURRENCY_PARAM , "EUR")).andDo(print()).
				andExpect(status().isOk()).
				andExpect(content().string(containsString("https://giphy.com")));
	}

	@Test
	public void invalidCurrencyTest() throws Exception {
		this.mockMvc.perform(get(GIF_URL).param(CURRENCY_PARAM , "&&&")).andDo(print()).
				andExpect(status().isBadRequest()).
				andExpect(content().string(containsString("Currency invalid or missing")));
	}

	@Test
	public void USDTest() throws Exception {
		this.mockMvc.perform(get(GIF_URL).param(CURRENCY_PARAM , "USD")).andDo(print()).
				andExpect(status().isOk())
				.andExpectAll(content().string(containsString("equal")),
						content().string(containsString("https://giphy.com")));
	}

	@Test
	public void responseChecker() throws Exception {
		this.mockMvc.perform(get(CHECK_URL)).andDo(print()).
				andExpect(status().isOk()).
				andExpect(content().string(containsString("Seems like app works fine")));
	}

}
