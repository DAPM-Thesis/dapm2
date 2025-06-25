#!/bin/bash
# Bash script to start all Docker Compose stacks in parallel

(docker compose down -v) &
(cd ingestion-service && docker compose down -v) &
(cd operator-service && docker compose down -v) &
(cd sink-service && docker compose down -v) &
(cd client-service && docker compose down -v) &
wait
echo "All Docker Compose services Stoped!"
