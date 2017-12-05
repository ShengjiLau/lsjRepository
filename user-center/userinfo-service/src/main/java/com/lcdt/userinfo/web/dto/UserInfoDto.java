package com.lcdt.userinfo.web.dto;

import com.lcdt.converter.ResponseData;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;

import java.util.List;

/**
 * Created by ss on 2017/12/5.
 */
public class UserInfoDto implements ResponseData {

	private User user;
	private List<Group> groups;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
