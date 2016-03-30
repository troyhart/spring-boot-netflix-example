package com.deswaef.netflixexamples.api.reddit.client;

import com.deswaef.netflixexamples.api.reddit.model.RedditResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${com.deswaef.reddit.url}")
public interface RedditResource {

    @RequestMapping(method = RequestMethod.GET, value = "/java.json")
    RedditResponse posts();

}
