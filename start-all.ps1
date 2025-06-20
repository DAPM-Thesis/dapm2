Start-Process powershell -ArgumentList "cd .\ingestion-service; docker compose up --build -d"
Start-Process powershell -ArgumentList "cd .\operator-service; docker compose up --build -d"
Start-Process powershell -ArgumentList "cd .\sink-service; docker compose up --build -d"
Start-Process powershell -ArgumentList "cd .\client-service; docker compose up --build -d"
