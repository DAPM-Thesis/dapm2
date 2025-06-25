#!/bin/bash
# Bash script to start all Docker Compose stacks in parallel

(docker compose up --build -d) &
(cd ingestion-service && docker compose up --build -d) &
(cd operator-service && docker compose up --build -d) &
(cd sink-service && docker compose up --build -d) &
(cd client-service && docker compose up --build -d) &
wait
echo "All Docker Compose services started!"
