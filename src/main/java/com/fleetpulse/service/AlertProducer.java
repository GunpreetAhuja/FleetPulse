
package com.fleetpulse.service;

import jakarta.jms.Topic;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertProducer {

    private final JmsTemplate topicJmsTemplate;
    private final Topic alertsTopic;

    public AlertProducer(JmsTemplate topicJmsTemplate, Topic alertsTopic) {
        this.topicJmsTemplate = topicJmsTemplate;
        this.alertsTopic = alertsTopic;
    }

    public void broadcastAlert(String alert) {
        topicJmsTemplate.convertAndSend(alertsTopic, alert);
        System.out.println("📢 Broadcast alert: " + alert);
    }
}
