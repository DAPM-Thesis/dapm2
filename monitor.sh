#!/bin/bash

declare -A services=(
  [ingestion]="~/dapm2/ingestion-service"
  [operator]="~/dapm2/operator-service"
  [sink]="~/dapm2/sink-service"
  [client]="~/dapm2/client-service"
)

while true
do
  for service in "${!services[@]}"
  do
    if ! screen -list | grep -q "$service"; then
      echo "🔁 Restarting $service (detected down)..."
      screen -dmS $service bash -c "cd ${services[$service]} && mvn spring-boot:run"
    fi
  done
  sleep 20  # check every 20 seconds
done
