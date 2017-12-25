package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.exception.GroupExistException;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserGroupRelation;

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

	/***
	 * 组用户列表
	 * @param m
	 * @return
	 */
	PageInfo selectGroupUserList(Map m);



	/***
	 * 组用户添加
	 * @param userGroupRelation
	 * @return
	 */
	int groupUserAdd(UserGroupRelation userGroupRelation);


	/***
	 * 组用户删除
	 * @param userGroupRelation
	 * @return
	 */
	int groupUserDelete(UserGroupRelation userGroupRelation);


	/**
	 * 组客户添加
	 *
	 * @param map
	 * @return
	 */
	int groupCustomerAdd(Map map) ;



	/**
	 * 组客户删除
	 *
	 * @param map
	 * @return
	 */
	int groupCustomerdelete(Map map);

}
