package com.lcdt.client.dao;


import com.lcdt.client.model.MyClient;

import java.util.List;
import java.util.Map;

public interface MyClientMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long myClientId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client
     *
     * @mbg.generated
     */
    int insert(MyClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client
     *
     * @mbg.generated
     */
    MyClient selectByPrimaryKey(Long myClientId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client
     *
     * @mbg.generated
     */
    List<MyClient> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_my_client
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(MyClient record);

    /**
     * 根据条件查询客户列表
     * @param map
     * @return
     */
    List<MyClient> selectByCondition(Map map);

    /**
     * 更新状态
     * @param myClient
     * @return
     */
    int updateStatus(MyClient myClient);

}