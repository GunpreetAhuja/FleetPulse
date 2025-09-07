
package com.fleetpulse.service;

import jakarta.jms.Queue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceProducer {

    private final JmsTemplate jmsTemplate;
    private final Queue maintenanceQueue;

    public MaintenanceProducer(JmsTemplate jmsTemplate, Queue maintenanceQueue) {
        this.jmsTemplate = jmsTemplate;
        this.maintenanceQueue = maintenanceQueue;
    }

    public void scheduleMaintenance(Long vehicleId) {
        jmsTemplate.convertAndSend(this.maintenanceQueue, vehicleId);
        System.out.println("📤 Maintenance scheduled for vehicle " + vehicleId);
    }
}
