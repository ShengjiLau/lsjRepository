package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.CompanyCertificate;

import java.util.List;

public interface CompanyCertificateMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_uc_company_certificate
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long certiId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_uc_company_certificate
     *
     * @mbg.generated
     */
    int insert(CompanyCertificate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_uc_company_certificate
     *
     * @mbg.generated
     */
    CompanyCertificate selectByPrimaryKey(Long certiId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_uc_company_certificate
     *
     * @mbg.generated
     */
    List<CompanyCertificate> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_uc_company_certificate
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(CompanyCertificate record);
}