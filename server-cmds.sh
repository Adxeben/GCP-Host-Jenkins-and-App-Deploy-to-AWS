#!/usr/bin/env bash

sleep 60
export IMAGE_NAME=$1
docker-compose -f docker-compose.yaml up -d
