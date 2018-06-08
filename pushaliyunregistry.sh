#!/usr/bin/env bash

registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/

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

read_service_file(){
while IFS='' read -r line || [[ -n "$line" ]]; do
    echo "Text read from file: $line"
done < "$1"
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
exit
fi
}


if [ $1 ];then
  maven_build
fi

docker_login

read -p "请输入镜像名称: " imagename
read -p "请输入构建路径: " imagepath
push_to_aliregistry $imagename 1.0 $imagepath
read -p "请输入应用名字: " appname
update_server $appname

