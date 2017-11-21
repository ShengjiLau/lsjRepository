package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.exception.GroupExistException;
import com.lcdt.userinfo.model.Group;

import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/10/19.
 */
public interface GroupManageService {

	boolean deleteGroup(Group group);

	Group createGroup(Group group) throws GroupExistException;

	Group modifyGroup(Group originalGroup) throws GroupExistException;

	PageInfo groupList(Map m);

	void setCompanyUserGroup(Long userId, Long companyId, List<Long> roleId);

	void updateCompanyUsergroup(Long userId, Long companyId, List<Long> groups);
	List<Group> userCompanyGroups(Long userId, Long companyId);
}
