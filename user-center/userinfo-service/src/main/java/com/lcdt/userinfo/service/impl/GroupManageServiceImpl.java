package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.permission.model.RoleUserRelation;
import com.lcdt.customer.model.Customer;
import com.lcdt.userinfo.dao.CustomerMapper;
import com.lcdt.userinfo.dao.GroupMapper;
import com.lcdt.userinfo.dao.UserGroupRelationMapper;
import com.lcdt.userinfo.dao.UserMapper;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserGroupRelation;
import com.lcdt.userinfo.service.GroupManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/10/19.
 */
@Service
public class GroupManageServiceImpl implements GroupManageService {

	@Autowired
	public GroupMapper groupDao;

	@Autowired
	public UserGroupRelationMapper relationDao;


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CustomerMapper customerMapper;

	public static Logger logger = LoggerFactory.getLogger(GroupManageServiceImpl.class);


	@Transactional(rollbackFor = Exception.class)
	public void deleteUserGroupRelation(Long userId,Long companyId){
		relationDao.removeUserGroupRelation(userId,companyId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteGroup(Group group) {
		Group group1 = groupDao.selectByPrimaryKey(group.getGroupId());
		List<UserGroupRelation> userGroupRelations = relationDao.selectByGroupId(group.getGroupId());
		if (userGroupRelations != null && !userGroupRelations.isEmpty()) {
			return false;
		}
		return groupDao.deleteByPrimaryKey(group.getGroupId())==1?true : false;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Group createGroup(Group group) {
		Map map = new HashMap<>();
		map.put("companyId",group.getCompanyId());
		map.put("groupName",group.getGroupName());
		List<Group> list = groupDao.selectByCondition(map);
		if (list!=null && list.size()>0) {
			throw new DeptmentExistException("业务组已存在");
		} else {
			 groupDao.insert(group);
		}
		return group;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Group modifyGroup(Group group) {
		Map map = new HashMap<>();
		map.put("companyId",group.getCompanyId());
		map.put("groupName",group.getGroupName());
		map.put("groupId",group.getGroupId());
		List<Group> list = groupDao.selectByCondition(map);
		if (list!=null && list.size()>0) {
			throw new DeptmentExistException("业务组已存在");
		} else {
			groupDao.updateByPrimaryKey(group);
		}
		return group;
	}


	@Transactional(readOnly = true)
	@Override
	public PageInfo groupList(Map m) {
		int pageNo = 1;
		int pageSize = 0; //0表示所有

		if (m.containsKey("page_no")) {
			if (m.get("page_no") != null) {
				pageNo = (Integer) m.get("page_no");
			}
		}
		if (m.containsKey("page_size")) {
			if (m.get("page_size") != null) {
				pageSize = (Integer) m.get("page_size");
			}
		}
		PageHelper.startPage(pageNo, pageSize);
		List<Group>  list = groupDao.selectByCondition(m);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Transactional
	@Override
	public void setCompanyUserGroup(Long userId, Long companyId, List<Long> groupId) {

		if (groupId == null) {
			return;
		}

		for (Long id : groupId) {
			UserGroupRelation userGroupRelation = new UserGroupRelation();
			userGroupRelation.setCompanyId(companyId);
			userGroupRelation.setUserId(userId);
			userGroupRelation.setGroupId(id);
			relationDao.insert(userGroupRelation);
		}
	}

	@Transactional
	@Override
	public void updateCompanyUsergroup(Long userId, Long companyId, List<Long> groups) {
		//更新group
		//删除被取消的用户组
		if (!CollectionUtils.isEmpty(groups)) {
			relationDao.deleteNotInGroups(userId,companyId,groups);
		}

		//新增用户组
		if (groups != null) {
			List<Group> relations = relationDao.selectByUserCompany(userId, companyId);
			ArrayList<Long> ids = new ArrayList<>();
			for (Group relation : relations) {
				if (relation != null) {
					ids.add(relation.getGroupId());
				}
			}
			groups.removeAll(ids);
			if (groups != null && groups.size() > 0) {
				relationDao.insertGroups(userId,companyId,groups);
			}
		}

	}

	@Override
	@Transactional
	public List<Group> userCompanyGroups(Long userId, Long companyId) {
		return groupDao.selectUserCompanyGroups(userId, companyId);
	}


	@Transactional(readOnly = true)
	@Override
	public PageInfo selectGroupUserList(Map m) {
		int pageNo = 1;
		int pageSize = 0; //0表示所有

		if (m.containsKey("page_no")) {
			if (m.get("page_no") != null) {
				pageNo = (Integer) m.get("page_no");
			}
		}
		if (m.containsKey("page_size")) {
			if (m.get("page_size") != null) {
				pageSize = (Integer) m.get("page_size");
			}
		}
		PageHelper.startPage(pageNo, pageSize);
		List<User> list  = userMapper.selectUser4Group(m);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Transactional
	@Override
	public int groupUserAdd(UserGroupRelation userGroupRelation) {
		List<UserGroupRelation> list = relationDao.selectByUserIdAndCmpId(userGroupRelation);
		if(list!=null && list.size()>0) {
			throw new RuntimeException("成员已存在！");
		}
		return relationDao.insert(userGroupRelation);
	}

	@Transactional
	@Override
	public int groupUserDelete(UserGroupRelation userGroupRelation) {




		return relationDao.deleteByUserGroupRelation(userGroupRelation);
	}

	@Transactional
	@Override
	public int groupCustomerAdd(Map map) {
		Long companyId = Long.valueOf(map.get("companyId").toString());
		Long customerId = Long.valueOf(map.get("customerId").toString());
		String groupId = map.get("groupId").toString();
		String groupName = map.get("groupName").toString();
		Customer customer =  customerMapper.selectByPrimaryKey(customerId,companyId);
		if (customer==null) {
			throw new RuntimeException("获取客户信息失败！");
		}

			if (!StringUtils.isEmpty(customer.getGroupIds())) {
			String groupIds = customer.getGroupIds();
			String[] str1 = groupIds.split(",");
			if (str1!=null && str1.length>0) {
				boolean flag = false;
				for(int i=0;i<str1.length;i++) {
					if(groupId.equals(str1[i])){
						flag=true;
						break;
					}
				}
				if(!flag){
					customer.setGroupIds(customer.getGroupIds()+","+groupId);
				}
			} else {
				//不
			}
		} else {
			customer.setGroupIds(groupId);
		}

		if (!StringUtils.isEmpty(customer.getGroupNames())) {
			String groupNames = customer.getGroupNames();
			String[] str1 = groupNames.split(",");
			if (str1!=null && str1.length>0) {
				boolean flag = false;
				for(int i=0;i<str1.length;i++) {
					if(groupName.equals(str1[i])){
						flag=true;
						break;
					}
				}
				if(!flag){
					customer.setGroupNames(customer.getGroupNames()+","+groupName);
				}
			} else {
				//不
			}
		} else {
			customer.setGroupNames(groupName);
		}
		return customerMapper.updateByPrimaryKey(customer);
	}

	@Transactional
	@Override
	public int groupCustomerdelete(Map map) {
		Long companyId = Long.valueOf(map.get("companyId").toString());
		Long customerId = Long.valueOf(map.get("customerId").toString());

		String groupId = map.get("groupId").toString();
		String groupName = map.get("groupName").toString();
		Customer customer =  customerMapper.selectByPrimaryKey(customerId,companyId);
		if (customer==null) {
			throw new RuntimeException("获取客户信息失败！");
		}
		if(!StringUtils.isEmpty(customer.getGroupIds())) {
			String groupIds = customer.getGroupIds();
			List<String> groupIdsArray = new ArrayList<String>();
			String[] str1 = groupIds.split(",");
			if (str1!=null && str1.length>0) {
				for(int i=0;i<str1.length;i++) {
					if(!groupId.equals(str1[i])){
						groupIdsArray.add(str1[i]);
					}
				}
				customer.setGroupIds(String.join(",",groupIdsArray));
			} else {
				customer.setGroupIds("");
			}
		}
		if (!StringUtils.isEmpty(customer.getGroupIds())) {
			List<String> groupNamesArray = new ArrayList<String>();
			String groupNames = customer.getGroupNames();
			String[] str2 = groupNames.split(",");
			if (str2!=null && str2.length>0) {
				for(int i=0;i<str2.length;i++) {
					if (!groupName.equals(str2[i])){
						groupNamesArray.add(str2[i]);
					}
				}
				customer.setGroupNames(String.join(",",groupNamesArray));
			} else {
				customer.setGroupNames("");
			}
		}

		return customerMapper.updateByPrimaryKey(customer);
	}

	@Transactional
	@Override
	public PageInfo selectGroupClientList(Map m) {
		int pageNo = 1;
		int pageSize = 0; //0表示所有

		if (m.containsKey("page_no")) {
			if (m.get("page_no") != null) {
				pageNo = (Integer) m.get("page_no");
			}
		}
		if (m.containsKey("page_size")) {
			if (m.get("page_size") != null) {
				pageSize = (Integer) m.get("page_size");
			}
		}
		PageHelper.startPage(pageNo, pageSize);
		List<Customer> list  = customerMapper.selectByCondition(m);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}


}
