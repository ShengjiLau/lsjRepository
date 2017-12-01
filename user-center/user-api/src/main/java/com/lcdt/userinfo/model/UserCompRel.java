package com.lcdt.userinfo.model;

import com.lcdt.clms.permission.model.Role;
import com.lcdt.converter.ResponseData;

import java.util.Date;
import java.util.List;

public class UserCompRel implements java.io.Serializable,ResponseData {


    private List<Group> groups;

    private List<Role> roles;

    private Company company;

    private User user;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_uc_user_comp_rel.user_comp_rel_id
     *
     * @mbg.generated
     */
    private Long userCompRelId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_uc_user_comp_rel.user_id
     *
     * @mbg.generated
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_uc_user_comp_rel.comp_id
     *
     * @mbg.generated
     */
    private Long compId;


    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_uc_company.full_name
     *
     * @mbg.generated
     */
    private String fullName;


    private Date createDate;

    private Boolean isEnable;






    private String duty;

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean enable) {
        isEnable = enable;
    }


    public short getIsCreate() {
        return isCreate;
    }

    public void setIsCreate(short isCreate) {
        this.isCreate = isCreate;
    }

    private short isCreate;


    private String deptIds;
    private String deptNames;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_uc_user_comp_rel.user_comp_rel_id
     *
     * @return the value of t_uc_user_comp_rel.user_comp_rel_id
     *
     * @mbg.generated
     */
    public Long getUserCompRelId() {
        return userCompRelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_uc_user_comp_rel.user_comp_rel_id
     *
     * @param userCompRelId the value for t_uc_user_comp_rel.user_comp_rel_id
     *
     * @mbg.generated
     */
    public void setUserCompRelId(Long userCompRelId) {
        this.userCompRelId = userCompRelId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_uc_user_comp_rel.user_id
     *
     * @return the value of t_uc_user_comp_rel.user_id
     *
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_uc_user_comp_rel.user_id
     *
     * @param userId the value for t_uc_user_comp_rel.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_uc_user_comp_rel.comp_id
     *
     * @return the value of t_uc_user_comp_rel.comp_id
     *
     * @mbg.generated
     */
    public Long getCompId() {
        return compId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_uc_user_comp_rel.comp_id
     *
     * @param compId the value for t_uc_user_comp_rel.comp_id
     *
     * @mbg.generated
     */
    public void setCompId(Long compId) {
        this.compId = compId;
    }


    public List<Role> getRoles() {

        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_uc_company.full_name
     *
     * @return the value of t_uc_company.full_name
     *
     * @mbg.generated
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_uc_company.full_name
     *
     * @param fullName the value for t_uc_company.full_name
     *
     * @mbg.generated
     */
    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public String getDeptNames() {
        return deptNames;
    }

    public void setDeptNames(String deptNames) {
        this.deptNames = deptNames;
    }
}