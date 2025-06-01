# DAPM2 Project

A multi-module Maven project for the Online Distributed Architecture for Process Mining (DAPM), containing two Spring Boot services:

* ingestion-service: fetches raw events and publishes to Kafka
* sink-service: consumes processed events from Kafka and stores them in PostgreSQL

---

## Repository Layout

```
dapm2/
├── README.md
├── pom.xml                    # Parent POM (packaging = pom)
├── docker-compose.yml         # Orchestrates Kafka, Zookeeper, PostgreSQL, MongoDB and both services
├── ingestion-service/         # Child module: Ingestion microservice
│   ├── pom.xml
│   └── src/
│       └── main/resources/
│           └── application.properties
└── sink-service/              # Child module: Sink microservice
    ├── pom.xml
    └── src/
        └── main/resources/
            └── application.properties
```


## Prerequisites

* Git (any recent version)
* JDK 21
* Maven 3.6+
* Docker Engine & Docker Compose (v1.27+)
