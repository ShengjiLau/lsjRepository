package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.lcdt.warehouse.mapper.OutWarehouseOrderMapper;
import com.lcdt.warehouse.service.InventoryService;
import com.lcdt.warehouse.service.OutOrderGoodsInfoService;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.vo.ConstantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-25
 */
@Transactional
@Service
public class OutWarehouseOrderServiceImpl extends ServiceImpl<OutWarehouseOrderMapper, OutWarehouseOrder> implements OutWarehouseOrderService {

    @Autowired
    OutOrderGoodsInfoService outOrderGoodsInfoService;

    @Autowired
    InventoryService inventoryService;

    @Reference
    private CompanyServiceCountService companyServiceCountService;

    @Reference()
    SubItemsInfoService goodsService;

    @Override
    public int addOutWarehouseOrder(OutWhOrderDto dto) {
        int result = 0;

        //先判断是否还有剩于运单服务条数(后面计费用)
        if(!companyServiceCountService.checkCompanyProductCount(dto.getCompanyId(),"storage_service", 1)){
            throw new RuntimeException("剩余仓单服务次数不足");
        }

        OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
        dto.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_WATIE_OUTBOUND);
        BeanUtils.copyProperties(dto, outWarehouseOrder);
        //插入出库单
        result += baseMapper.insertOutWarehouseOrder(outWarehouseOrder);
        if (dto.getOutOrderGoodsInfoList() != null && dto.getOutOrderGoodsInfoList().size() > 0) {
            List<OutOrderGoodsInfo> outOrderGoodsInfoList = new ArrayList<>();
            for (int i = 0; i < dto.getOutOrderGoodsInfoList().size(); i++) {
                OutOrderGoodsInfo outOrderGoodsInfo = new OutOrderGoodsInfo();
                BeanUtils.copyProperties(dto.getOutOrderGoodsInfoList().get(i), outOrderGoodsInfo);
                outOrderGoodsInfo.setOutorderId(outWarehouseOrder.getOutorderId());
                outOrderGoodsInfo.setCompanyId(outWarehouseOrder.getCompanyId());
                outOrderGoodsInfo.setOutboundQuantity(outOrderGoodsInfo.getGoodsNum());
                outOrderGoodsInfoList.add(outOrderGoodsInfo);
                inventoryService.lockInventoryNum(outOrderGoodsInfo.getInvertoryId(),outOrderGoodsInfo.getGoodsNum());
            }
            //批量插入出库单明细
            outOrderGoodsInfoService.insertBatch(outOrderGoodsInfoList);

            //直接出库
//            if (dto.getOperationType() == 1) {
//                //对接库存，减库存
//                outWarehouseOrder = queryOutWarehouseOrder(outWarehouseOrder.getCompanyId(),outWarehouseOrder.getOutorderId());
//                outWarehouseOrder.setOutboundMan(dto.getCreateName());
//                inventoryService.outInventory(outWarehouseOrder,outOrderGoodsInfoList);
//            }
        }

        //扣减运单费用
        if (result > 0) {
            companyServiceCountService.reduceCompanyProductCount(dto.getCompanyId(),"storage_service", 1);
        }

        return result;
    }

    @Override
    public Page<OutWhOrderDto> queryOutWarehouseOrderList(OutWhOrderSearchDto params) {
        Page page = new Page(params.getPageNo(), params.getPageSize());
        return page.setRecords(baseMapper.selectByCondition(page, params));
    }

    @Override
    public OutWhOrderDto queryOutWarehouseOrder(Long companyId, Long outorderId) {
        return baseMapper.selectOutWarehouseOrder(companyId, outorderId);
    }

    @Override
    public boolean modifyOutOrderStatus(ModifyOutOrderStatusParamsDto params) {
        //更新字段
        OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
        BeanUtils.copyProperties(params, outWarehouseOrder);
        outWarehouseOrder.setUpdateDate(new Date());

        //更新条件
        OutWarehouseOrder orderWrapper = new OutWarehouseOrder();
        orderWrapper.setCompanyId(params.getCompanyId());
        orderWrapper.setOutorderId(params.getOutorderId());
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.setEntity(orderWrapper);
        //释放锁定库存
        if(params.getOrderStatus()==ConstantVO.IN_ORDER_STATUS_HAVE_CANCEL){
            OutWhOrderDto outWhOrderDto=queryOutWarehouseOrder(params.getCompanyId(),params.getOutorderId());
            List<OutOrderGoodsInfoDto> outOrderGoodsInfoDtoList=outWhOrderDto.getOutOrderGoodsInfoList();
            if(outOrderGoodsInfoDtoList!=null&&outOrderGoodsInfoDtoList.size()>0){
                for(OutOrderGoodsInfoDto dto:outOrderGoodsInfoDtoList){
                    if(dto!=null){
                        inventoryService.unLockInventoryNum(dto.getInvertoryId(),dto.getGoodsNum());
                    }
                }
            }
        }
        //调用更新的方法
        boolean result = update(outWarehouseOrder, wrapper);

        return result;
    }

    @Override
    public boolean outbound(ModifyOutOrderStatusParamsDto modifyParams, List<OutOrderGoodsInfoDto> listParams) {
        //拆分
        boolean result = false;
        //拆分
        List<OutOrderGoodsInfo> modifyOutOrderGoodsInfoList = new ArrayList<>();
        if (listParams != null && listParams.size() > 0) {
            for (OutOrderGoodsInfo infoDto : listParams) {
                OutOrderGoodsInfo outorderGoodsInfo = new OutOrderGoodsInfo();
                BeanUtils.copyProperties(infoDto, outorderGoodsInfo);
                modifyOutOrderGoodsInfoList.add(outorderGoodsInfo);
            }
            if (modifyOutOrderGoodsInfoList != null && modifyOutOrderGoodsInfoList.size() > 0) {
                result = outOrderGoodsInfoService.updateBatchById(modifyOutOrderGoodsInfoList);
            }
        }
        //更新入库单状态
        result = modifyOutOrderStatus(modifyParams);

        //出库减库存
        OutWarehouseOrder outWarehouseOrder=new OutWarehouseOrder();
        outWarehouseOrder = queryOutWarehouseOrder(modifyParams.getCompanyId(),modifyParams.getOutorderId());
        outWarehouseOrder.setOutboundMan(modifyParams.getUpdateName());
        inventoryService.outInventory(outWarehouseOrder,modifyOutOrderGoodsInfoList);

        return result;
    }

    @Override
    public List<DistributionRecordsOutOrderDto> queryOutOrderDisRecords(Long companyId, Long outPlanId) {
        return baseMapper.selectOutOrderDisRecords(companyId,outPlanId);
    }

    /**
     * 概览出库单已完成数量
     * @param params
     * @return
     */
    public List<Map<String,Object>> selectOutWarehouseNum(OutWhOrderSearchDto params){
        return baseMapper.selectOutWarehouseNum(params);
    }


}
