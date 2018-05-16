package com.lcdt.userinfo.rpc;

import java.util.Map;

/**
 * Created by liz on 2018/5/14.
 */
public interface GroupWareHouseRpcService {
    boolean addWareHouseRelationBatch(String groupIds, Long userId, Long companyId, Long wareHouseId);
    int deleteWareHouseRelationBatch(Long wareHouseId);
    String selectGroupNamesByGroupIds(Map map);
}
