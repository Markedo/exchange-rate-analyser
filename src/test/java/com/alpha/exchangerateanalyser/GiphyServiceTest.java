package com.alpha.exchangerateanalyser;

import com.alpha.exchangerateanalyser.intefraces.GiphyInterface;
import com.alpha.exchangerateanalyser.service.GiphyService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class GiphyServiceTest {

    @Autowired
    GiphyService gifService;

    @MockBean
    GiphyInterface giphyInterface;

    String keyword = "testKeyword";

    @Test
    void randomGifRequestTest() throws Exception {
        JSONObject gifResponse = new JSONObject();
        gifResponse.put("url", "http://plain-url.com");
        gifResponse.put("embed_url", "http://embed-url.com");
        gifResponse.put("notURL", "errorerrorerror");
        ResponseEntity<String> respEnt = ResponseEntity.ok(gifResponse.toString());

        Mockito.when(giphyInterface.requestRandomGif(any(),any())).thenReturn(respEnt);

        String answer = gifService.requestRandomGif(keyword);

        assertThat(answer).isEqualTo(gifResponse.get("embed_url"));
    }
}
