#!/bin/bash
while IFS='' read -r line || [[ -n "$line" ]]; do
    var=${line//:/ }
    echo ${var}
    for word in var;do

     echo $word

    if [[ ${var:0} == $2 ]];then
        echo ${var:1}
    fi
done < "$1"
