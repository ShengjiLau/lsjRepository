package com.lcdt.contract.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.contract.dao.OrderProductRelationshipDao;
import com.lcdt.contract.dao.OrderProductRelationshipMapper;
import com.lcdt.contract.dto.OrderProductRelationshipParams;
import com.lcdt.contract.model.OrderProductRelationship;
import com.lcdt.contract.service.OrderProductRelationshipService;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyqishan on 2018/8/14
 */

@Service
@Transactional
public class OrderProductRelationshipServiceImpl implements OrderProductRelationshipService {

    @Autowired
    private OrderProductRelationshipMapper orderProductRelationshipMapper;

    @Reference
    private SubItemsInfoService subItemsInfoService;

    @Override
    public int addRelationship(OrderProductRelationshipParams params) {
        OrderProductRelationship relationship = new OrderProductRelationship();
        BeanUtils.copyProperties(params,relationship);
        return orderProductRelationshipMapper.insert(relationship);
    }

    @Override
    public int modifyRelationship(OrderProductRelationshipParams params) {
        if(params.getRelationshipId()!=null){
            OrderProductRelationship relationship = new OrderProductRelationship();
            BeanUtils.copyProperties(params,relationship);
            return orderProductRelationshipMapper.updateByPrimaryKey(relationship);
        }else{
            return this.addRelationship(params);
        }

    }

    @Override
    public OrderProductRelationship queryRelationship(Long relationshipId) {
        return orderProductRelationshipMapper.selectByPrimaryKey(relationshipId);
    }

    @Override
    public OrderProductRelationshipDao queryRelationshipDao(Long opId,Long companyId) {
        OrderProductRelationshipDao dao=orderProductRelationshipMapper.selectByOpId(opId);
        GoodsListParamsDto dto=new GoodsListParamsDto();
        dto.setCompanyId(companyId);
        List<Long> goodsIds=new ArrayList<>();
        goodsIds.add(dao.getGoodsId());
        dto.setGoodsIds(goodsIds);
        List<GoodsInfoDao> list=subItemsInfoService.queryByCondition(dto).getList();
        if(null != list && !list.isEmpty()){
            dao.setGoodsInfo(list.get(0));
        }
        return dao;
    }
}
