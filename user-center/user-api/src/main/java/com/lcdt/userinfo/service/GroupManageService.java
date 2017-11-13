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

	Group createGroup(Group group)  throws GroupExistException;

	Group modifyGroup(Group originalGroup) throws GroupExistException;

	PageInfo groupList(Map m);


}
