package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;

import java.util.Map;

/**
 * Created by yangbinq on 2017/10/26.
 */
public interface DepartmentService {

    /***
     * 创建部门
     * @param obj
     * @return
     */
    int createDepartment(Department obj) throws DeptmentExistException;

    /***
     * 编辑部门
     * @param obj
     * @return
     */
    int updateDepartment(Department obj) throws DeptmentExistException;

    /***
     * 移除部门
     * @param deptId
     * @return
     */
    int removeDepartment(Long deptId);

    /**
     * 部门列表
     * @param m
     * @return
     */
    PageInfo deptmentList(Map m);





}
