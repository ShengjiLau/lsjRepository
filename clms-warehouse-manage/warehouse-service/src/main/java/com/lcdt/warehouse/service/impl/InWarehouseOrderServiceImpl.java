package com.lcdt.warehouse.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.warehouse.dto.InWarehouseOrderAddParamsDto;
import com.lcdt.warehouse.dto.InWarehouseOrderSearchParamsDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.mapper.InWarehouseOrderMapper;
import com.lcdt.warehouse.service.GoodsInfoService;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.InorderGoodsInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
@Service
public class InWarehouseOrderServiceImpl extends ServiceImpl<InWarehouseOrderMapper, InWarehouseOrder> implements InWarehouseOrderService {

    @Autowired
    InorderGoodsInfoService inorderGoodsInfoService;

    @Autowired
    GoodsInfoService goodsInfoService;

    @Override
    public int addInWarehouseOrder(InWarehouseOrderAddParamsDto params) {
        int result=0;
        InWarehouseOrder inWarehouseOrder=new InWarehouseOrder();
        BeanUtils.copyProperties(params,inWarehouseOrder);
        result+=baseMapper.insertInWarehouseOrder(inWarehouseOrder);

        if(params.getGoodsInfoDtoList()!=null&&params.getGoodsInfoDtoList().size()>0){
            for(int i=0;i<params.getGoodsInfoDtoList().size();i++){
                GoodsInfo goodsInfo=new GoodsInfo();
                BeanUtils.copyProperties(params.getGoodsInfoDtoList().get(i),goodsInfo);
                goodsInfoService.insert(goodsInfo);

                InorderGoodsInfo inorderGoodsInfo=new InorderGoodsInfo();
                BeanUtils.copyProperties(params.getGoodsInfoDtoList().get(i),inorderGoodsInfo);
                inorderGoodsInfo.setGoodsId(goodsInfo.getGoodsId());
                inorderGoodsInfo.setInorderId(inWarehouseOrder.getInorderId());
                inorderGoodsInfoService.insert(inorderGoodsInfo);
            }

        }
        return result;
    }

    @Override
    public Page<InWarehouseOrder> queryInWarehouseOrderList(InWarehouseOrderSearchParamsDto params) {
        Page page=new Page();
        return page.setRecords(baseMapper.selectByCondition(page,params));
    }
}
