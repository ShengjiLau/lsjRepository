#!/usr/bin/env bash
registry_url=registry.cn-hangzhou.aliyuncs.com/lcdt-clms/
docker_login(){
    docker login --username=hi35700248@aliyun.com -p A1111777  registry.cn-hangzhou.aliyuncs.com
}


push_to_aliregistry(){
    cd $BASE_PATH

    echo build $servicename $BASE_PATH/$buildpath
    docker build -t $registry_url$servicename $BASE_PATH/$buildpath
    imageid=$(docker images | grep $registry_url$servicename | awk 'NR==1{print $3}')
    docker tag $imageid $registry_url$servicename:1.0
    docker push $registry_url$servicename:1.0
}



find_project_in_path(){
    cd $SCRIPT_PATH
    while IFS='' read -r line || [[ -n "$line" ]]; do
        str=(${line//:/ })
        if [[ $str == $1* ]];then
            servicename=${str[0]}
            buildpath=${str[1]}
            break
        fi
    exit 2
    done < service_path
    echo '----find build_path----'
    echo ${servicename}:${buildpath}
}



maven_build(){
    cd $BASE_PATH
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

main(){
    docker_login
    echo $service_name
    find_project_in_path $service_name
    push_to_aliregistry
}

DIR="$( cd "$( dirname "$0"  )" && pwd  )"
SCRIPT_PATH=$DIR
cd $SCRIPT_PATH/..
BASE_PATH=`pwd`

while [ "$1" ]
do
if [ "$1" = "-t" ];then
maven_build
else
service_name=$1
fi
shift 1
done

main



