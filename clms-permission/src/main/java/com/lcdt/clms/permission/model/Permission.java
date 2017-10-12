package com.lcdt.clms.permission.model;

public class Permission {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_id
     *
     * @mbg.generated
     */
    private Integer permissionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_remark
     *
     * @mbg.generated
     */
    private String permissionRemark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_name
     *
     * @mbg.generated
     */
    private String permissionName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_prefix
     *
     * @mbg.generated
     */
    private String permissionPrefix;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column permission.permission_code
     *
     * @mbg.generated
     */
    private String permissionCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_id
     *
     * @return the value of permission.permission_id
     *
     * @mbg.generated
     */
    public Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_id
     *
     * @param permissionId the value for permission.permission_id
     *
     * @mbg.generated
     */
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_remark
     *
     * @return the value of permission.permission_remark
     *
     * @mbg.generated
     */
    public String getPermissionRemark() {
        return permissionRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_remark
     *
     * @param permissionRemark the value for permission.permission_remark
     *
     * @mbg.generated
     */
    public void setPermissionRemark(String permissionRemark) {
        this.permissionRemark = permissionRemark == null ? null : permissionRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_name
     *
     * @return the value of permission.permission_name
     *
     * @mbg.generated
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_name
     *
     * @param permissionName the value for permission.permission_name
     *
     * @mbg.generated
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_prefix
     *
     * @return the value of permission.permission_prefix
     *
     * @mbg.generated
     */
    public String getPermissionPrefix() {
        return permissionPrefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_prefix
     *
     * @param permissionPrefix the value for permission.permission_prefix
     *
     * @mbg.generated
     */
    public void setPermissionPrefix(String permissionPrefix) {
        this.permissionPrefix = permissionPrefix == null ? null : permissionPrefix.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column permission.permission_code
     *
     * @return the value of permission.permission_code
     *
     * @mbg.generated
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column permission.permission_code
     *
     * @param permissionCode the value for permission.permission_code
     *
     * @mbg.generated
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }
}