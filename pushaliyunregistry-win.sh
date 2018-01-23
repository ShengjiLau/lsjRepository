#!/usr/bin/env bash
#阿里docker registry地址
registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/
#userservice="user-service 1.0 user-center\userinfo-service"
#clms-sso="clms-sso 1.0 cwms-sso\clms-sso-service"
#customer-service="customer-service 1.0 clms-customer\clms-customer-service"
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
read -p "Press any key to continue." var

docker build -t $fullimagename $3
imageid=$(docker images | grep $fullimagename | awk 'NR==1{print $3}')
docker tag $imageid $imagenamewithtag
docker push $imagenamewithtag
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
if [ $1 ];
then
  maven_build
fi

docker_login

read -p "JingXiang-name: " imagename
read -p "JingXiang-tag: " imagetag
read -p "JingXiang-path: " imagepath
push_to_aliregistry $imagename $imagetag $imagepath

read -p "Press any key to continue." var