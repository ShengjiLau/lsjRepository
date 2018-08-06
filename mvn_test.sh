#!/usr/bin/env bash
str=test
main(){
    pwd
    mvn -DargLine="-Dspring.profiles.active=gitlab" test
    echo $?
    if [[ `$?` != 0 ]];then
        echo "push notify"
       push_dingtalk_notification
    fi
    exit $?
}

push_dingtalk_notification(){
 curl 'https://oapi.dingtalk.com/robot/send?access_token=f09e531131ab1d38a06cbb12b68700f994446f57253687fa8f5e420344360972' \
   -H 'Content-Type: application/json' \
   -d "
  {\"msgtype\": \"markdown\",
    \"markdown\":{
        \"title\":\"集成警报\",
        \"text\":\" ${CI_COMMIT_REF_NAME} ${GITLAB_USER_NAME} start job ${CI_JOB_TOKEN}  on ${CI_JOB_STAGE} 编译失败
        \n [${CI_JOB_URL}]\"
    }
  }"
}

main