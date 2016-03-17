package com.deswaef.netflixexamples.api.notifications;

import com.deswaef.netflixexamples.api.infrastructure.ApiException;
import com.deswaef.netflixexamples.api.notifications.service.NotificationService;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/notifications")
public class NotificationStatusRestController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(method = GET)
    public InstanceStatus status() {
        return notificationService.notificationsStatus();
    }


}
