package com.lcdt.warehouse.mapper;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.lcdt.warehouse.dto.DistributionRecordsOutOrderDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.dto.OutWhOrderSearchDto;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
public interface OutWarehouseOrderMapper extends BaseMapper<OutWarehouseOrder> {
    /**
     * 新增出库单
     * @param outWarehouseOrder
     * @return
     */
    int insertOutWarehouseOrder(OutWarehouseOrder outWarehouseOrder);

    /**
     * 根据条件查询出库单
     * @param page
     * @param params
     * @return
     */
    List<OutWhOrderDto> selectByCondition(Pagination page, OutWhOrderSearchDto params);

    /**
     * 查询出库单详细
     * @param companyId
     * @param outorderId
     * @return
     */
    OutWhOrderDto selectOutWarehouseOrder(@Param("companyId") Long companyId, @Param("outorderId") Long outorderId);

    /**
     * 获取出库配仓记录
     * @param companyId
     * @param outPlanId
     * @return
     */
    List<DistributionRecordsOutOrderDto> selectOutOrderDisRecords(@Param("companyId") Long companyId,@Param("outPlanId") Long outPlanId);


    /**
     * 概览出库单已完成数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseNum(OutWhOrderSearchDto params);

    /**
     * 概览出库单已完成商品数量
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseProductNum(OutWhOrderSearchDto params);

    /**
     * 出入库汇总出库已完成商品数量
     * @param params
     * @return
     */
    Integer selectOutWarehouseProductNum4Report(OutWhOrderSearchDto params);

    /**
     * 出入库汇总出库已完成商品
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseProduct4Report(Pagination page, OutWhOrderSearchDto params);

    /**
     * 出入库汇总出库已完成商品按仓库分组
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseProduct4ReportGroupWare(OutWhOrderSearchDto params);

    /**
     * 出入库汇总出库已完成商品按仓库分组
     * @param params
     * @return
     */
    List<Map<String,Object>> selectOutWarehouseProduct4ReportGroupCustomer(OutWhOrderSearchDto params);
}
