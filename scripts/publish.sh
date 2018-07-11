#!/usr/bin/env bash
registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/
docker_login(){
docker login --username=hi35700248@aliyun.com -p A1111777  registry.cn-hangzhou.aliyuncs.com
}


push_to_aliregistry(){
cd $basepath

echo build $servicename $basepath/$buildpath
docker build -t $registry_url$servicename $basepath/$buildpath
imageid=$(docker images | grep $registry_url$servicename | awk 'NR==1{print $3}')
docker tag $imageid $registry_url$servicename:1.0
docker push $registry_url$servicename:1.0
}



findprojectpath(){
    cd $scriptpath
    while IFS='' read -r line || [[ -n "$line" ]]; do
        str=(${line//:/ })
        if [[ $str == $1* ]];then
            servicename=${str[0]}
            buildpath=${str[1]}
            break
        fi
    done < servicepath
    echo '----find buildpath----'
    echo ${servicename}:${buildpath}
}



maven_build(){

cd $basepath
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
cd ..
basepath=$(cd `dirname $0`; pwd)
scriptpath=$basepath/scripts
while [ "$1" ]
do
if [ "$1" = "-t" ];then
maven_build
else
servicename=$1
fi
shift 1
done

docker_login
echo $servicename
findprojectpath $servicename
push_to_aliregistry


