package com.lcdt.userinfo.model;

import com.lcdt.converter.ResponseData;
import org.springframework.data.annotation.Transient;

import java.util.List;

public class Department implements java.io.Serializable,ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.dept_id
     *
     * @mbg.generated
     */
    private Long deptId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.dept_name
     *
     * @mbg.generated
     */
    private String deptName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.dept_pid
     *
     * @mbg.generated
     */
    private Long deptPid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.dept_order
     *
     * @mbg.generated
     */
    private Long deptOrder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_department.company_id
     *
     * @mbg.generated
     */
    private Long companyId;


    @Transient
    private List<Department> list;

    @Transient
    private short isSub;

    @Transient
    private short childNum;



    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.dept_id
     *
     * @return the value of t_department.dept_id
     *
     * @mbg.generated
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.dept_id
     *
     * @param deptId the value for t_department.dept_id
     *
     * @mbg.generated
     */
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.dept_name
     *
     * @return the value of t_department.dept_name
     *
     * @mbg.generated
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.dept_name
     *
     * @param deptName the value for t_department.dept_name
     *
     * @mbg.generated
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.dept_pid
     *
     * @return the value of t_department.dept_pid
     *
     * @mbg.generated
     */
    public Long getDeptPid() {
        return deptPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.dept_pid
     *
     * @param deptPid the value for t_department.dept_pid
     *
     * @mbg.generate
     */
    public void setDeptPid(Long deptPid) {
        this.deptPid = deptPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.dept_order
     *
     * @return the value of t_department.dept_order
     *
     * @mbg.generated
     */
    public Long getDeptOrder() {
        return deptOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.dept_order
     *
     * @param deptOrder the value for t_department.dept_order
     *
     * @mbg.generated
     */
    public void setDeptOrder(Long deptOrder) {
        this.deptOrder = deptOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_department.company_id
     *
     * @return the value of t_department.company_id
     *
     * @mbg.generated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_department.company_id
     *
     * @param companyId the value for t_department.company_id
     *
     * @mbg.generated
     */

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public List<Department> getList() {
        return list;
    }

    public void setList(List<Department> list) {
        this.list = list;
    }

    public short getIsSub() {
        return isSub;
    }

    public void setIsSub(short isSub) {
        this.isSub = isSub;
    }

    public short getChildNum() {
        return childNum;
    }

    public void setChildNum(short childNum) {
        this.childNum = childNum;
    }
}