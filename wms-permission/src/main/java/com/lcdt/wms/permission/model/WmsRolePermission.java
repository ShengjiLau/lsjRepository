package com.lcdt.wms.permission.model;

public class WmsRolePermission {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_up_role_permission.relation_id
     *
     * @mbg.generated
     */
    private Integer relationId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_up_role_permission.role_id
     *
     * @mbg.generated
     */
    private Integer roleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_up_role_permission.permission_id
     *
     * @mbg.generated
     */
    private Integer permissionId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_up_role_permission.relation_id
     *
     * @return the value of t_up_role_permission.relation_id
     *
     * @mbg.generated
     */
    public Integer getRelationId() {
        return relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_up_role_permission.relation_id
     *
     * @param relationId the value for t_up_role_permission.relation_id
     *
     * @mbg.generated
     */
    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_up_role_permission.role_id
     *
     * @return the value of t_up_role_permission.role_id
     *
     * @mbg.generated
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_up_role_permission.role_id
     *
     * @param roleId the value for t_up_role_permission.role_id
     *
     * @mbg.generated
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_up_role_permission.permission_id
     *
     * @return the value of t_up_role_permission.permission_id
     *
     * @mbg.generated
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_up_role_permission.permission_id
     *
     * @param permissionId the value for t_up_role_permission.permission_id
     *
     * @mbg.generated
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}