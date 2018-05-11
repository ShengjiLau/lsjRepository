package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InWarehouseOrderDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.dto.InorderGoodsInfoDto;
import com.lcdt.warehouse.dto.ModifyInOrderStatusParamsDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.mapper.InWarehouseOrderMapper;
import com.lcdt.warehouse.service.GoodsInfoService;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.InorderGoodsInfoService;
import com.lcdt.warehouse.vo.ConstantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@Transactional
@Service
public class InWarehouseOrderServiceImpl extends ServiceImpl<InWarehouseOrderMapper, InWarehouseOrder> implements InWarehouseOrderService {

    @Autowired
    InorderGoodsInfoService inorderGoodsInfoService;

    @Override
    public int addInWarehouseOrder(InWarehouseOrderDto params) {
        int result = 0;
        InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
        BeanUtils.copyProperties(params, inWarehouseOrder);
        result += baseMapper.insertInWarehouseOrder(inWarehouseOrder);

        if (params.getGoodsInfoDtoList() != null && params.getGoodsInfoDtoList().size() > 0) {
            List<InorderGoodsInfo> inorderGoodsInfoList = new ArrayList<>();
            for (int i = 0; i < params.getGoodsInfoDtoList().size(); i++) {
                InorderGoodsInfo inorderGoodsInfo = new InorderGoodsInfo();
                BeanUtils.copyProperties(params.getGoodsInfoDtoList().get(i), inorderGoodsInfo);
                inorderGoodsInfo.setInorderId(inWarehouseOrder.getInorderId());
                inorderGoodsInfoList.add(inorderGoodsInfo);
            }
            inorderGoodsInfoService.insertBatch(inorderGoodsInfoList);
        }
        return result;
    }

    @Override
    public Page<InWarehouseOrderDto> queryInWarehouseOrderList(InWarehouseOrderSearchParamsDto params) {
        Page page = new Page(params.getPageNo(), params.getPageSize());
        return page.setRecords(baseMapper.selectByCondition(page, params));
    }

    @Override
    public boolean modifyInOrderStatus(ModifyInOrderStatusParamsDto params) {
        //更新字段
        InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
        BeanUtils.copyProperties(params, inWarehouseOrder);
        if (params.getInOrderStatus() == ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE) {
            inWarehouseOrder.setStorageMan(params.getUpdateName());
        }
        inWarehouseOrder.setUpdateDate(new Date());

        //更新条件
        InWarehouseOrder orderWrapper = new InWarehouseOrder();
        orderWrapper.setCompanyId(params.getCompanyId());
        orderWrapper.setInorderId(params.getInorderId());
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.setEntity(orderWrapper);

        //调用更新的方法
        boolean result = update(inWarehouseOrder, wrapper);

        return result;
    }

    @Override
    public boolean storage(ModifyInOrderStatusParamsDto modifyParams, List<InorderGoodsInfoDto> listParams) {
        //拆分
        List<InorderGoodsInfo> modifyInorderGoodsInfoList = new ArrayList<>();
        List<InorderGoodsInfo> addInorderGoodsInfoList = new ArrayList<>();
        if (listParams != null && listParams.size() > 0) {
            if (addInorderGoodsInfoList != null && addInorderGoodsInfoList.size() > 0) {
                inorderGoodsInfoService.insertBatch(addInorderGoodsInfoList);
            }
            if (modifyInorderGoodsInfoList != null && modifyInorderGoodsInfoList.size() > 0) {
                inorderGoodsInfoService.updateBatchById(modifyInorderGoodsInfoList);
            }
        }
        //更新入库单状态
        modifyInOrderStatus(modifyParams);

        //入库
        return false;
    }

}
