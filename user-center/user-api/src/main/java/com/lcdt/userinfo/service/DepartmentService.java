package com.lcdt.userinfo.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.model.UserCompRel;

import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/10/26.
 */
public interface DepartmentService {


    /***
     * 根据主键获取部门信息
     * @param deptId
     * @return
     */
    Department getDepartment(Long deptId);

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
    int getIdsNames(Department obj) throws DeptmentExistException;

    /***
     * 移除部门
     * @param deptId
     * @return
     */
    int removeDepartment(Long deptId, Long companyId);

    /**
     * 部门列表
     * @param m
     * @return
     */
    PageInfo deptmentList(Map m);
    PageInfo deptmentTreeList(Map m);

    /***
     * 获取企业下最大的排序值+1
     * @param m
     * @return
     */
    Long getMaxIndex(Map m);


    String getIdsNames(String departIds);






}
