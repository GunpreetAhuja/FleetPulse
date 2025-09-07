
package com.fleetpulse;

import com.fleetpulse.config.JmsConfig;
import com.fleetpulse.service.AlertProducer;
import com.fleetpulse.service.MaintenanceProducer;
import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.activemq.broker-url=tcp://localhost:61617",
        "spring.activemq.user=admin",
        "spring.activemq.password=admin"
})
public class MessagingIntegrationTest {

    private static BrokerService broker;

    @Autowired
    private MaintenanceProducer maintenanceProducer;

    @Autowired
    private AlertProducer alertProducer;

    private static CountDownLatch queueLatch = new CountDownLatch(1);
    private static CountDownLatch topicLatch = new CountDownLatch(1);

    @BeforeAll
    static void startBroker() throws Exception {
        broker = new BrokerService();
        broker.addConnector("tcp://localhost:61617");
        broker.setUseJmx(false);
        broker.setPersistent(false);
        broker.start();
    }

    @AfterAll
    static void stopBroker() throws Exception {
        if (broker != null) {
            broker.stop();
        }
    }

    @Test
    void endToEndMessaging() throws Exception {
        maintenanceProducer.scheduleMaintenance(1L);
        alertProducer.broadcastAlert("Test-Alert-1");

        assertTrue(queueLatch.await(5, TimeUnit.SECONDS));
        assertTrue(topicLatch.await(5, TimeUnit.SECONDS));
    }

    @org.springframework.jms.annotation.JmsListener(destination = JmsConfig.MAINTENANCE_QUEUE, containerFactory = "queueListenerFactory")
    public void queueListener(Long msg) {
        queueLatch.countDown();
    }

    @org.springframework.jms.annotation.JmsListener(destination = JmsConfig.ALERTS_TOPIC, containerFactory = "topicListenerFactory")
    public void topicListener(String msg) {
        topicLatch.countDown();
    }
}
