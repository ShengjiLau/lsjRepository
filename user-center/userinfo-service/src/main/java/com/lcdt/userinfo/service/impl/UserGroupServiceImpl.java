package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.userinfo.dao.UserGroupRelationMapper;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserGroupRelation;
import com.lcdt.userinfo.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ss on 2017/10/24.
 */
@Service
public class UserGroupServiceImpl implements UserGroupService{

	@Autowired
	UserGroupRelationMapper userGroupDao;

	@Override
	public List<UserGroupRelation> userGroups(Long userId,Long companyId) {
		List<UserGroupRelation> userGroupRelations = userGroupDao.selectByUserCompany(userId, companyId);
		return userGroupRelations;
	}



	@Override
	public UserGroupRelation addUserToGroup(Long companyId,Long userId, Group group) {
		UserGroupRelation userGroupRelation = new UserGroupRelation();
		userGroupRelation.setCompanyId(companyId);
		userGroupRelation.setUserId(userId);
		userGroupRelation.setGroupId(group.getGroupId());
		userGroupDao.insert(userGroupRelation);
		return userGroupRelation;
	}

	//TODO 删除之前应该判
	@Override
	public boolean removeUserFromGroup(UserGroupRelation relation) {
		userGroupDao.deleteByPrimaryKey(relation.getRelationId());
		return true;
	}
}
