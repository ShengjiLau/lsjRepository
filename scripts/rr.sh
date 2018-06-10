#!/bin/bash
while IFS='' read -r line || [[ -n "$line" ]]; do
    str=(${line//:/ })
    if [[ $str == $2* ]];then
        echo ${str[1]}
    fi
done < "$1"
