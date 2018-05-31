package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.rpc.GroupWareHouseRpcService;
import com.lcdt.warehouse.dto.WarehouseDto;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.entity.WarehouseLinkman;
import com.lcdt.warehouse.entity.WarehouseLoc;
import com.lcdt.warehouse.mapper.WarehousseLinkmanMapper;
import com.lcdt.warehouse.mapper.WarehousseLocMapper;
import com.lcdt.warehouse.mapper.WarehousseMapper;
import com.lcdt.warehouse.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tl.commons.util.StringUtility;

import java.util.Date;
import java.util.HashMap;
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
    @Reference
    GroupWareHouseRpcService groupWareHouseRpcService;

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
        List<WarehouseDto> list = warehousseMapper.selectByCondition(m);
        if(list != null && list.size() > 0){
            Map map = new HashMap();
            for(WarehouseDto dto : list){
                if(StringUtility.isNotEmpty(dto.getGroupIds())){
                    map.put("groupIds", dto.getGroupIds());
                    map.put("wareHouseId", dto.getWhId());
                    String groupNames = groupWareHouseRpcService.selectGroupNamesByGroupIds(map);
                    dto.setGroupNames(groupNames);
                }
            }
        }
        PageInfo pageInfo = new PageInfo(list);

        return pageInfo;
    }

    @Override
    public boolean addWarehouse(Warehouse warehouse) {
        try{
            int count = warehousseMapper.selectWarehouse(warehouse);
            if (count != 0) {   //判断仓库名称是否重复
                return false;
            } else {
                warehousseMapper.insert(warehouse);
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
                addWarehouseLinkMan(linkman);
                if(StringUtility.isNotEmpty(warehouse.getGroupIds())) {
                    //新增仓库项目组关系（多条）
                    groupWareHouseRpcService.addWareHouseRelationBatch(warehouse.getGroupIds(), SecurityInfoGetter.getUser().getUserId(), SecurityInfoGetter.geUserCompRel().getCompId(), warehouse.getWhId());
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifyWarehouse(Warehouse warehouse) {
        try{
            warehousseMapper.updateByPrimaryKey(warehouse);
            //删除仓库项目组关系（多条）
            groupWareHouseRpcService.deleteWareHouseRelationBatch(warehouse.getWhId());
            if(StringUtility.isNotEmpty(warehouse.getGroupIds())) {
                //新增仓库项目组关系（多条）
                groupWareHouseRpcService.addWareHouseRelationBatch(warehouse.getGroupIds(), SecurityInfoGetter.getUser().getUserId(), SecurityInfoGetter.geUserCompRel().getCompId(), warehouse.getWhId());
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifyWarehouseIsDelete(Long whId) {
        try{
            //删除仓库项目组关系（多条）
            groupWareHouseRpcService.deleteWareHouseRelationBatch(whId);
            warehousseMapper.updateIsDeleteByPrimaryKey(whId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
        WarehouseLoc oldLoc = warehousseLocMapper.selectByPrimaryKey(loc.getWhLocId());
        loc.setStatus(oldLoc.getStatus());
        loc.setCreateId(oldLoc.getCreateId());
        loc.setCreateName(oldLoc.getCreateName());
        loc.setCreateDate(oldLoc.getCreateDate());
        loc.setCompanyId(oldLoc.getCompanyId());
        loc.setIsDeleted(oldLoc.getIsDeleted());
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

    @Override
    public int getWarehouseLocByCode(String code) {
        int result = warehousseLocMapper.selectByCode(code);
        return result;
    }
}
