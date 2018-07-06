package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.RegisterUtils;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.DriverService;
import com.lcdt.userinfo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2018/1/12.
 */
@Service
public class TrafficRpcImpl implements TrafficRpc {

    @Autowired
    private WaybillService waybillService;

    @Autowired
    private OwnVehicleMapper ownVehicleMapper;

    @Autowired
    private OwnDriverMapper ownDriverMapper;

    @Reference
    public UserService userService;

    @Reference
    public DriverService driverService;

    @Autowired
    private WaybillPlanMapper waybillPlanMapper; //计划

    @Autowired
    private PlanDetailMapper planDetailMapper; //计划详细

    @Override
    public void waybillPositionTimer(Map map) {
        waybillService.queryWaybillListToPoPosition(map);
    }


    @Transactional
    @Override
    public OwnVehicle addVehicle(OwnVehicle ownVehicle) {
        //  获取companyId
        Long companyId = SecurityInfoGetter.getCompanyId();
        ownVehicle.setCompanyId(companyId);
        int count = ownVehicleMapper.selectVehicleNum(ownVehicle);
        //判断车牌号是否重复
        if (count != 0) {
            return ownVehicleMapper.selectByVehicleNum(ownVehicle);
        } else {
            /**保存车辆基本信息*/
            Long userId = SecurityInfoGetter.getUser().getUserId();
            String userName = SecurityInfoGetter.getUser().getRealName();
            ownVehicle.setCreateId(userId);
            ownVehicle.setCreateName(userName);
            ownVehicleMapper.insert(ownVehicle);
            return ownVehicle;
        }
    }


    @Transactional
    @Override
    public OwnDriver addDriver(OwnDriver ownDriver) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        ownDriver.setCompanyId(companyId);
        int count = ownDriverMapper.selectDriverPhone(ownDriver);
        //判断手机号是否重复
        if (count != 0) {
            return ownDriverMapper.selectByAddWaybillDriverPhone(ownDriver);
        } else {
            //保存车司机本信息
            Long userId = SecurityInfoGetter.getUser().getUserId();
            String userName = SecurityInfoGetter.getUser().getRealName();
            ownDriver.setCreateId(userId);
            ownDriver.setCreateName(userName);
            if(null != ownDriver.getDriverPhone()) {
                String phone = ownDriver.getDriverPhone().trim();
                /**判断是否已经开通cLMS司机账号，若没有开通，则自动开通,新增一条司机账号信息*/
                if(!driverService.isExistDriver(phone)){
                    //为空则保存clms司机账号信息
                    if (!userService.isPhoneBeenRegister(phone)) {
                        RegisterDto registerDto = new RegisterDto();
                        registerDto.setUserPhoneNum(phone);
                        registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                        registerDto.setIntroducer("");
                        registerDto.setEmail("");
                        try {
                            //保存账号信息
                            User user = userService.registerUser(registerDto);
                            Driver driver = new Driver();
                            driver.setUserId(user.getUserId());
                            driver.setAffiliatedCompany(ownDriver.getAffiliatedCompany());
                            driver.setDriverName(ownDriver.getDriverName());
                            driver.setDriverPhone(phone);
                            driver.setCreateId(ownDriver.getCreateId());
                            driver.setCreateName(ownDriver.getCreateName());
                            //保存司机信息
                            driverService.addDriver(driver);
                            /*将司机账号的user_id更新到我的司机表里*/
                            ownDriver.setDriverId(user.getUserId());
                            //保存我的司机信息
                            ownDriverMapper.insert(ownDriver);
                            return ownDriver;
                        } catch (PhoneHasRegisterException e) {
                            e.printStackTrace();
                            throw new RuntimeException("保存司机账号信息失败！");
                        }
                    }else{
                        User user = userService.selectUserByPhone(phone);
                        Driver driver = new Driver();
                        driver.setUserId(user.getUserId());
                        driver.setAffiliatedCompany(ownDriver.getAffiliatedCompany());
                        driver.setDriverName(ownDriver.getDriverName());
                        driver.setDriverPhone(phone);
                        driver.setCreateId(ownDriver.getCreateId());
                        driver.setCreateName(ownDriver.getCreateName());
                        //保存司机信息
                        driverService.addDriver(driver);
                        /*将司机账号的user_id更新到我的司机表里*/
                        ownDriver.setDriverId(user.getUserId());
                        //保存我的司机信息
                        ownDriverMapper.insert(ownDriver);
                        return ownDriver;
                    }
                }
                User user = userService.selectUserByPhone(phone);
                /*将司机账号的user_id更新到我的司机表里*/
                ownDriver.setDriverId(user.getUserId());
                //保存我的司机信息
                ownDriverMapper.insert(ownDriver);
                return ownDriver;
            }
            return ownDriver;
        }
    }




    @Transactional
    @Override
    public WaybillPlan purchase4Plan(WaybillParamsDto waybillParamsDto,int flag) {
        WaybillPlan vo = new WaybillPlan(); //复制传来对象值
        BeanUtils.copyProperties(waybillParamsDto, vo);
        vo.setSendOrderType(ConstantVO.PLAN_SEND_ORDER_TPYE_ZHIPAI);
        vo.setCarrierType(ConstantVO.PLAN_CARRIER_TYPE_ELSE); //发布后派单
        vo.setPlanStatus(ConstantVO.PLAN_STATUS_SEND_ORDERS); //派单中
        Date dt = new Date();
        vo.setStartDate(dt); //当前系统是时间
        vo.setIsDeleted((short) 0);
        vo.setCreateDate(dt);
        vo.setTransportWay((short) 1);
        vo.setDistributionWay(4+"");
        waybillPlanMapper.insert(vo); //生成计划
        WaybillPlan vo2 = waybillPlanMapper.selectByWaybillPlanId(vo.getWaybillPlanId());
        List<PlanDetail> planDetailList = waybillParamsDto.getPlanDetailList();
        if (null!=planDetailList && planDetailList.size()>0) {
            for (PlanDetail obj : planDetailList) {
                obj.setWaybillPlanId(vo.getWaybillPlanId());
                obj.setRemainderAmount(obj.getPlanAmount());//初期【计划=剩余】
                obj.setCreateId(vo.getCreateId());
                obj.setCreateName(vo.getCreateName());
                obj.setCreateDate(new Date());
                obj.setUpdateId(vo.getUpdateId());
                obj.setUpdateName(vo.getUpdateName());
                obj.setUpdateTime(obj.getCreateDate());
                obj.setCompanyId(vo.getCompanyId());
                obj.setIsDeleted((short)0);
            }
            planDetailMapper.batchAddPlanDetail(planDetailList);//批量保存计划详细
        }
        return vo2;
    }


	@Override
	public WaybillPlan getWaybillPlanBySerialNo(String serialNo) {
		return waybillPlanMapper.getWaybillPlanBySerialCode(serialNo);
	}



}
