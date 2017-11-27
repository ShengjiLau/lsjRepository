#!/bin/bash
#设置虚拟host
SSO_SERVER="login.datuodui.com"
TEST_SERVER="customer.datuodui.com"
DEMO_SERVER="demo.datuodui.com"

HOST_PATH="/etc/hosts";
LOCAL_IP="127.0.0.1"



echo -e "${LOCAL_IP} \t ${SSO_SERVER} " >> /etc/hosts
echo -e "${LOCAL_IP} \t ${TEST_SERVER} " >> /etc/hosts
echo -e "${LOCAL_IP} \t ${DEMO_SERVER} " >> /etc/hosts

