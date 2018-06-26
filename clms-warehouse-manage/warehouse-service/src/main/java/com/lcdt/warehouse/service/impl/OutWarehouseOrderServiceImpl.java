package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.lcdt.warehouse.mapper.OutWarehouseOrderMapper;
import com.lcdt.warehouse.service.InventoryService;
import com.lcdt.warehouse.service.OutOrderGoodsInfoService;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.OutWarehousePlanService;
import com.lcdt.warehouse.vo.ConstantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    OutWarehousePlanService outWarehousePlanService;

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
        if (!companyServiceCountService.checkCompanyProductCount(dto.getCompanyId(), "storage_service", 1)) {
            throw new RuntimeException("剩余仓单服务次数不足");
        }

        OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
        dto.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_WATIE_OUTBOUND);
        BeanUtils.copyProperties(dto, outWarehouseOrder);

        //插入出库单
        result += baseMapper.insertOutWarehouseOrder(outWarehouseOrder);
        if (dto.getOutOrderGoodsInfoList() != null && dto.getOutOrderGoodsInfoList().size() > 0) {
            List<OutOrderGoodsInfo> outOrderGoodsInfoList = dto.getOutOrderGoodsInfoList().stream().map(goods -> {
                OutOrderGoodsInfo outOrderGoodsInfo = new OutOrderGoodsInfo();
                BeanUtils.copyProperties(goods, outOrderGoodsInfo);
                outOrderGoodsInfo.setOutorderId(outWarehouseOrder.getOutorderId());
                outOrderGoodsInfo.setCompanyId(outWarehouseOrder.getCompanyId());
                outOrderGoodsInfo.setOutboundQuantity(outOrderGoodsInfo.getGoodsNum());
                inventoryService.lockInventoryNum(outOrderGoodsInfo.getInvertoryId(), outOrderGoodsInfo.getGoodsNum());
                return outOrderGoodsInfo;
            }).collect(Collectors.toList());

            //批量插入出库单明细
            outOrderGoodsInfoService.insertBatch(outOrderGoodsInfoList);
        }

        //扣减运单费用
        if (result > 0) {
            OutWarehouseOrder resultOutWarehouseOrder=baseMapper.selectById(outWarehouseOrder.getOutorderId());
            companyServiceCountService.reduceCompanyProductCount(dto.getCompanyId(), "storage_service", 1, dto.getCreateName(), "出库单消费-"+resultOutWarehouseOrder.getOutorderNo());
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

        if (params.getOrderStatus() != null && params.getOrderStatus() == ConstantVO.IN_ORDER_STATUS_HAVE_CANCEL) {
            OutWhOrderDto outWhOrderDto = queryOutWarehouseOrder(params.getCompanyId(), params.getOutorderId());

            //解锁库存
            List<OutOrderGoodsInfoDto> outOrderGoodsInfoDtoList = outWhOrderDto.getOutOrderGoodsInfoList();
            if (outOrderGoodsInfoDtoList != null && outOrderGoodsInfoDtoList.size() > 0) {
                outOrderGoodsInfoDtoList.forEach(goods -> inventoryService.unLockInventoryNum(goods.getInvertoryId(), goods.getGoodsNum()));
            }

            //通知计划改变状态
            if (outWhOrderDto != null && outWhOrderDto.getOutPlanId() != null) {
                OutWhPlanDto whPlanDto = new OutWhPlanDto();
                UserCompRel userCompRel = new UserCompRel();
                whPlanDto.setOutplanId(outWhOrderDto.getOutPlanId());

                User user = new User();
                Company company = new Company();
                user.setUserId(params.getUpdateId());
                user.setRealName(params.getUpdateName());
                company.setCompId(params.getCompanyId());
                userCompRel.setUser(user);
                userCompRel.setCompany(company);
                outWarehousePlanService.changeOutWarehousePlanStatus(whPlanDto, userCompRel);
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
        List<OutOrderGoodsInfo> modifyOutOrderGoodsInfoList = null;
        if (listParams != null && listParams.size() > 0) {
            modifyOutOrderGoodsInfoList = listParams.stream()
                    .map(params -> {
                        OutOrderGoodsInfo outorderGoodsInfo = new OutOrderGoodsInfo();
                        BeanUtils.copyProperties(params, outorderGoodsInfo);
                        return outorderGoodsInfo;
                    }).collect(Collectors.toList());

            if (modifyOutOrderGoodsInfoList != null && modifyOutOrderGoodsInfoList.size() > 0) {
                result = outOrderGoodsInfoService.updateBatchById(modifyOutOrderGoodsInfoList);
            }
        }
        //更新入库单状态
        result = modifyOutOrderStatus(modifyParams);

        //出库减库存
        OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
        outWarehouseOrder = queryOutWarehouseOrder(modifyParams.getCompanyId(), modifyParams.getOutorderId());
        outWarehouseOrder.setOutboundMan(modifyParams.getUpdateName());
        inventoryService.outInventory(outWarehouseOrder, modifyOutOrderGoodsInfoList);

        return result;
    }

    @Override
    public List<DistributionRecordsOutOrderDto> queryOutOrderDisRecords(Long companyId, Long outPlanId) {
        return baseMapper.selectOutOrderDisRecords(companyId, outPlanId);
    }

    /**
     * 概览出库单已完成数量
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> selectOutWarehouseNum(OutWhOrderSearchDto params) {
        return baseMapper.selectOutWarehouseNum(params);
    }


}
