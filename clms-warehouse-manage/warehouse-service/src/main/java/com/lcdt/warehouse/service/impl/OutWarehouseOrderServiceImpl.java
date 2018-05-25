package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.warehouse.dto.OutWhOrderDto;
import com.lcdt.warehouse.entity.OutOrderGoodsInfo;
import com.lcdt.warehouse.entity.OutWarehouseOrder;
import com.lcdt.warehouse.mapper.OutWarehouseOrderMapper;
import com.lcdt.warehouse.service.OutOrderGoodsInfoService;
import com.lcdt.warehouse.service.OutWarehouseOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
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

    @Override
    public int addOutWarehouseOrder(OutWhOrderDto dto) {
        int result=0;
        OutWarehouseOrder outWarehouseOrder=new OutWarehouseOrder();
        BeanUtils.copyProperties(dto,outWarehouseOrder);
        //插入出库单
        result +=baseMapper.insertOutWarehouseOrder(outWarehouseOrder);
        if (dto.getOutOrderGoodsInfoList() != null && dto.getOutOrderGoodsInfoList().size() > 0) {
            List<OutOrderGoodsInfo> outOrderGoodsInfoList = new ArrayList<>();
            for (int i = 0; i < dto.getOutOrderGoodsInfoList().size(); i++) {
                OutOrderGoodsInfo outOrderGoodsInfo = new OutOrderGoodsInfo();
                BeanUtils.copyProperties(dto.getOutOrderGoodsInfoList().get(i), outOrderGoodsInfo);
                outOrderGoodsInfo.setOutorderId(outOrderGoodsInfo.getOutorderId());
                outOrderGoodsInfoList.add(outOrderGoodsInfo);
            }
            //批量插入出库单明细
            outOrderGoodsInfoService.insertBatch(outOrderGoodsInfoList);

            //直接出库
            if(dto.getOperationType()==1){
                //对接库存，减库存
            }
        }
        return result;
    }
}
