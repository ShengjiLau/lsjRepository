package com.lcdt.userinfo.dao;

import com.lcdt.userinfo.model.MyClientLinkman;

import java.util.List;

public interface MyClientLinkmanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client_linkman
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long myClientLinkmanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client_linkman
     *
     * @mbg.generated
     */
    int insert(MyClientLinkman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client_linkman
     *
     * @mbg.generated
     */
    MyClientLinkman selectByPrimaryKey(Long myClientLinkmanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client_linkman
     *
     * @mbg.generated
     */
    List<MyClientLinkman> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client_linkman
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MyClientLinkman record);
}