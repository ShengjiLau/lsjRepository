package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.DepartmentMapper;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int createDepartment(Department obj) throws DeptmentExistException {
        Map map = new HashMap<>();
        map.put("companyId",obj.getCompanyId());
        map.put("deptName",obj.getDeptName());
        List<Department> list = departmentMapper.selectByCondition(map);
        if (list!=null && list.size()>0) {
            throw new DeptmentExistException();
        } else {
            return departmentMapper.insert(obj);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateDepartment(Department obj) throws DeptmentExistException {
        Map map = new HashMap<>();
        map.put("companyId",obj.getCompanyId());
        map.put("deptName",obj.getDeptName());
        map.put("deptId",obj.getDeptId());
        List<Department> list = departmentMapper.selectByCondition(map);
        if (list!=null && list.size()>0) {
            throw new DeptmentExistException();
        } else {
            return  departmentMapper.updateByPrimaryKey(obj);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int removeDepartment(Long deptId) {
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
        List<Department>  list = departmentMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
