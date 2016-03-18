package com.deswaef.netflixexamples.api.notifications.service;

import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.ObservableResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;

@Service
public class NotificationService {

    private static final String SERVICE_NAME = "notification-service";
    private final Log LOG = LogFactory.getLog(this.getClass());

    @Autowired
    private DiscoveryClient discoveryClient;

    @HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "statusNotFound")
    public InstanceStatus notificationsStatus() {
        return discoveryClient.getNextServerFromEureka(SERVICE_NAME, false)
                .getStatus();
    }

    public InstanceStatus statusNotFound() {
        return InstanceStatus.DOWN;
    }

    @HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "statusPageDown")
    public Observable<String> statusPageUrl() {
        return new ObservableResult<String>() {
            @Override
            public String invoke() {
                return discoveryClient.getNextServerFromEureka(SERVICE_NAME, false)
                        .getStatusPageUrl();
            }
        };
    }

    public String statusPageDown() {
        LOG.debug("Seems like our notifications-service is down: ");
        return "notification stuff down";
    }

}
