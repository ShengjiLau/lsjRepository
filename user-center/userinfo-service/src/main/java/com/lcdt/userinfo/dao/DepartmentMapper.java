package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_department
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_department
     *
     * @mbg.generated
     */
    int insert(Department record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_department
     *
     * @mbg.generated
     */
    Department selectByPrimaryKey(Long deptId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_department
     *
     * @mbg.generated
     */
    List<Department> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_department
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Department record);

    /***
     * 根据条件查询
     * @param map
     * @return
     */
    List<Department> selectByCondition(Map map);

    List<Department> departmentTreeList(Map map);


    /***
     * 获取当前企业下最大索引
     * @param map
     * @return
     */
    Long getMaxIndex(Map map);
}