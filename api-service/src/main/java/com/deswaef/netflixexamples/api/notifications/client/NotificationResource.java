package com.deswaef.netflixexamples.api.notifications.client;

import com.deswaef.netflixexamples.api.notifications.model.Notification;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient("http://notification-service")
public interface NotificationResource {

    @RequestMapping(value = "/version", method = GET)
    String version();

    @RequestMapping(value = "/notifications", method = GET)
    List<Notification> findAll();

}
