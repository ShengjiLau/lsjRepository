package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.GroupMapper;
import com.lcdt.userinfo.dao.UserGroupRelationMapper;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserGroupRelation;
import com.lcdt.userinfo.service.GroupManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

	public static Logger logger = LoggerFactory.getLogger(GroupManageServiceImpl.class);

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





/*	public Group modifyGroupName(Group originalGroup, String newName) {

		Map map = new HashMap<>();
		map.put("companyId",originalGroup.getCompanyId());
		map.put("deptName",obj.getDeptName());
		map.put("deptId",obj.getDeptId());
		List<Department> list = departmentMapper.selectByCondition(map);
		if (list!=null && list.size()>0) {
			throw new DeptmentExistException("部门已存在");
		} else {
			return  departmentMapper.updateByPrimaryKey(obj);
		}



		if (originalGroup != null) {
			originalGroup.setGroupName(newName);
		}
		groupDao.updateByPrimaryKey(originalGroup);
		return originalGroup;
	}*/
}
