#!/usr/bin/env bash
cd ..

mvn clean install package
docker build -t 192.168.1.131:5000/test-user-service .
docker push 192.168.1.131:5000/test-user-service
ssh -Tq clms@192.168.1.131 << remotessh
docker pull localhost:5000/test-user-service
docker stop userservice
docker rm userservice
docker run -d --name=userservice --restart=always -e "ZKHOST=172.17.0.3:2181" -p 52411:52411 localhost:5000/test-user-service
exit
remotessh
