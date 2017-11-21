package com.lcdt.client.dao;


import com.lcdt.client.model.MyClientLinkman;

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

    /**
     * 根据myClientId查询相关联系人
     * @param myClientId
     * @return
     */
    List<MyClientLinkman> selectByMyClientId(Long myClientId);

    /**
     * 更新是否为默认
     * @param myClientLinkman
     * @return
     */
    int updateIsDefault(MyClientLinkman myClientLinkman);

}