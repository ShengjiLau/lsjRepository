#!/usr/bin/env bash
cd ..

mvn clean install package
docker build -t 192.168.1.131:5000/test-user-service .
docker push 192.168.1.131:5000/test-user-service
