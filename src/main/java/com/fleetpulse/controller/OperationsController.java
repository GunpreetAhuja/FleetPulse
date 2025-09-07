
package com.fleetpulse.controller;

import com.fleetpulse.service.AlertProducer;
import com.fleetpulse.service.MaintenanceProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ops")
public class OperationsController {

    private final MaintenanceProducer maintenanceProducer;
    private final AlertProducer alertProducer;

    public OperationsController(MaintenanceProducer maintenanceProducer, AlertProducer alertProducer) {
        this.maintenanceProducer = maintenanceProducer;
        this.alertProducer = alertProducer;
    }

    @PostMapping("/maintenance/{vehicleId}")
    public ResponseEntity<String> schedule(@PathVariable Long vehicleId) {
        maintenanceProducer.scheduleMaintenance(vehicleId);
        return ResponseEntity.accepted().body("Maintenance scheduled for " + vehicleId);
    }

    @PostMapping("/alerts")
    public ResponseEntity<String> alert(@RequestParam String msg) {
        alertProducer.broadcastAlert(msg);
        return ResponseEntity.ok("Alert broadcasted");
    }
}
