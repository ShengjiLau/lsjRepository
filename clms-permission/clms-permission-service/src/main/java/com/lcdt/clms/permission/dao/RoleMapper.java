package com.lcdt.clms.permission.dao;

import com.lcdt.clms.permission.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated
     */
    int insert(Role record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated
     */
    Role selectByPrimaryKey(Long roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated
     */
    List<Role> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Role record);

    List<Role> selectByCompanyId(Long companyId);

    List<Role> selectUserCompanyRoles(@Param("userId") Long userId, @Param("companyId") Long companyId);

    List<Role> selectByCondition(Map map);
    List<Role> selectByRoleName(@Param("roleName") String roleName,@Param("companyId") Long companyId);
}