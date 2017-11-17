package com.lcdt.clms.permission.model;

import com.lcdt.converter.ResponseData;

import java.util.List;

public class Role implements ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_id
     *
     * @mbg.generated
     */
    private Long roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_name
     *
     * @mbg.generated
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_comment
     *
     * @mbg.generated
     */
    private String roleComment;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.role_company_id
     *
     * @mbg.generated
     */
    private Long roleCompanyId;

    private List<Permission> permissions;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_id
     *
     * @return the value of role.role_id
     *
     * @mbg.generated
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_id
     *
     * @param roleId the value for role.role_id
     *
     * @mbg.generated
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_name
     *
     * @return the value of role.role_name
     *
     * @mbg.generated
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_name
     *
     * @param roleName the value for role.role_name
     *
     * @mbg.generated
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_comment
     *
     * @return the value of role.role_comment
     *
     * @mbg.generated
     */
    public String getRoleComment() {
        return roleComment;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_comment
     *
     * @param roleComment the value for role.role_comment
     *
     * @mbg.generated
     */
    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment == null ? null : roleComment.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.role_company_id
     *
     * @return the value of role.role_company_id
     *
     * @mbg.generated
     */
    public Long getRoleCompanyId() {
        return roleCompanyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.role_company_id
     *
     * @param roleCompanyId the value for role.role_company_id
     *
     * @mbg.generated
     */
    public void setRoleCompanyId(Long roleCompanyId) {
        this.roleCompanyId = roleCompanyId;
    }
}