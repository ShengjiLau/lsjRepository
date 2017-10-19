package com.lcdt.userinfo.service;

import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserGroupRelation;

import java.util.List;

/**
 * Created by ss on 2017/10/19.
 */
public interface GroupManageService {

	boolean deleteGroup(Group group);

	Group createGroup(Group group);

	Group modifyGroupName(Group originalGroup, String newName);


}
