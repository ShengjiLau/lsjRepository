#!/usr/bin/env bash
cd ..

mvn clean install package
docker build -t 192.168.1.131:5000/test-sso-service .
docker push 192.168.1.131:5000/test-sso-service
ssh -Tq clms@192.168.1.131 << remotessh
docker pull localhost:5000/test-sso-service
docker stop ssoservice
docker rm ssoservice
docker run -d --name=ssoservice --restart=always -e "ZKHOST=172.17.0.3:2181" -p 52412:52412 localhost:5000/test-sso-service
exit
remotessh