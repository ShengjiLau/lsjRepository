package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.OwnDriverMapper;
import com.lcdt.traffic.dao.OwnVehicleCertificateMapper;
import com.lcdt.traffic.dao.OwnVehicleMapper;
import com.lcdt.traffic.dto.BackstageVehicleDto;
import com.lcdt.traffic.dto.OwnVehicleDto;
import com.lcdt.traffic.exception.CommonRunException;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.model.OwnVehicleCertificate;
import com.lcdt.traffic.service.OwnDriverService;
import com.lcdt.traffic.service.OwnVehicleService;
import com.lcdt.traffic.util.RegisterUtils;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.DriverService;
import com.lcdt.userinfo.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class OwnVehicleServiceImpl implements OwnVehicleService {

    Logger logger = LoggerFactory.getLogger(OwnVehicleServiceImpl.class);
    @Autowired
    private OwnVehicleMapper ownVehicleMapper;

    @Autowired
    private OwnDriverService ownDriverService;

    @Autowired
    private OwnDriverMapper ownDriverMapper;

    @Autowired
    private OwnVehicleCertificateMapper ownVehicleCertificateMapper;

    @Reference
    public UserService userService;

    @Reference
    public DriverService driverService;

    @Override
    public int addVehicle(OwnVehicleDto ownVehicleDto) {
        OwnVehicle ownVehicle = new OwnVehicle();
        //复制对象属性
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle);
        int count = ownVehicleMapper.selectVehicleNum(ownVehicle);
        //判断车牌号是否重复
        if (count != 0) {
            vehicleException("车牌号不能重复!");
        } else {
            //保存车辆基本信息
            ownVehicleMapper.insert(ownVehicle);
            if(CollectionUtils.isNotEmpty(ownVehicleDto.getOwnVehicleCertificateList())) {
                //设置证件的创建人/id创建人/公司id
                for (OwnVehicleCertificate ownVehicleCertificate : ownVehicleDto.getOwnVehicleCertificateList()) {
                    ownVehicleCertificate.setOwnVehicleId(ownVehicle.getOwnVehicleId());
                    ownVehicleCertificate.setCreateId(ownVehicleDto.getCreateId());
                    ownVehicleCertificate.setCreateName(ownVehicleDto.getCreateName());
                    ownVehicleCertificate.setCompanyId(ownVehicleDto.getCompanyId());
                }
                //批量插入车辆证件
                ownVehicleCertificateMapper.insertBatch(ownVehicleDto.getOwnVehicleCertificateList());
            }
            if(null != ownVehicleDto.getVehicleDriverPhone()) {
                String phone = ownVehicleDto.getVehicleDriverPhone().trim();
                User user = userService.selectUserByPhone(phone);
                /**将司机账号的user_id更新到我的车辆表里*/
                ownVehicle.setVehicleDriverId(user.getUserId());
                ownVehicleMapper.updateDriverId(ownVehicle);
                //**判断我的司机是否存在该手机号，没有自动创建一条*/
                /*
                if(!ownDriverService.isExistDriver(phone,ownVehicleDto.getCompanyId())){    //我的司机不存在记录
                    OwnDriver ownDriver = new OwnDriver();
                    ownDriver.setCompanyId(ownVehicleDto.getCompanyId());
                    ownDriver.setDriverPhone(phone);
                    ownDriver.setAffiliatedCompany(ownVehicle.getAffiliatedCompany());
                    ownDriver.setDriverName(ownVehicle.getVehicleDriverName());
                    ownDriver.setDriverCategory(ownVehicle.getVehicleCategory());
                    ownDriver.setCreateId(ownVehicle.getCreateId());
                    ownDriver.setCreateName(ownVehicle.getCreateName());
                    ownDriverMapper.insert(ownDriver);    //保存车司机本信息
                }
                //**判断是否已经开通cLMS司机账号，若没有开通，则自动开通,新增一条司机账号信息*/
                /*
                if(!driverService.isExistDriver(phone)){
                    if (!userService.isPhoneBeenRegister(phone)) { //为空则保存司机账号信息
                        RegisterDto registerDto = new RegisterDto();
                        registerDto.setUserPhoneNum(phone);
                        registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                        logger.debug("司机账号默认密码：" + phone.substring(5));
                        registerDto.setIntroducer("");
                        registerDto.setEmail("");
                        try {
                            User user = userService.registerUser(registerDto);  //保存账号信息
                            Driver driver = new Driver();
                            driver.setUserId(user.getUserId());
                            driver.setAffiliatedCompany(ownVehicleDto.getAffiliatedCompany());
                            driver.setDriverName(ownVehicleDto.getVehicleDriverName());
                            driver.setDriverPhone(phone);
                            driver.setCreateId(ownVehicleDto.getCreateId());
                            driver.setCreateName(ownVehicleDto.getCreateName());
                            driverService.addDriver(driver);    //保存司机信息
                            *//*将司机账号的user_id更新到我的车辆表里*//*
                            ownVehicle.setVehicleDriverId(user.getUserId());
                            ownVehicleMapper.updateDriverId(ownVehicle);
                        } catch (PhoneHasRegisterException e) {
                            e.printStackTrace();
                            throw new RuntimeException("保存司机账号信息失败！");
                        }
                    }else{
                        User user = userService.selectUserByPhone(phone);
                        Driver driver = new Driver();
                        driver.setUserId(user.getUserId());
                        driver.setAffiliatedCompany(ownVehicleDto.getAffiliatedCompany());
                        driver.setDriverName(ownVehicleDto.getVehicleDriverName());
                        driver.setDriverPhone(phone);
                        driver.setCreateId(ownVehicleDto.getCreateId());
                        driver.setCreateName(ownVehicleDto.getCreateName());
                        driverService.addDriver(driver);    //保存司机信息
                    }
                }*/

            }
        }
        return 1;
    }

    @Override
    public int modVehicle(OwnVehicleDto ownVehicleDto) {
        OwnVehicle ownVehicle = new OwnVehicle();
        //复制对象属性
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle);
        int count = ownVehicleMapper.selectVehicleNum(ownVehicle);
        //判断车牌号是否重复
        if (count != 0) {
            vehicleException("车牌号不能重复!");
        } else {
            //更新车辆基本信息
            ownVehicleMapper.updateByPrimaryKey(ownVehicle);
            //获取该车下面的证件ovcId用来匹配被删除的证件
            List<Long> ovcIdList = ownVehicleCertificateMapper.selectOvcIdByOwnVehicleId(ownVehicle.getOwnVehicleId());
            List<OwnVehicleCertificate> list1 = new ArrayList<>();
            List<OwnVehicleCertificate> list2 = new ArrayList<>();
            if (null != ownVehicleDto.getOwnVehicleCertificateList()) {
                //迭代根据ownVehicleId来区分是新增还是插入
                for (OwnVehicleCertificate item : ownVehicleDto.getOwnVehicleCertificateList()) {
                    //设置ownVehicleId
                    item.setOwnVehicleId(ownVehicle.getOwnVehicleId());
                    //没有主键的则为新增
                    if (item.getOvcId() == null) {
                        item.setCreateId(ownVehicleDto.getUpdateId());
                        item.setCreateName(ownVehicleDto.getUpdateName());
                        item.setCompanyId(ownVehicleDto.getCompanyId());
                        list1.add(item);
                    } else {

                        item.setUpdateId(ownVehicleDto.getUpdateId());
                        item.setUpdateName(ownVehicleDto.getUpdateName());
                        item.setCompanyId(ownVehicleDto.getCompanyId());
                        list2.add(item);
                        //
                        if(null!=ovcIdList) {
                            Iterator<Long> it = ovcIdList.iterator();
                            while(it.hasNext()){
                                Long ovcId = it.next();
                                if(ovcId.equals(item.getOvcId())){
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                if (CollectionUtils.isNotEmpty(list1)) {
                    //批量插入车辆证件
                    ownVehicleCertificateMapper.insertBatch(list1);
                }
                if (CollectionUtils.isNotEmpty(list2)) {
                    //批量更新车辆证件
                    ownVehicleCertificateMapper.updateBatch(list2);
                }
                if(CollectionUtils.isNotEmpty(ovcIdList)){
                    Map<String, Object> params = new HashMap<>(5);
                    params.put("ovcIds",ovcIdList);
                    params.put("updateId",ownVehicleDto.getUpdateId());
                    params.put("updateName",ownVehicleDto.getUpdateName());
                    ownVehicleCertificateMapper.deleteByBatch(params);
                }
                if(null != ownVehicleDto.getVehicleDriverPhone()) {
                    String phone = ownVehicleDto.getVehicleDriverPhone().trim();
                    /**判断我的司机是否存在该手机号，没有自动创建一条*/
                    //我的司机不存在记录
                    if(!ownDriverService.isExistDriver(phone,ownVehicleDto.getCompanyId())){
                        OwnDriver ownDriver = new OwnDriver();
                        ownDriver.setCompanyId(ownVehicleDto.getCompanyId());
                        ownDriver.setDriverPhone(phone);
                        ownDriver.setAffiliatedCompany(ownVehicle.getAffiliatedCompany());
                        ownDriver.setDriverName(ownVehicle.getVehicleDriverName());
                        ownDriver.setDriverCategory(ownVehicle.getVehicleCategory());
                        ownDriver.setCreateId(ownVehicle.getCreateId());
                        ownDriver.setCreateName(ownVehicle.getCreateName());
                        //保存车司机本信息
                        ownDriverMapper.insert(ownDriver);
                    }
                    /**判断是否已经开通cLMS司机账号，若没有开通，则自动开通,新增一条司机账号信息*/
                    if(!driverService.isExistDriver(phone)){
                        //为空则保存司机账号信息
                        if (!userService.isPhoneBeenRegister(phone)) {
                            RegisterDto registerDto = new RegisterDto();
                            registerDto.setUserPhoneNum(phone);
                            registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                            logger.info("司机账号默认密码为手机号后六位：{}" , phone);
                            registerDto.setIntroducer("");
                            registerDto.setEmail("");
                            try {
                                //保存账号信息
                                User user = userService.registerUser(registerDto);
                                Driver driver = new Driver();
                                driver.setUserId(user.getUserId());
                                driver.setAffiliatedCompany(ownVehicleDto.getAffiliatedCompany());
                                driver.setDriverName(ownVehicleDto.getVehicleDriverName());
                                driver.setDriverPhone(phone);
                                driver.setCreateId(ownVehicleDto.getCreateId());
                                driver.setCreateName(ownVehicleDto.getCreateName());
                                //保存司机信息
                                driverService.addDriver(driver);
                            } catch (PhoneHasRegisterException e) {
                                logger.error("PhoneHasRegisterException : {}",e);
                                vehicleException("保存司机账号信息失败！");
                            }
                        }else{
                            User user = userService.selectUserByPhone(phone);
                            Driver driver = new Driver();
                            driver.setUserId(user.getUserId());
                            driver.setAffiliatedCompany(ownVehicleDto.getAffiliatedCompany());
                            driver.setDriverName(ownVehicleDto.getVehicleDriverName());
                            driver.setDriverPhone(phone);
                            driver.setCreateId(ownVehicleDto.getCreateId());
                            driver.setCreateName(ownVehicleDto.getCreateName());
                            //保存司机信息
                            driverService.addDriver(driver);
                        }
                    }

                }
            }
        }
        return 1;
    }

    @Override
    public int delVehicle(OwnVehicleDto ownVehicleDto) {
        OwnVehicle ownVehicle = new OwnVehicle();
        //复制对象属性
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle);
        try {
            ownVehicleMapper.deleteByUpdate(ownVehicle);
            OwnVehicleCertificate ownVehicleCertificate = new OwnVehicleCertificate();
            /**设置车辆证件更新信息*/
            ownVehicleCertificate.setOwnVehicleId(ownVehicleDto.getOwnVehicleId());
            ownVehicleCertificate.setUpdateId(ownVehicleDto.getUpdateId());
            ownVehicleCertificate.setUpdateName(ownVehicleDto.getUpdateName());
            ownVehicleCertificate.setCompanyId(ownVehicleDto.getCompanyId());
            ownVehicleCertificateMapper.deleteByUpdate(ownVehicleCertificate);
        } catch (Exception e) {
            vehicleException("删除失败!");
        }
        return 1;
    }

    @Override
    public PageInfo<List<OwnVehicle>> ownVehicleList(OwnVehicle ownVehicle, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return new PageInfo(ownVehicleMapper.selectByCondition(ownVehicle));
    }

    @Override
    public OwnVehicleDto ownVehicleDetail(Long ownVehicleId, Long companyId) {
        return ownVehicleMapper.selectDetail(ownVehicleId, companyId);
    }

    @Override
    public List<Driver> getGpsInfo(List<String> driverPhoneList) {
        return driverService.getGpsInfo(driverPhoneList);
    }

    @Override
    public int syncVehicleInfo(OwnVehicle ownVehicle){
        int result = 0;
        int row = ownVehicleMapper.selectVehicleNum(ownVehicle);
        //如果该车辆不在里面，则创建一条记录
        if(row==0){
            result = ownVehicleMapper.insert(ownVehicle);
        }
        return result;
    }


    @Override
    public PageInfo<List<OwnVehicle>> companyVehicleList(BackstageVehicleDto dto, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        return new PageInfo(ownVehicleMapper.selectForManage(dto));
    }

    private void vehicleException(String msg){
        throw new CommonRunException(msg);
    }

}
