#!/usr/bin/env bash

deploy(){
cd user-center/userinfo-service/
docker build -t 192.168.1.131:5000/test-user-service .
docker push 192.168.1.131:5000/test-user-service
ssh -Tq clms@192.168.1.131 << remotessh
docker pull localhost:5000/test-user-service
docker stop userservice
docker rm userservice
docker run -d --name=userservice --restart=always -e "ZKHOST=172.17.0.3:2181" -e "ssoserverlogin=http://login.datuodui.com:52412/account" -p 52411:52411 -p 20881:20881 localhost:5000/test-user-service
exit
remotessh
}

mvn clean install package
STATUS=$?

if [[ $STATUS -eq 0 ]] ; then
echo "编译成功"
say "编译成功"
deploy
else
echo "编译失败"
say "编译失败"
fi




