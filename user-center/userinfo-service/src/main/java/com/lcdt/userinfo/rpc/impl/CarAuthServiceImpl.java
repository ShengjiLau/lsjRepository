package com.lcdt.userinfo.rpc.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.lcdt.userinfo.dao.DriverVehicleAuthMapper;
import com.lcdt.userinfo.model.DriverVehicleAuth;
import com.lcdt.userinfo.rpc.CarAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarAuthServiceImpl implements CarAuthService{

    @Autowired
    DriverVehicleAuthMapper dao;

    @Transactional
    @Override
    public DriverVehicleAuth addVehicleAuth(DriverVehicleAuth auth) {
        auth.setAuthStatus("0");
        if(isVehicleNumExist(auth)!=-1){
            throw new RuntimeException("车牌号已存在!");
        }
        if(auth.getIsDefault()){
            //新增的如果是默认，已经存在的记录需要全部设置为非默认
            dao.updateDefault(auth.getDriverId());
        }
        dao.insert(auth);
        return auth;
    }

    @Override
    public List<DriverVehicleAuth> vehicleList(Long driverId, Integer pageNo, Integer pageSize) {
        if (pageNo != null) {
            if (pageSize == null) {
                pageSize = 10;
            }
            PageHelper.startPage(pageNo, pageSize);
        }
        List<DriverVehicleAuth> driverVehicleAuths = dao.selectByDriverId(driverId);
        return driverVehicleAuths;
    }

    @Override
    public DriverVehicleAuth selectById(Long authId) {
        DriverVehicleAuth driverVehicleAuth = dao.selectByPrimaryKey(authId);
//        List<DriverVehicleAuth> driverVehicleAuths = dao.selectByDriverId(authId);

        return driverVehicleAuth;
    }

    @Override
    public DriverVehicleAuth updateVehicleAuth(DriverVehicleAuth auth) {
        if(isVehicleNumExist(auth)==1){
            throw new RuntimeException("车牌号已存在");
        }
        if(auth.getIsDefault()){
            //新增的如果是默认，已经存在的记录需要全部设置为非默认
            dao.updateDefault(auth.getDriverId());
        }
        dao.updateByPrimaryKey(auth);
        return auth;
    }

    /**
     * 根据 driverId 和 vehicleNum 判断这个 司机id 下车辆是否已存在，（-1；已存在、0：和原来相同没改变、1：单位名称被使用)
     * @param auth
     * @return
     */
    private int isVehicleNumExist(DriverVehicleAuth auth){
        List<DriverVehicleAuth> list=dao.selectVehicleNumExist(auth);
        if(list!=null&&list.size()>0){
            //判断车牌号是否是自己
            if((auth.getVehicleNum()!=null&&auth.getVehicleNum().equals(list.get(0).getVehicleNum()))&&(auth.getAuthId()!=null&&auth.getAuthId().equals(list.get(0).getAuthId()))){
                return 0;
            }else {
                return 1;
            }
        }else {
            return -1;
        }
    }

}
