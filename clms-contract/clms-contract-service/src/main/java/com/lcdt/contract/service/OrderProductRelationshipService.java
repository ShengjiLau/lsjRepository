package com.lcdt.contract.service;

import com.lcdt.contract.dao.OrderProductRelationshipDao;
import com.lcdt.contract.dto.OrderProductRelationshipParams;
import com.lcdt.contract.model.OrderProductRelationship;

/**
 * Created by lyqishan on 2018/8/14
 */

public interface OrderProductRelationshipService {

    /**
     * 新增关系
     * @param params
     * @return
     */
    int addRelationship(OrderProductRelationshipParams params);

    /**
     * 修改关系
     * @param params
     * @return
     */
    int modifyRelationship(OrderProductRelationshipParams params);

    /**
     * 查询关系
     * @param relationshipId
     * @return
     */
    OrderProductRelationship queryRelationship(Long relationshipId);

    /**
     * 查询匹配商品
     * @param opId
     * @param companyId
     * @return
     */
    OrderProductRelationshipDao queryRelationshipDao(Long opId,Long companyId);
}
