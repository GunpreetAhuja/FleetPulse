
package com.fleetpulse.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class AlertSubscriber {

    @JmsListener(destination = "alerts.topic", containerFactory = "topicListenerFactory")
    public void onAlert(String alert) {
        System.out.println("🔔 Received alert: " + alert);
    }
}
