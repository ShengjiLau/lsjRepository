package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.DepartmentMapper;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.DepartmentResultDto;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/10/26.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private UserCompRelMapper userCompRelMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int createDepartment(Department obj) throws DeptmentExistException {
		Map map = new HashMap<>();
		map.put("companyId", obj.getCompanyId());
		map.put("deptName", obj.getDeptName());
		List<Department> list = departmentMapper.selectByCondition(map);
		if (list != null && list.size() > 0) {
			throw new DeptmentExistException();
		} else {
			return departmentMapper.insert(obj);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int getIdsNames(Department obj) throws DeptmentExistException {
		Map map = new HashMap<>();
		map.put("companyId", obj.getCompanyId());
		map.put("deptName", obj.getDeptName());
		map.put("deptId", obj.getDeptId());
		List<Department> list = departmentMapper.selectByCondition(map);
		if (list != null && list.size() > 0) {
			throw new DeptmentExistException("部门已存在");
		} else {
			return departmentMapper.updateByPrimaryKey(obj);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int removeDepartment(Long deptId, Long companyId) {
		//1、检查部门下是否存在子部门
		Map map = new HashMap<>();
		map.put("deptPid", deptId);
		map.put("companyId", companyId);
		List<Department> list = departmentMapper.selectByCondition(map);
		if (list!=null && list.size()>0) {
			throw new DeptmentExistException("该部门下存在子部门不能删除");
		} else {
			Department department = departmentMapper.selectByPrimaryKey(deptId);
			if (department!=null){
				if (department.getIsDefault()!=null && department.getIsDefault()==1) { //不存在子部门同时是一级部门不能删除
					throw new DeptmentExistException("该部门为一级部门不能删除");
				}
				if (!department.getCompanyId().equals(companyId)) { //不同企业下能删除
					throw new DeptmentExistException("非法删除数据");
				}
			}
		}
		//2、查询企业用户部是否有引引用
		map = new HashMap<>();
		map.put("deptIds", "find_in_set('"+deptId+"',dept_ids)");
		map.put("compId", companyId);
		List<UserCompRel> userCompRelList =  userCompRelMapper.selectByCondition(map);
		if (userCompRelList!=null && userCompRelList.size()>0) {
			throw new DeptmentExistException("该部门已有用户在使用，不能删除");
		}
		return departmentMapper.deleteByPrimaryKey(deptId);
	}

	@Transactional(readOnly = true)
	@Override
	public PageInfo deptmentList(Map m) {
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
		List<Department> list = departmentMapper.selectByCondition(m);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Transactional(readOnly = true)
	@Override
	public PageInfo deptmentTreeList(Map m) {
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
		List<Department> list = departmentMapper.departmentTreeList(m);
		PageInfo pageInfo = new PageInfo(list);
		return pageInfo;
	}

	@Transactional(readOnly = true)
	@Override
	public Long getMaxIndex(Map m) {
		Long maxIndex = departmentMapper.getMaxIndex(m);
		if (maxIndex == null) maxIndex = 0l;
		return ++maxIndex;
	}


	@Transactional(readOnly = true)
	@Override
	public Department getDepartment(Long deptId) {
		return departmentMapper.selectByPrimaryKey(deptId);
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public String getIdsNames(String departIds) {

		if (StringUtils.isEmpty(departIds)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String[] split = departIds.split(",");
		for (String id : split) {
			Department department = departmentMapper.selectByPrimaryKey(Long.valueOf(id));
			if (department == null) {
				throw new NullPointerException("部门不存在");
			}
			sb.append(department.getDeptName()).append(',');
		}
		int i = sb.lastIndexOf(",");
		sb.deleteCharAt(i);
		return sb.toString();
	}

	@Transactional(readOnly = true)
	@Override
	public Map deptChildStat(Long deptPid, Long companyId) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<DepartmentResultDto> departmentResultDtoList = departmentMapper.deptChildStat(deptPid,companyId);
		if (departmentResultDtoList!=null && departmentResultDtoList.size()>0) {
			for (DepartmentResultDto obj :departmentResultDtoList) {
				int empCount = 0;
				String[] ids = obj.getChildIds().split(",");
				if (null!=ids && ids.length>0) {

					//if(ids.length<=2) obj.setChildCount(0);
					//if(ids.length>2) obj.setChildCount(ids.length-2); //减2是因为返回来的数包含了自身及$;
					for (int i=0;i<ids.length;i++) {
						if(ids[i].equals("$")) {
							continue;
						}
						List<UserCompRel>  userCompRelList1 = userCompRelMapper.selectByCompanyIdDepIds(companyId,ids[i]);
						if(userCompRelList1!=null && userCompRelList1.size()>0) {
							empCount += userCompRelList1.size();
						}

					}
					obj.setEmpCount(empCount);
				}
			}
		}

		List<UserCompRel>  userCompRelList = userCompRelMapper.selectByCompanyIdDepIds(companyId,deptPid.toString());
    	resultMap.put("depts",departmentResultDtoList);
		resultMap.put("emps",userCompRelList);
		return resultMap;
	}

}
