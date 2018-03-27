package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.DriverGroupMapper;
import com.lcdt.traffic.dao.DriverGroupRelationshipMapper;
import com.lcdt.traffic.dto.DriverGroupDto;
import com.lcdt.traffic.dto.DriverGroupDto2;
import com.lcdt.traffic.model.DriverAndGroup;
import com.lcdt.traffic.model.DriverGroup;
import com.lcdt.traffic.service.DriverGroupService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-25
 */
@Service
public class DriverGroupServiceImpl implements DriverGroupService {

    @Autowired
    private DriverGroupMapper driverGroupMapper;

    @Autowired
    private DriverGroupRelationshipMapper driverGroupRelationshipMapper;

    @Override
    public int addDriverGroup(DriverGroup driverGroup) {
        int count = driverGroupMapper.selectByGroupName(driverGroup.getCompanyId(),driverGroup.getGroupName(),null);
        if(count!=0){
            throw new RuntimeException("司机分组不能重复!");
        }
        return driverGroupMapper.insert(driverGroup);
    }

    @Override
    public int modDriverGroup(DriverGroup driverGroup) {
        int count = driverGroupMapper.selectByGroupName(driverGroup.getCompanyId(),driverGroup.getGroupName(),driverGroup.getDriverGroupId());
        if(count!=0){
            throw new RuntimeException("司机分组不能重复!");
        }
        return driverGroupMapper.updateByPrimaryKey(driverGroup);
    }

    @Override
    public int delDriverGroup(DriverGroup driverGroup) {
        int count = driverGroupRelationshipMapper.selectByDriverGroupId(driverGroup.getDriverGroupId(),driverGroup.getCompanyId());
        if(count>0){
            throw new RuntimeException("有司机属于此分组，不能删除！");
        }
        return driverGroupMapper.deleteByUpdate(driverGroup);
    }

    @Override
    public List<DriverGroup> selectAll(Long companyId) {
        return driverGroupMapper.selectAll(companyId);
    }

    @Override
    public List<DriverGroupDto> selectRelationship(Long companyId) {
        return driverGroupMapper.selectRelationship(companyId);
    }
    @Override
    public PageInfo<List<DriverAndGroup>> selectDriverAndGroup(Long companyId, PageInfo pageInfo){
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(driverGroupMapper.selectDriverAndGroup(companyId));
        return page;
    }

    @Override
    public List<DriverGroupDto2> driverListByGroupId2(Long companyId, String [] groupIds){
        Map<String,Object> map = new HashMap<>();
        map.put("companyId",companyId);
        map.put("groupIds",groupIds);
        return driverGroupMapper.selectDriverByGroupIds2(map);
    }
}
