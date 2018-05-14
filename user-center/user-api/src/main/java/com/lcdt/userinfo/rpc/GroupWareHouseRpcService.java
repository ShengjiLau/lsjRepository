package com.lcdt.userinfo.rpc;

/**
 * Created by liz on 2018/5/14.
 */
public interface GroupWareHouseRpcService {
    boolean addWareHouseRelationBatch(String groupIds, Long userId, Long companyId, Long wareHouseId);
    int deleteWareHouseRelationBatch(Long wareHouseId);
}
