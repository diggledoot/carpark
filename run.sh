#!/bin/bash

echo "Building image!"
mvn clean package

echo "Spinning up instances!"
docker compose up --build
