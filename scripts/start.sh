#!/bin/bash
#本地环境启动脚本
#author 马伟
userServiceName="UserServiceApp"
loginServiceName="CwmsLoginApp"
smsServiceName="NotifyApp"
webAppName="WmsWebApp"

rootFile=`pwd`/..
apps=("UserServiceApp","CwmsLoginApp","NotifyApp","WmsWebApp")
echo ${apps[*]}



rs="jps -l"
running=`${rs} | grep ${userServiceName}`
if test -n "${running}"
then
echo ${running}
else
echo "${rootFile}"
cd ${rootFile}




fi

