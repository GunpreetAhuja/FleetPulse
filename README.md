
# FleetPulse — Fleet Management Platform 🚗

![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Docker](https://img.shields.io/badge/docker-ready-blue)
![Java](https://img.shields.io/badge/java-17-orange)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.3-brightgreen)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

FleetPulse is a Spring Boot demo project demonstrating fleet management workflows with ActiveMQ messaging for asynchronous maintenance tasks and real-time alerts.

## Features
- REST APIs for Vehicles and Operations
- ActiveMQ Queue for maintenance job scheduling (point-to-point)
- ActiveMQ Topic for alerts broadcasting (pub/sub)
- PostgreSQL persistence with Hibernate
- Docker + Docker Compose for local development
- Jenkins CI/CD pipeline
- Integration tests using embedded ActiveMQ broker

## Quick Start

### With Docker Compose
```bash
docker compose up --build
# App: http://localhost:8081
# ActiveMQ console: http://localhost:8161 (admin/admin)
# Postgres: localhost:5432
```

### Run Locally
1. Start ActiveMQ in Docker:
```bash
docker run -it --rm -p 61616:61616 -p 8161:8161 \
  -e ACTIVEMQ_ADMIN_LOGIN=admin -e ACTIVEMQ_ADMIN_PASSWORD=admin \
  apache/activemq-classic:5.18.3
```
2. Start Postgres:
```bash
docker run -it --rm -p 5432:5432 -e POSTGRES_DB=fleetdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password postgres:15
```
3. Run the app:
```bash
./gradlew bootRun
```

## API Examples
- `GET /api/vehicles` — list vehicles
- `POST /api/vehicles` — create vehicle (JSON body)
- `POST /api/ops/maintenance/{vehicleId}` — schedule maintenance (Queue)
- `POST /api/ops/alerts?msg=...` — broadcast alert (Topic)

## Tests
```bash
./gradlew test
```



