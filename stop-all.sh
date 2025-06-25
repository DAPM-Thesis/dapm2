#!/bin/bash

for service in ingestion operator sink client
do
  echo "🛑 Stopping $service..."
  screen -S $service -X quit
done

echo "✅ All services stopped"
