package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.WaybillPlan;

import java.util.List;
import java.util.Map;

public interface WaybillPlanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_plan
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    int deleteByPrimaryKey(Long waybillPlanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_plan
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    int insert(WaybillPlan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_plan
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    WaybillPlan selectByPrimaryKey(Long waybillPlanId, Long companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_plan
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    List<WaybillPlan> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill_plan
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    int updateByPrimaryKey(WaybillPlan record);

    int updateWaybillPlan(WaybillPlan record);


    /***
     * 根据条件查询相关运但计划
     * @param map
     * @return
     */
    List<WaybillPlan> selectByCondition(Map map);
}