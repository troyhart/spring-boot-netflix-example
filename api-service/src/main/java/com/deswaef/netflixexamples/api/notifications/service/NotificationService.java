package com.deswaef.netflixexamples.api.notifications.service;

import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @HystrixCommand(fallbackMethod = "statusNotFound")
    public InstanceStatus notificationsStatus() {
        return discoveryClient.getNextServerFromEureka("notification-service", false)
                .getStatus();
    }

    public InstanceStatus statusNotFound() {
        return InstanceStatus.DOWN;
    }

}
