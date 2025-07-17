# DAPM: Distributed Architecture for Process Mining

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)

A Distributed Architecture for Process Mining (DAPM) ingests, filters, analyzes, and visualizes streaming event data in real time. DAPM is composed of four microservices—`Source`, `Operator`, `Sink` and `client-service`—connected via Apache Kafka.

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Technologies](#technologies)
4. [Prerequisites](#prerequisites)
5. [Installation](#installation)
6. [Configuration](#configuration)
7. [Running the Services](#running-the-services)
8. [Visualizing Results](#visualizing-results)
9. [Troubleshooting](#troubleshooting)
10. [Contributing](#contributing)
11. [License](#license)

---

## Project Overview

DAPM demonstrates a scalable, modular pipeline for real-time process mining:

* **Source**: Ingests raw events via Server-Sent Events (SSE), filters and anonymizes them, and publishes to Kafka.
* **Operator**: Implements a Simple Heuristic Miner that consumes from Kafka, computes direct-follow dependencies and frequency scores in real time, and republishes metrics.
* **Sink**: Persists metrics into InfluxDB for downstream visualization in Grafana.

This architecture ensures zero message loss, high throughput, and low end-to-end latency.

## Architecture
<img width="1350" height="820" alt="componentDiagram" src="https://github.com/user-attachments/assets/757f8cea-9115-4d3d-a299-9a3b24a66716" />


## Technologies

* Java 11+
* Apache Maven
* Apache Kafka
* InfluxDB 2.x
* Grafana 8+
* Spring Boot

## Prerequisites

* Docker & Docker Compose 
* Local or remote Kafka cluster
* Local or remote InfluxDB instance
* Grafana installation
* This dapm2 was build on the top model of dapm-pipeline. So befor running this service dapm-pipeline should be build in local system. For dapm-pipeline follow this github repo instructions: `https://github.com/DAPM-Thesis/dapm-thesis`

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-org/dapm.git
   cd dapm
   ```

2. **Build services**

   ```bash
   mvn clean package -DskipTests
   ```

3. **Start dependencies with Docker Compose**
    First create a Kafka Network
    ```bash
   docker network create kafka-cluster
   ```
    Now go to every service root for running the docker container:
   1. for Ingestion go to dapm2/ingestion-service
   2. for Ingestion go to dapm2/operator-service
   3. for Ingestion go to dapm2/sink-service
   4. for Ingestion go to dapm2/client-service
   ```bash
   docker-compose up -d 
   ```

## Configuration

Each service reads configuration from `src/main/resources/application.yml`. Key settings:

* **Kafka**

  ```yaml
  kafka:
    bootstrap-servers: localhost:9092
    topics:
      raw: dapm-raw
      metrics: dapm-metrics
  ```

* **InfluxDB**

  ```yaml
  influx:
    url: http://localhost:8086
    token: YOUR_INFLUX_TOKEN
    org: YOUR_ORG
    bucket: dapm_bucket
  ```

* **Anonymization & Filtering**

  ```yaml
  dapm:
    filter:
      enabled: true
      rules:
        - field: timestamp
          min: 2025-01-01
    anonymization:
      fields:
        - userId
        - sessionId
  ```

Update values in each service’s `application.properties` before running.

## Running the Services

Start each microservice in a separate terminal (or background process) and follow the sequence to run services:
1. ingestion-service
2. operator-service
3. sink-service
4. client-service

```bash
mvn clean instal
mvn spring-boot:run
```

The **Client Service** serves as the entry point for the pipeline: it reads config files from client-service/resources package, applies global filters and anonymization settings, and dynamically registers the Source, Operator, and Sink topics with Kafka before launching the other components.

## Visualizing Results

1.**InfluxDB**: Open `http://localhost:8086`, log in, and add InfluxDB as a data source.
2. **Grafana**: Open `http://localhost:3000`, log in, and add InfluxDB as a data source.
3. **Dashboards**: Import the provided dashboard from root dapm2 to visualize mining metrics of Wikimedia and Student Simulator from main branch or download from below:
[Student Activity During Exam-Grafana.json](https://github.com/user-attachments/files/21302590/Student.Activity.During.Exam-Grafana.json), 
[Wikipedia Event Changes-Grafana.json](https://github.com/user-attachments/files/21302591/Wikipedia.Event.Changes-Grafana.json)


## Troubleshooting

* **Kafka connection issues**: Ensure `bootstrap-servers` matches your cluster.
* **InfluxDB errors**: Verify token, organization, and bucket settings.
* **Permission denied**: Check file permissions and Java version.

## Contributing

Contributions are welcome! Please:

1. Fork the repo
2. Create a feature branch
3. Submit a pull request

Ensure all tests pass and follow the project’s coding conventions.

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.

