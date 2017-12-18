package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.traffic.dao.OwnVehicleCertificateMapper;
import com.lcdt.traffic.dao.OwnVehicleMapper;
import com.lcdt.traffic.web.dto.OwnVehicleDto;
import com.lcdt.traffic.model.OwnVehicle;
import com.lcdt.traffic.model.OwnVehicleCertificate;
import com.lcdt.traffic.service.OwnVehicleService;
import com.lcdt.traffic.util.RegisterUtils;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.DriverService;
import com.lcdt.userinfo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
@Transactional
@Service
public class OwnVehicleServiceImpl implements OwnVehicleService {

    Logger logger = LoggerFactory.getLogger(OwnVehicleServiceImpl.class);
    @Autowired
    private OwnVehicleMapper ownVehicleMapper;
    @Autowired
    private OwnVehicleCertificateMapper ownVehicleCertificateMapper;

    @Reference
    public UserService userService;

    @Reference
    public DriverService driverService;

    @Override
    public int addVehicle(OwnVehicleDto ownVehicleDto) {
        int count = ownVehicleMapper.selectVehicleNum(ownVehicleDto.getVehicleNum());
        if (count != 0) {   //判断车牌号是否重复
            throw new RuntimeException("车牌号不能重复!");
        }else{
            OwnVehicle ownVehicle = new OwnVehicle();
            BeanUtils.copyProperties(ownVehicleDto, ownVehicle); //复制对象属性
            ownVehicleMapper.insert(ownVehicle);    //保存车辆基本信息
            //设置证件的更新人更新时间公司id
            for (OwnVehicleCertificate ownVehicleCertificate : ownVehicleDto.getOwnVehicleCertificateList()) {
                ownVehicleCertificate.setCreateId(ownVehicleDto.getCreateId());
                ownVehicleCertificate.setCreateName(ownVehicleDto.getCreateName());
                ownVehicleCertificate.setCompnayId(ownVehicleDto.getCompnayId());
            }
            ownVehicleCertificateMapper.insertBatch(ownVehicleDto.getOwnVehicleCertificateList());  //批量插入车辆证件

            /**下面判断用户表中是否存在该随车电话的账号，不存在的话，则自动保存一条司机的账号信息*/
            String phone = ownVehicleDto.getVehicleDriverPhone().trim();
            if (!userService.isPhoneBeenRegister(phone)) { //为空则保存司机账号信息
                RegisterDto registerDto = new RegisterDto();
                registerDto.setUserPhoneNum(ownVehicleDto.getVehicleDriverPhone());
                registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                logger.info("司机账号默认密码：" + phone.substring(5));
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
                } catch (PhoneHasRegisterException e) {
                    e.printStackTrace();
                    throw new RuntimeException("保存司机账号信息失败！");
                }
            }
        }
        return 1;
    }

    @Override
    public int modVehicle(OwnVehicleDto ownVehicleDto) {
        int count = ownVehicleMapper.selectVehicleNum(ownVehicleDto.getVehicleNum());
        if (count == 0) {   //判断车牌号是否重复
            OwnVehicle ownVehicle = new OwnVehicle();
            BeanUtils.copyProperties(ownVehicleDto, ownVehicle); //复制对象属性
            ownVehicleMapper.insert(ownVehicle);    //保存车辆基本信息
            List<OwnVehicleCertificate> list1 = new ArrayList<>();
            List<OwnVehicleCertificate> list2 = new ArrayList<>();
            for (OwnVehicleCertificate item : ownVehicleDto.getOwnVehicleCertificateList()) {  //迭代根据ownVehicleId来区分是新增还是插入
                if (item.getOwnVehicleId() == null) {   //没有主键的则为新增
                    list1.add(item);
                } else {
                    list2.add(item);
                }
            }
            ownVehicleCertificateMapper.insertBatch(list1);  //批量插入车辆证件
            ownVehicleCertificateMapper.updateBatch(list2); //批量更新车辆证件
            /**下面判断用户表中是否存在该随车电话的账号，不存在的话，则自动保存一条司机的账号信息*/
            String phone = ownVehicleDto.getVehicleDriverPhone().trim();
            if (!userService.isPhoneBeenRegister(phone)) { //为空则保存司机账号信息
                RegisterDto registerDto = new RegisterDto();
                registerDto.setUserPhoneNum(ownVehicleDto.getVehicleDriverPhone());
                registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                registerDto.setIntroducer("");
                registerDto.setEmail("");
                try {
                    User user = userService.registerUser(registerDto);  //保存账号信息
                    Driver driver = new Driver();
                    driver.setUserId(user.getUserId());
                    driver.setAffiliatedCompany(ownVehicleDto.getAffiliatedCompany());
                    driver.setDriverName(ownVehicleDto.getVehicleDriverName());
                    driver.setDriverPhone(phone);
                    driverService.addDriver(driver);    //保存司机信息
                } catch (PhoneHasRegisterException e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;
    }

    @Override
    public int delVehicle(Long ownVehicleId) {
        return 0;
    }

    @Override
    public List<OwnVehicleDto> ownVehicleDtoList() {
        return null;
    }
}
