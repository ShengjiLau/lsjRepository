package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class GroupwareHouseDaoTest extends BaseIntegrationContext{

    @Autowired
    GroupWareHouseService groupWareHouseService;

    @Test
    @Rollback
    public void testAddedList(){
        List<TUserGroupWarehouseRelation> tUserGroupWarehouseRelations = groupWareHouseService.addedUserGroupWareHouse(1L, 1L);
        List<TUserGroupWarehouseRelation> tUserGroupWarehouseRelations1 = groupWareHouseService.notAddUserGroupWareHouse(1L, 1L);

//        groupWareHouseService.addedUserGroupWareHouse(1L, 1L);
        if (!CollectionUtils.isEmpty(tUserGroupWarehouseRelations1)) {
            TUserGroupWarehouseRelation relation = tUserGroupWarehouseRelations1.get(0);
            System.out.println(relation.toString());
        }

        groupWareHouseService.addedUserGroupWareHouse(1L, 1L);
    }

}
