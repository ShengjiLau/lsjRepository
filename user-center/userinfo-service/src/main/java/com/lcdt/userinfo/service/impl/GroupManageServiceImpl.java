package com.lcdt.userinfo.service.impl;

import com.lcdt.userinfo.dao.GroupMapper;
import com.lcdt.userinfo.dao.UserGroupRelationMapper;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserGroupRelation;
import com.lcdt.userinfo.service.GroupManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ss on 2017/10/19.
 */
public class GroupManageServiceImpl implements GroupManageService {

	@Autowired
	public GroupMapper groupDao;

	@Autowired
	public UserGroupRelationMapper relationDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteGroup(Group group) {
		List<UserGroupRelation> userGroupRelations = relationDao.selectByGroupId(group.getGroupId());
		if (userGroupRelations != null && !userGroupRelations.isEmpty()) {
			return false;
		}
		groupDao.deleteByPrimaryKey(group.getGroupId());
		return true;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Group createGroup(Group group) {
		groupDao.insert(group);
		return group;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Group modifyGroupName(Group originalGroup, String newName) {
		if (originalGroup != null) {
			originalGroup.setGroupName(newName);
		}
		groupDao.updateByPrimaryKey(originalGroup);
		return originalGroup;
	}
}
