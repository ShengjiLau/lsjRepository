package com.lcdt.cwms.user.service.impl;

import com.lcdt.cwms.user.dao.WmsCompanyUserGroupMapper;
import com.lcdt.cwms.user.dao.WmsCompanyUserGroupRelationMapper;
import com.lcdt.cwms.user.exception.GroupNotExistException;
import com.lcdt.cwms.user.exception.RelationExistException;
import com.lcdt.cwms.user.model.WmsCompanyUserGroup;
import com.lcdt.cwms.user.model.WmsCompanyUserGroupRelation;
import com.lcdt.cwms.user.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ss on 2017/8/4.
 */
@Transactional
public class GroupServiceImpl implements GroupService {

	@Autowired
	WmsCompanyUserGroupMapper groupDao;

	@Autowired
	WmsCompanyUserGroupRelationMapper userGroupDao;

	private Logger logger = LoggerFactory.getLogger(GroupService.class);

	@Override
	public WmsCompanyUserGroup createGroup(Integer parentGroupId, String groupName, Long companyId) {
		WmsCompanyUserGroup wmsCompanyUserGroup = new WmsCompanyUserGroup();
		wmsCompanyUserGroup.setCompanyId(companyId);
		wmsCompanyUserGroup.setParentGroupId(parentGroupId);
		wmsCompanyUserGroup.setGroupName(groupName);
		groupDao.insert(wmsCompanyUserGroup);
		return wmsCompanyUserGroup;
	}


	@Override
	public WmsCompanyUserGroupRelation addUserToGroup(Integer groupId, Integer userId) throws GroupNotExistException {
		WmsCompanyUserGroup wmsCompanyUserGroup = groupDao.selectByPrimaryKey(groupId);
		if (wmsCompanyUserGroup == null){
			throw new GroupNotExistException();
		}

		WmsCompanyUserGroupRelation relation = new WmsCompanyUserGroupRelation();
		relation.setCompanyId(wmsCompanyUserGroup.getCompanyId());
		relation.setUserId(userId);
		relation.setGroupId(groupId);
		userGroupDao.insert(relation);
		return relation;
	}

	@Override
	public void deleteGroup(Integer groupId) throws RelationExistException {
		List<WmsCompanyUserGroupRelation> relations = userGroupDao.selectByGroupId(groupId);
		if (relations != null && !relations.isEmpty()) {
			throw new RelationExistException();
		}else{
			groupDao.deleteByPrimaryKey(groupId);
		}
	}

	@Override
	public WmsCompanyUserGroup modifyGroupName(Integer groupId, String groupName) {
		WmsCompanyUserGroup wmsCompanyUserGroup = groupDao.selectByPrimaryKey(groupId);
		wmsCompanyUserGroup.setGroupName(groupName);
		groupDao.updateByPrimaryKey(wmsCompanyUserGroup);
		return wmsCompanyUserGroup;
	}

	@Override
	public void removeUserFromGroup(Integer groupId, Integer userId) {
		List<WmsCompanyUserGroupRelation> relation = userGroupDao.selectByGroupUser(groupId, userId);
		if (relation != null && !relation.isEmpty()) {
			WmsCompanyUserGroupRelation wmsCompanyUserGroupRelation = relation.get(0);
			groupDao.deleteByPrimaryKey(wmsCompanyUserGroupRelation.getGroupId());
		}
	}
}
