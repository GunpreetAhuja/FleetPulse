
package com.fleetpulse.service;

import com.fleetpulse.repository.VehicleRepository;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class MaintenanceConsumer {

    private final VehicleRepository vehicleRepository;

    public MaintenanceConsumer(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @JmsListener(destination = "maintenance.queue", containerFactory = "queueListenerFactory")
    @Transactional
    public void handleMaintenance(Long vehicleId) {
        System.out.println("📥 Processing maintenance for vehicle " + vehicleId);
        vehicleRepository.findById(vehicleId).ifPresent(v -> {
            v.setStatus("MAINTENANCE_COMPLETE");
            vehicleRepository.save(v);
            System.out.println("✅ Maintenance done for vehicle " + vehicleId);
        });
    }
}
