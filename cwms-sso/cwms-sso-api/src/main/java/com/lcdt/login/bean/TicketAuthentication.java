package com.lcdt.login.bean;

import com.lcdt.clms.permission.model.Permission;
import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ss on 2017/8/25.
 */
public class TicketAuthentication implements Serializable {

	private boolean chooseCompany;
	private UserCompRel userCompRel;
	private User user;

	private List<Group> groups;

	private List<Permission> permissions;
	private List<SysRole> sysRoles;

	private String ticket;


	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<SysRole> getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(List<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public boolean isChooseCompany() {
		return chooseCompany;
	}

	public void setChooseCompany(boolean chooseCompany) {
		this.chooseCompany = chooseCompany;
	}

	public UserCompRel getUserCompRel() {
		return userCompRel;
	}

	public void setUserCompRel(UserCompRel userCompRel) {
		this.userCompRel = userCompRel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
