package com.lcdt.traffic.util;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Group;

import java.util.List;

/**
 * Created by lyqishan on 2018/3/24
 */

public class GroupIdsUtil {

    public static String getOwnGroupIds(Object groupIds){
        StringBuffer sb = new StringBuffer();
        if (groupIds!=null&&!groupIds.equals("")) {//传业务组，查这个组帮定的客户
            sb.append(" find_in_set('"+groupIds+"',group_id)"); //项目组id
        } else {
            //没传组，查这个用户所有组帮定的客户

            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null&&groupList.size()>0){
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_id)"); //所有项目组ids
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }

        }
        return sb.toString();
    }

    public static String getCustomerGroupIds(Object groupIds){
        //组权限信息
        StringBuffer sb = new StringBuffer();
        if (groupIds!=null&&!groupIds.equals("")) {//传业务组，查这个组帮定的客户
            sb.append(" find_in_set('"+groupIds+"',group_ids)"); //客户表
        } else {
            //没传组，查这个用户所有组帮定的客户
            List<Group> groupList = SecurityInfoGetter.groups();
            if(groupList!=null&&groupList.size()>0){
                sb.append("(");
                for(int i=0;i<groupList.size();i++) {
                    Group group = groupList.get(i);
                    sb.append(" find_in_set('"+group.getGroupId()+"',group_ids)"); //客户表
                    if(i!=groupList.size()-1){
                        sb.append(" or ");
                    }
                }
                sb.append(")");
            }

        }
        return sb.toString();
    }
}
