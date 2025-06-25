#!/bin/bash

# Define all services and their screen names and paths
declare -A services=(
  [ingestion]="~/dapm2/ingestion-service"
  [operator]="~/dapm2/operator-service"
  [sink]="~/dapm2/sink-service"
  [client]="~/dapm2/client-service"
)

# Start each service in order
for service in ingestion operator sink client
do
  path=${services[$service]}
  echo "🚀 Starting $service..."
  screen -dmS $service bash -c "cd $path && mvn spring-boot:run"

  # Wait a few seconds for logs to appear
  sleep 10

  # Check if screen is still running
  if screen -list | grep -q "$service"; then
    echo "✅ $service service started in screen session '$service'"
  else
    echo "❌ Failed to start $service"
  fi
done
