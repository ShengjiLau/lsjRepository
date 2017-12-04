#!/usr/bin/env bash
#阿里docker registry地址
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
imageid=$(docker images | grep $fullimagename | awk '{print $3}')
docker tag $imageid $imagenamewithtag
docker push $imagenamewithtag
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
docker_login
read -p "请输入镜像名称: " imagename
read -p "请输入镜像tag: " imagetag
read -p "请输入构建路径: " imagepath
push_to_aliregistry $imagename $imagetag $imagepath


