package com.lcdt.userinfo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.dao.TUserGroupWarehouseRelationMapper;
import com.lcdt.userinfo.dao.WarehousseLinkmanMapper;
import com.lcdt.userinfo.dao.WarehousseLocMapper;
import com.lcdt.userinfo.dao.WarehousseMapper;
import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.*;
import com.lcdt.userinfo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/10.
 */
@Service
public class WarehouseSeviceImpl implements WarehouseService {

    @Autowired
    private WarehousseMapper warehousseMapper;
    @Autowired
    private WarehousseLinkmanMapper warehousseLinkManMapper;
    @Autowired
    private WarehousseLocMapper warehousseLocMapper;

    @Autowired
    TUserGroupWarehouseRelationMapper tUserGroupWarehouseRelationMapper;

    @Autowired
    GroupWareHouseService groupWareHouseService;

    @Autowired
    WarehouseService warehouseService;
    @Override
    public int initWarehouse(UserCompRel userCompRel, Group group) {
        //添加仓库
        Warehouse warehouse = new Warehouse();
        warehouse.setWhName("默认仓库");
        warehouse.setWhType((short)0);
        warehouse.setPrincipal(userCompRel.getUser().getRealName());
        warehouse.setMobile(userCompRel.getUser().getPhone());
        warehouse.setWhStatus((short)0);
        warehouse.setCreateId(userCompRel.getUserId());
        warehouse.setCreateName(userCompRel.getUser().getRealName());
        warehouse.setCreateDate(new Date());
        warehouse.setIsDeleted((short)0);
        warehouse.setCompanyId(userCompRel.getCompId());
        int result = warehousseMapper.insert(warehouse);
        //添加仓库默认联系人
        WarehouseLinkman linkman = new WarehouseLinkman();
        linkman.setWhId(warehouse.getWhId());
        linkman.setName(warehouse.getPrincipal());
        linkman.setMobile(warehouse.getMobile());
        linkman.setCreateId(warehouse.getCreateId());
        linkman.setCreateName(warehouse.getCreateName());
        linkman.setCreateDate(new Date());
        linkman.setIsDefault((short)1);
        linkman.setIsDeleted((short)0);
        linkman.setCompanyId(warehouse.getCompanyId());
        result += addWarehouseLinkMan(linkman);

        //添加仓库与业务组关系

        groupWareHouseService.addWareHouseRelation(group.getGroupId(),userCompRel.getCompId(),warehouse.getWhId());

        //初始化库位信息
        WarehouseLoc loc = new WarehouseLoc();
        loc.setCreateDate(new Date());
        loc.setCreateId(userCompRel.getUserId());
        loc.setCompanyId(userCompRel.getCompId());
        loc.setCreateName(userCompRel.getName());
        loc.setName("默认库位");
        loc.setWhId(warehouse.getWhId());
        loc.setCode("MOREN");
        loc.setStatus((short) 1);
        result+=warehouseService.addWarehouseLoc(loc);
        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo warehouseList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Warehouse> list = warehousseMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        int result = warehousseMapper.insert(warehouse);
        WarehouseLinkman linkman = new WarehouseLinkman();
        linkman.setWhId(warehouse.getWhId());
        linkman.setName(warehouse.getPrincipal());
        linkman.setMobile(warehouse.getMobile());
        linkman.setMail(warehouse.getMail());
        linkman.setProvince(warehouse.getProvince());
        linkman.setCity(warehouse.getCity());
        linkman.setCounty(warehouse.getCounty());
        linkman.setDetailAddress(warehouse.getDetailAddress());
        linkman.setCreateId(warehouse.getCreateId());
        linkman.setCreateName(warehouse.getCreateName());
        linkman.setCreateDate(new Date());
        linkman.setIsDefault((short)1);
        linkman.setIsDeleted((short)0);
        linkman.setCompanyId(warehouse.getCompanyId());
        result += addWarehouseLinkMan(linkman);
        return result;
    }

    @Override
    public int modifyWarehouse(Warehouse warehouse) {
        int result = warehousseMapper.updateByPrimaryKey(warehouse);
        return result;
    }

    @Override
    public int modifyWarehouseIsDelete(Long whId) {
        int result = warehousseMapper.updateIsDeleteByPrimaryKey(whId);
        return result;
    }

    @Override
    public int modifyWarehouseWhStatus(Long whId) {
        Warehouse warehouse = warehousseMapper.selectByPrimaryKey(whId);
        int result = 0;
        if(warehouse != null){
            if(warehouse.getWhStatus() == 0) {
                warehouse.setWhStatus((short)1);
            }else{
                warehouse.setWhStatus((short)0);
            }
            result = warehousseMapper.updateByPrimaryKey(warehouse);
        }
        return result;
    }
    //----------仓库联系人----------
    @Override
    public PageInfo warehouseLinkManList(Map m) {
        List<WarehouseLinkman> list = warehousseLinkManMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public int addWarehouseLinkMan(WarehouseLinkman linkman) {
        int result = warehousseLinkManMapper.insert(linkman);
        return result;
    }

    @Override
    public int modifyWarehouseLinkMan(WarehouseLinkman linkman) {
        int result = warehousseLinkManMapper.updateByPrimaryKey(linkman);
        return result;
    }

    @Override
    public int modifyWarehouseLinkManIsDelete(Long whLinkmanId) {
        int result = warehousseLinkManMapper.updateIsDeleteByPrimaryKey(whLinkmanId);
        return result;
    }

    @Override
    public int modifyWarehouseLinkManIsDefault(WarehouseLinkman linkman) {
        if(linkman.getIsDefault() != null && linkman.getIsDefault() == 1){
            WarehouseLinkman oldLinkman = warehousseLinkManMapper.selectByPrimaryKey(linkman.getWhLinkmanId());
            if(oldLinkman != null && oldLinkman.getWhId() != null) {
                warehousseLinkManMapper.updateIsDefaultByWhId(oldLinkman.getWhId());
            }
        }
        int result = warehousseLinkManMapper.updateIsDefaultByPrimaryKey(linkman);
        return result;
    }
    //----------库位列表----------
    @Override
    public PageInfo warehouseLocList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<WarehouseLoc> list = warehousseLocMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public int addWarehouseLoc(WarehouseLoc loc) {
        int result = warehousseLocMapper.insert(loc);
        return result;
    }

    @Override
    public int modifyWarehouseLoc(WarehouseLoc loc) {
        int result = warehousseLocMapper.updateByPrimaryKey(loc);
        return result;
    }

    @Override
    public int modifyWarehouseLocIsDelete(Long whLocId) {
        int result = warehousseLocMapper.updateIsDeleteByPrimaryKey(whLocId);
        return result;
    }

    @Override
    public int modifyWarehouseLocStatus(Long whLocId) {
        WarehouseLoc loc = warehousseLocMapper.selectByPrimaryKey(whLocId);
        int result = 0;
        if(loc != null){
            if(loc.getStatus() == 0) {
                loc.setStatus((short)1);
            }else{
                loc.setStatus((short)0);
            }
            result = warehousseLocMapper.updateByPrimaryKey(loc);
        }
        return result;
    }
}
