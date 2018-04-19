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
readonly USER_SERVICE="user-service 1.0 user-center/userinfo-service"
readonly SSO_SERVICE="sso_service 1.0 cwms-sso/clms-sso-service"
readonly NOTIFY_SERVICE="notify-service 1.0 clms-notify/clms-notify-service"
readonly PAY_SERVICE="pay-service 1.0 clms-pay-service/pay-service"
readonly ITEMS_SERVICE="items-service 1.0 cwms-items/cwms-items-service"
readonly CUSTOMER_SERVICE="customer-service 1.0 clms-customer/clms-customer-service"
readonly QUARTZ_SERVICE="quartz-service 1.0 common-quartz/clms-quartz-service"
readonly TRAFFIC_SERVICE="traffic-service 1.0 clms-traffic/clms-traffic-service"
readonly CONTRACT_SERVICE="contract-service 1.0 cwms-contract/cwms-contract-service"
readonly DRIVER_WECHAT_API="driver-wechat-api 1.0 driver-app-wechat-api"

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
service_name=$USER_SERVICE
;;
"2")
service_name=$SSO_SERVICE
;;
"3")
service_name=$NOTIFY_SERVICE
;;
"4")
service_name=$PAY_SERVICE
;;
"5")
service_name=$ITEMS_SERVICE
;;
"6")
service_name=$CUSTOMER_SERVICE
;;
"7")
service_name=$QUARTZ_SERVICE
;;
"8")
service_name=$TRAFFIC_SERVICE
;;
"9")
service_name=$CONTRACT_SERVICE
;;
"10")
service_name=$DRIVER_WECHAT_API
;;
esac

echo $service_name

#上传
push_to_aliregistry $service_name


readonly TEST_CLMS="clms"
readonly TEST_DRIVER_WECHAT_API="driver-wechat-api"


echo "请选择应用名字：
1)、clms
2)、driver-wechat-api"

read -p "请选择应用名字对应的数字：" APP_NUM

app_name=""

case $APP_NUM in
"1")
app_name=$TEST_CLMS
;;
"2")
app_name=$TEST_DRIVER_WECHAT_API
;;
esac

echo $app_name

#更新应用名字
update_server $app_name