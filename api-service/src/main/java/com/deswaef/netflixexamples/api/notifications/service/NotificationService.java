package com.deswaef.netflixexamples.api.notifications.service;

import com.deswaef.netflixexamples.api.infrastructure.Collaborators;
import com.deswaef.netflixexamples.api.notifications.client.NotificationResource;
import com.deswaef.netflixexamples.api.notifications.model.Notification;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private final Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private NotificationResource notificationResource;
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "statusNotFound")
    public InstanceStatus notificationsStatus() {
        return discoveryClient.getNextServerFromEureka(Collaborators.NOTIFICATIONS, false)
                .getStatus();
    }

    public InstanceStatus statusNotFound() {
        return InstanceStatus.DOWN;
    }

    @HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "notificationsAreDown")
    public Observable<String> statusPageUrl() {
        return new ObservableResult<String>() {
            @Override
            public String invoke() {
                return discoveryClient.getNextServerFromEureka(Collaborators.NOTIFICATIONS, false)
                        .getStatusPageUrl();
            }
        };
    }

    @HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "notificationsAreDown")
    public String version() {
        return restTemplate.getForObject("http://notification-service/version", String.class);
    }

    public String notificationsAreDown() {
        LOG.debug("Seems like our notifications-service is down: ");
        return "notification stuff down";
    }

    @HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "noNotifications")
    public List<Notification> notifications() {
        return notificationResource.findAll();
    }

    public List<Notification> noNotifications() {
        return new ArrayList<>();
    }

}
