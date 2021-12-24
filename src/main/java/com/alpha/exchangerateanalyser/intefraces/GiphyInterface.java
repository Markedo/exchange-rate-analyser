package com.alpha.exchangerateanalyser.intefraces;

/*
This interface provides interaction with the Giphy service through API as of 21.12.2021
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Giphy", url = "${GiphyURL}")
public interface GiphyInterface {

    /*
    Since the use of 3rd party libraries is restricted, and GIPHY's JSON is quite huge to form a POJO for it,
    as well as it's useless for our purpose,
    we will receive JSON as string in order to acquire actual URL of the gif file from it
    */

    @GetMapping("/v1/gifs/random")
    String requestRandomGif(@RequestParam("api_key") String api_key);

    @GetMapping("/v1/gifs/random")
    String requestRandomGif(@RequestParam("api_key") String api_key,
                                   @RequestParam(name = "tag", required = false) String tag);

    @GetMapping("/v1/gifs/random")//method with all possible parameters(as of 21.12.2021), not used properly in current version of the app
    String requestRandomGif(@RequestParam("api_key") String api_key,
                                   @RequestParam(name = "tag", required = false) String tag,
                                   @RequestParam(name = "rating", required = false) String rating,
                                   @RequestParam(name = "random_id", required = false) String random_id);

    @GetMapping("/v1/gifs/search")//method with all possible parameters(as of 21.12.2021), not used properly in current version of the app
    String searchGif(@RequestParam("api_key") String api_key,
                                         @RequestParam("q") String q,
                                         @RequestParam(name="limit", required = false) int limit,
                                         @RequestParam(name="offset", required = false) int offset,
                                         @RequestParam(name="rating", required = false) String rating,
                                         @RequestParam(name="lang", required = false) String lang,
                                         @RequestParam(name="random_id", required = false) String random_id,
                                         @RequestParam(name="bundle", required = false) String bundle);
}
