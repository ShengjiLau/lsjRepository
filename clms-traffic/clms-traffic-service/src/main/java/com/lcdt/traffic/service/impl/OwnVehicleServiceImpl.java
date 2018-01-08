package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
        OwnVehicle ownVehicle = new OwnVehicle();
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle); //复制对象属性
        int count = ownVehicleMapper.selectVehicleNum(ownVehicle);
        if (count != 0) {   //判断车牌号是否重复
            throw new RuntimeException("车牌号不能重复!");
        } else {
            ownVehicleMapper.insert(ownVehicle);    //保存车辆基本信息
            if(null!=ownVehicleDto.getOwnVehicleCertificateList() && ownVehicleDto.getOwnVehicleCertificateList().size()>0) {
                //设置证件的创建人/id创建人/公司id
                for (OwnVehicleCertificate ownVehicleCertificate : ownVehicleDto.getOwnVehicleCertificateList()) {
                    ownVehicleCertificate.setOwnVehicleId(ownVehicle.getOwnVehicleId());
                    ownVehicleCertificate.setCreateId(ownVehicleDto.getCreateId());
                    ownVehicleCertificate.setCreateName(ownVehicleDto.getCreateName());
                    ownVehicleCertificate.setCompanyId(ownVehicleDto.getCompanyId());
                }
                ownVehicleCertificateMapper.insertBatch(ownVehicleDto.getOwnVehicleCertificateList());  //批量插入车辆证件
            }
            /**下面判断用户表中是否存在该随车电话的账号，不存在的话，则自动保存一条司机的账号信息*/
            if(null != ownVehicleDto.getVehicleDriverPhone()) {
                String phone = ownVehicleDto.getVehicleDriverPhone().trim();
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
                    } catch (PhoneHasRegisterException e) {
                        e.printStackTrace();
                        throw new RuntimeException("保存司机账号信息失败！");
                    }
                }
            }
        }
        return 1;
    }

    @Override
    public int modVehicle(OwnVehicleDto ownVehicleDto) {
        OwnVehicle ownVehicle = new OwnVehicle();
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle); //复制对象属性
        int count = ownVehicleMapper.selectVehicleNum(ownVehicle);
        if (count != 0) {   //判断车牌号是否重复
            throw new RuntimeException("车牌号不能重复!");
        } else {
            ownVehicleMapper.updateByPrimaryKey(ownVehicle);    //更新车辆基本信息
            //获取该车下面的证件ovcId用来匹配被删除的证件
            List<Long> ovcIdList = ownVehicleCertificateMapper.selectOvcIdByOwnVehicleId(ownVehicle.getOwnVehicleId());
            List<OwnVehicleCertificate> list1 = new ArrayList<>();
            List<OwnVehicleCertificate> list2 = new ArrayList<>();
            if (null != ownVehicleDto.getOwnVehicleCertificateList()) {
                for (OwnVehicleCertificate item : ownVehicleDto.getOwnVehicleCertificateList()) {  //迭代根据ownVehicleId来区分是新增还是插入
                    item.setOwnVehicleId(ownVehicle.getOwnVehicleId());     //设置ownVehicleId
                    if (item.getOvcId() == null) {   //没有主键的则为新增
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
                                if(ovcId==item.getOvcId()){
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                if (list1.size() > 0) {
                    ownVehicleCertificateMapper.insertBatch(list1);  //批量插入车辆证件
                }
                if (list2.size() > 0) {
                    ownVehicleCertificateMapper.updateBatch(list2); //批量更新车辆证件
                }
                if(ovcIdList.size()>0){
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("ovcIds",ovcIdList);
                    params.put("updateId",ownVehicleDto.getUpdateId());
                    params.put("updateName",ownVehicleDto.getUpdateName());
                    ownVehicleCertificateMapper.deleteByBatch(params);
                }
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
                        driver.setCreateId(ownVehicleDto.getUpdateId());
                        driver.setCreateName(ownVehicleDto.getUpdateName());
                        driverService.addDriver(driver);    //保存司机信息
                    } catch (PhoneHasRegisterException e) {
                        e.printStackTrace();
                        throw new RuntimeException("保存司机账号信息失败！");
                    }
                }
            }
        }
        return 1;
    }

    @Override
    public int delVehicle(OwnVehicleDto ownVehicleDto) {
        OwnVehicle ownVehicle = new OwnVehicle();
        BeanUtils.copyProperties(ownVehicleDto, ownVehicle); //复制对象属性
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
            throw new RuntimeException("删除失败!");
//            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public PageInfo<List<OwnVehicle>> ownVehicleList(OwnVehicle ownVehicle, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(ownVehicleMapper.selectByCondition(ownVehicle));
        return page;
    }

    @Override
    public OwnVehicleDto ownVehicleDetail(Long ownVehicleId, Long companyId) {
        return ownVehicleMapper.selectDetail(ownVehicleId, companyId);
    }

    @Override
    public List<Driver> getGpsInfo(List<String> driverPhoneList) {
        return driverService.getGpsInfo(driverPhoneList);
    }
}
