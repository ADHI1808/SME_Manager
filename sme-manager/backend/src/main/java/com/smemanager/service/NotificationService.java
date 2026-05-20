package com.smemanager.service;

import com.smemanager.entity.CohortRequest;
import com.smemanager.entity.Sme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    @Value("${app.teams.notification.enabled:false}")
    private boolean notificationsEnabled;

    @Async
    public void sendFirstPing(Sme sme, CohortRequest cohort, String responseToken) {
        logger.info("First Ping -> SME: " + sme.getFullName()
                + " | Cohort: " + cohort.getCohortId()
                + " | Token: " + responseToken);

        if (!notificationsEnabled) {
            logger.info("[MOCK TEAMS PING] Would send to: " + sme.getTeamsId()
                    + " | Portal: http://localhost:4200/respond/" + responseToken);
        }
    }
}
