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

    /**
     * 计划取消时查询此计划下是否存在已卸货或者已完成的运单
     * @param map
     * @return
     */
    List<Waybill> selectPlannedWaybill(Map map);

    /**
     * 根据计划id更新运单状态（参数：waybillStatus，updateId，updateName，waybillPlanId，companyId）
     * @param map
     * @return
     */
    int updateOwnWaybillStatusByWaybillPlanId(Map map);

    /**
     * 根据waybillId or waybillPlanId 查询列表 返回带运单货物详细的列表
     * @param map
     * @return
     */
    List<WaybillDao> selectWaybillByIdOrPlanId(Map map);

    /**
     * 根据waybillId or waybillPlanId 查询列表 返回运单列表
     * @param map
     * @return
     */
    List<Waybill> selectWaybillPlanId(Map map);

    /**
     * 根据planId查询列表
     * @param map
     * @return
     */
    List<Waybill> selectWaybillByPlanId(Map map);

    /**
     * 定时用的：根据定位设置条件查询订单
     * @param map
     * @return
     */
    List<Waybill> selectWaybillByPositionSetting(Map map);

    /**
     * 根据waybillCode和companyId查询列表
     * @param map
     * @return
     */
    List<Waybill> selectByCodeAndCompanyId(Map map);


    /**
     * 根据waybillIds查询运单
     */
    List<WaybillDao> selectWaybillByWaybillIds(String waybillIds);


    /**
     * 新增换车记录时更新运单司机信息
     */

    int updateWaybillByTransferRecord(Waybill waybill);

    /**
     * 司机根据条件查询运单
     * @param map
     * @return
     */
    List<WaybillDao> selectDriverByCondition(Map map);

    /**
     * 司机修改运单信息
     * @param map
     * @return
     */
    int updateWaybillByDriver(Map map);
}