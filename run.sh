#!/bin/bash

mvn clean package -DskipTests
docker build -t local/staff-management-service:latest .

docker-compose down -v --remove-orphans
docker-compose up
