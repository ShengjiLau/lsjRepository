#!/usr/bin/env bash
#阿里docker registry地址
registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/
userservice="user-service 1.0 user-center\userinfo-service"
clms-sso="clms-sso 1.0 cwms-sso\clms-sso-service"
customer-service="customer-service 1.0 clms-customer\clms-customer-service"
docker_login(){
docker login --username=hi35700248@aliyun.com -p A1111777  registry.cn-hangzhou.aliyuncs.com
}




maven_build(){
mvn clean install package -Dmaven.test.skip=true
STATUS=$?

if [[ $STATUS -eq 0 ]] ; then
echo "successful..."
say "successful..."
push_to_aliregistry
else
echo "failed..."
say "failed..."
fi
}
maven_build
docker_login

read -p "Press any key to continue." var