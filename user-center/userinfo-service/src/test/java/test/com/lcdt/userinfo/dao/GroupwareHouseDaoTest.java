package test.com.lcdt.userinfo.dao;

import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.TUserGroupWarehouseRelation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GroupwareHouseDaoTest extends BaseIntegrationContext{

    @Autowired
    GroupWareHouseService groupWareHouseService;

    @Test
    public void testAddedList(){
        List<TUserGroupWarehouseRelation> tUserGroupWarehouseRelations = groupWareHouseService.addedUserGroupWareHouse(1L, 1L);
    }

}
