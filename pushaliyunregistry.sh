#!/usr/bin/env bash

pushaliyun(){

docker login --username=hi35700248@aliyun.com -p A1111777  registry.cn-hangzhou.aliyuncs.com
cd user-center/userinfo-service/
docker build -t registry.cn-hangzhou.aliyuncs.com/lcdt-clms/clms/demo-user-service .
imageid=$(docker images | grep registry.cn-hangzhou.aliyuncs.com/lcdt-clms/clms/demo-user-service | awk '{print $3}')
docker tag $imageid registry.cn-hangzhou.aliyuncs.com/lcdt-clms/clms/demo-user-service
docker push registry.cn-hangzhou.aliyuncs.com/lcdt-clms/clms/demo-user-service
}

mvn clean install package -Dmaven.test.skip=true
STATUS=$?

if [[ $STATUS -eq 0 ]] ; then
echo "编译成功"
say "编译成功"
pushaliyun
else
echo "编译失败"
say "编译失败"
fi


