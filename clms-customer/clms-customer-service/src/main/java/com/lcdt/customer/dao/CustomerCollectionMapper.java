package com.lcdt.customer.dao;

import com.lcdt.customer.model.CustomerCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustomerCollectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_collection
     *
     * @mbg.generated Fri Dec 01 19:16:17 CST 2017
     */
    int deleteByPrimaryKey(Map map);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_collection
     *
     * @mbg.generated Fri Dec 01 19:16:17 CST 2017
     */
    int insert(CustomerCollection record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_collection
     *
     * @mbg.generated Fri Dec 01 19:16:17 CST 2017
     */
    CustomerCollection selectByPrimaryKey(Long collectionId, Long companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_collection
     *
     * @mbg.generated Fri Dec 01 19:16:17 CST 2017
     */
    List<CustomerCollection> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_customer_collection
     *
     * @mbg.generated Fri Dec 01 19:16:17 CST 2017
     */
    int updateByPrimaryKey(CustomerCollection record);

    /***
     * 按条件查询
     * @param map
     * @return
     */
    List<CustomerCollection> selectByCondition(Map map);


    List<CustomerCollection> selectByCollectionIds(@Param("companyId") Long companyId, @Param("ids") String ids);


}