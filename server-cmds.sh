#!/usr/bin/env bash

docker login
export IMAGE_NAME=$1
docker-compose -f docker-compose.yaml up -d
