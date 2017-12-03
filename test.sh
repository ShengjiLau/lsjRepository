#!/usr/bin/env bash
docker images | grep registry.cn-hangzhou.aliyuncs.com/lcdt-clms/clms/demo-user-service | awk '{print $3}'
id=$(docker images | grep registry.cn-hangzhou.aliyuncs.com/lcdt-clms/clms/demo-user-service | awk '{print $3}')
#id=$(echo 123)
echo $id