#!/usr/bin/env bash
#阿里docker registry地址
registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/
userservice="user-service 1.0 user-center/userinfo-service"
clms-sso="clms-sso 1.0 cwms-sso/clms-sso-service"
customer-service="customer-service 1.0 clms-customer/clms-customer-service"
docker_login(){
docker login --username=hi35700248@aliyun.com -p A1111777  registry.cn-hangzhou.aliyuncs.com
}


push_to_aliregistry(){
imagename=$1
imagetag=$2
buildpath=$3
fullimagename=$registry_url$1
imagenamewithtag=$fullimagename:$2
echo $imagenamewithtag
docker build -t $fullimagename $3
imageid=$(docker images | grep $fullimagename | awk 'NR==1{print $3}')
docker tag $imageid $imagenamewithtag
docker push $imagenamewithtag
}

update_server(){
    curl --insecure --cert ~/.docker/aliyun/clms-c/cert.pem --key ~/.docker/aliyun/clms-c/key.pem -X POST https://master2g5.cs-cn-beijing.aliyun.com:20017/projects/$1/redeploy
}

maven_build(){
mvn clean install package -Dmaven.test.skip=true
STATUS=$?

if [[ $STATUS -eq 0 ]] ; then
echo "编译成功"
say "编译成功"
push_to_aliregistry
else
echo "编译失败"
say "编译失败"
fi
}
if [ $1 ];
then
  maven_build
fi


docker_login

#要更新的 服务名 版本号 路径，新增的服务需要添加
user_service="user-service 1.0 user-center/userinfo-service"
sso_service="sso_service 1.0 cwms-sso/clms-sso-service"
notify_service="notify-service 1.0 clms-notify/clms-notify-service"
pay_service="pay-service 1.0 clms-pay-service/pay-service"
items_service="items-service 1.0 cwms-items/cwms-items-service"
customer_service="customer-service 1.0 clms-customer/clms-customer-service"
quartz_service="quartz-service 1.0 common-quartz/clms-quartz-service"
traffic_service="traffic-service 1.0 clms-traffic/clms-traffic-service"
contract_service="contract-service 1.0 cwms-contract/cwms-contract-service"
driver_wechat_api="driver-wechat-api 1.0 driver-app-wechat-api"

echo "请选择要更新的应用：
1)、user-service
2)、sso-service
3)、notify-service
4)、pay-service
5)、items-service
6)、customer-service
7)、quartz-service
8)、traffic-service
9)、contract-service
10)、driver-wechat-api"

read -p "请选择应用对应的数字：" NUM

service_name=""

case $NUM in
"1")
service_name=$user_service
;;
"2")
service_name=$sso_service
;;
"3")
service_name=$notify_service
;;
"4")
service_name=$pay_service
;;
"5")
service_name=$items_service
;;
"6")
service_name=$customer_service
;;
"7")
service_name=$quartz_service
;;
"8")
service_name=$traffic_service
;;
"9")
service_name=$contract_service
;;
"10")
service_name=$driver_wechat_api
;;
esac

#上传
push_to_aliregistry $service_name


test_clms="clms"
test_driver_wechat_api="driver-wechat-api"


echo "请选择应用名字：
1)、clms
2)、driver-wechat-api"

read -p "请选择应用名字对应的数字：" APP_NUM

app_name=""

case $APP_NUM in
"1")
app_name=$test_clms
;;
"2")
app_name=$test_driver_wechat_api
;;
esac

echo $app_name

#更新应用名字
update_server $app_name