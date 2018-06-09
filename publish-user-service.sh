#!/usr/bin/env bash
registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/
docker_login(){
docker login --username=hi35700248@aliyun.com -p A1111777  registry.cn-hangzhou.aliyuncs.com
}


push_to_aliregistry(){
imagename=$1
imagetag=1.0
buildpath=`findprojectpath`
fullimagename=$registry_url$1
imagenamewithtag=$fullimagename:1.0
echo build $imagenamewithtag $buildpath
docker build -t $fullimagename buildpath
imageid=$(docker images | grep $fullimagename | awk 'NR==1{print $3}')
docker tag $imageid $imagenamewithtag
docker push $imagenamewithtag
}

findprojectpath(){
    while IFS='' read -r line || [[ -n "$line" ]]; do
        str=(${line//:/ })
        if [[ $str == $1* ]];then
            echo ${str[1]}
        fi
    done < servicepath
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
if [ $1 == t ];
then
  maven_build
fi

docker_login

push_to_aliregistry $1


