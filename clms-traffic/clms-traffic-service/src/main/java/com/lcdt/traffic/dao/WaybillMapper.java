package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillDao;

import java.util.List;
import java.util.Map;

public interface WaybillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    int insert(Waybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    Waybill selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    List<Waybill> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_waybill
     *
     * @mbg.generated Wed Dec 20 10:13:16 CST 2017
     */
    int updateByPrimaryKey(Waybill record);

    /**
     * 我的运单，根据企业id和waybiiId修改
     * @param waybill
     * @return
     */
    int updateByPrimaryKeyAndCompanyId(Waybill waybill);

    /**
     * 客户运单，根据企业id和waybiiId修改
     * @param waybill
     * @return
     */
    int updateByPrimaryKeyAndCarrierCompanyId(Waybill waybill);
    /**
     * 我的运单：根据企业id和waybillId查询
     * @param waybill
     * @return
     */
    WaybillDao selectOwnWaybillByIdAndCompanyId(Waybill waybill);

    /**
     * 客户运单：根据承运商id和waybillId查询
     * @param waybill
     * @return
     */
    WaybillDao selectCustomerWaybillByIdAndCarrierCompanyId(Waybill waybill);
    /**
     * 根据查询条件查询我的运单
     * @param map
     * @return
     */
    List<WaybillDao> selectOwnByCondition(Map map);

    /**
     * 查询条件查询客户运单
     * @param map
     * @return
     */
    List<WaybillDao> selectCustomerByCondition(Map map);

    /**
     * 批量修改我的运单的状态
     * @param map
     * @return
     */
    int updateOwnWaybillStatus(Map map);

    /**
     * 批量修改客户运单的状态
     * @param map
     * @return
     */
    int updateCustomerWaybillStatus(Map map);
}