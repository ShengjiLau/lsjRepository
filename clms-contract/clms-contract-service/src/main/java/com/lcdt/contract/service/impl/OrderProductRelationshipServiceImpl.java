package com.lcdt.contract.service.impl;

import com.lcdt.contract.dao.OrderProductRelationshipMapper;
import com.lcdt.contract.dto.OrderProductRelationshipParams;
import com.lcdt.contract.model.OrderProductRelationship;
import com.lcdt.contract.service.OrderProductRelationshipService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Created by lyqishan on 2018/8/14
 */

@Service
@Transactional
public class OrderProductRelationshipServiceImpl implements OrderProductRelationshipService {

    @Autowired
    private OrderProductRelationshipMapper orderProductRelationshipMapper;

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
}
