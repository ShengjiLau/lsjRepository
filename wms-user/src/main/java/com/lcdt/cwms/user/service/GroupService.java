package com.lcdt.cwms.user.service;

import com.lcdt.cwms.user.exception.GroupNotExistException;
import com.lcdt.cwms.user.exception.RelationExistException;
import com.lcdt.cwms.user.model.WmsCompanyUserGroup;
import com.lcdt.cwms.user.model.WmsCompanyUserGroupRelation;

/**
 * Created by ss on 2017/8/4.
 */
public interface GroupService {

	//新建组
	WmsCompanyUserGroup createGroup(Integer parentGroupId, String groupName, Long companyId);

	//加入组
	WmsCompanyUserGroupRelation addUserToGroup(Integer groupId, Integer userId) throws GroupNotExistException;

	//删除组
	void deleteGroup(Integer groupId) throws RelationExistException;

	//修改组名
	WmsCompanyUserGroup modifyGroupName(Integer groupId, String groupName);

	//从组中移除用户
	void removeUserFromGroup(Integer groupId,Integer userId);


}
