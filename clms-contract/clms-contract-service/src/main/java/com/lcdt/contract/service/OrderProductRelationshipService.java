package com.lcdt.contract.service;

import com.lcdt.contract.model.OrderProductRelationship;

/**
 * Created by lyqishan on 2018/8/14
 */

public interface OrderProductRelationshipService {
    int addRelationship();

    int modifyRelationship();

    OrderProductRelationship queryRelationship();

}
