package com.lcdt.warehouse.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.GroupWareHouseRpcService;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.OutWhPlanDto;
import com.lcdt.warehouse.dto.WarehouseDto;
import com.lcdt.warehouse.entity.*;
import com.lcdt.warehouse.mapper.InWarehousePlanMapper;
import com.lcdt.warehouse.mapper.OutWarehousePlanMapper;
import com.lcdt.warehouse.mapper.WarehousseLinkmanMapper;
import com.lcdt.warehouse.mapper.WarehousseLocMapper;
import com.lcdt.warehouse.mapper.WarehousseMapper;
import com.lcdt.warehouse.rpc.WarehouseRpcService;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.lcdt.warehouse.service.OutWarehousePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.tl.commons.util.StringUtility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liz on 2018/5/9.
 */
@Service
public class WarehouseRpcServiceImpl implements WarehouseRpcService{
    @Autowired
    WarehousseMapper warehousseMapper;
    @Autowired
    WarehousseLinkmanMapper warehousseLinkmanMapper;
    @Autowired
    WarehousseLocMapper warehousseLocMapper;
    @Reference
    GroupWareHouseRpcService groupWareHouseRpcService;
   
    @Autowired
    private InWarehousePlanMapper inWarehousePlanMapper;
    
    @Autowired
    private InWarehousePlanService inWarehousePlanService;
   
    @Autowired
    private OutWarehousePlanService outWarehousePlanService;
    
    @Autowired
    private OutWarehousePlanMapper OutWarehousePlanMapper;

    @Override
    public Warehouse selectByPrimaryKey(Long whId) {
        Warehouse warehouse = warehousseMapper.selectByPrimaryKey(whId);
        return warehouse;
    }

    @Override
    public Warehouse addWarehouse(Warehouse warehouse) {
        warehousseMapper.insert(warehouse);
        return warehouse;
    }

    @Override
    public WarehouseLinkman addWarehouseLinkMan(WarehouseLinkman linkman) {
        warehousseLinkmanMapper.insert(linkman);
        return linkman;
    }

    @Override
    public WarehouseLoc addWarehouseLoc(WarehouseLoc loc) {
        warehousseLocMapper.insert(loc);
        return loc;
    }

    @Override
    public Warehouse modifyWarehouse(Warehouse warehouse){
        warehousseMapper.updateByPrimaryKey(warehouse);
        return warehouse;
    }

    @Override
    public List<Warehouse> selectNotInWhIds(Map map){
        return warehousseMapper.selectNotInWhIds(map);
    }

    @Override
    public PageInfo warehouseList(Map m){
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

    @Transactional
    @Override
    public String outWhPlanAdd(OutWhPlanDto outWhPlanDto) {
        UserCompRel userCompRel = new UserCompRel();
        userCompRel.setCompId(outWhPlanDto.getCompanyId());
        User user = new User();
        user.setUserId(outWhPlanDto.getUserId());
        user.setRealName(outWhPlanDto.getUserName());
        userCompRel.setUser(user);
        outWhPlanDto.setStorageType("01"); //原料出库
        OutWarehousePlan outWarehousePlan = outWarehousePlanService.outWhPlanAdd(outWhPlanDto,userCompRel);
        if(outWarehousePlan!=null) return outWarehousePlan.getPlanNo();
        return "";
    }

    @Transactional
    @Override
    public String inWhPlanAdd(InWhPlanDto inWhPlanAddParamsDto) {
        UserCompRel userCompRel = new UserCompRel();
        userCompRel.setCompId(inWhPlanAddParamsDto.getCompanyId());
        User user = new User();
        user.setUserId(inWhPlanAddParamsDto.getCreateUserId());
        user.setRealName(inWhPlanAddParamsDto.getCreateUserName());
        userCompRel.setUser(user);
        inWhPlanAddParamsDto.setStorageType("01"); //"原料入库"
        InWarehousePlan inWarehousePlan = inWarehousePlanService.inWhPlanAdd(inWhPlanAddParamsDto,userCompRel);
        if(inWarehousePlan!=null) return inWarehousePlan.getPlanNo();
        return "";
    }

	@Override
	public InWarehousePlan getInWarehousePlanBySerialNo(String serialNo) {
		return inWarehousePlanMapper.getInWarehousePlanBySerialCode(serialNo);
	}

	@Override
	public OutWarehousePlan getOutWarehousePlanBySerialNo(String serialNo) {
		return OutWarehousePlanMapper.getOutWarehousePlanBySerialCode(serialNo);
	}

	@Override
	public List<Warehouse> queryWarehouseByGroupIdAndCmpId(Warehouse warehouse){
        return warehousseMapper.selectByGroupIdAndCmpId(warehouse);
    }
}
