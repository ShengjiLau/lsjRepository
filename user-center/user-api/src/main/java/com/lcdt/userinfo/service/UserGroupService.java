package com.lcdt.userinfo.service;

import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserGroupRelation;

import java.util.List;

/**
 * Created by ss on 2017/10/19.
 */
public interface UserGroupService {

	List<UserGroupRelation> userGroups(User users);

	UserGroupRelation addUserToGroup(User user, Group group);

	boolean removeUserFromGroup(UserGroupRelation relation);

}
