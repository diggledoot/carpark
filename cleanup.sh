#!/bin/bash

# Define the volume name
VOLUME_NAME="carpark-main_postgres_data"
IMAGE_NAME="carpark-app-image"

echo "Stopping and removing Docker containers..."
docker compose down

echo "Removing Docker volume: $VOLUME_NAME"
docker volume rm $VOLUME_NAME

echo "Removing Docker image: $IMAGE_NAME"
docker rmi $IMAGE_NAME

echo "Cleanup complete!"