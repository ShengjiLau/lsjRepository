package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.traffic.dao.DriverGroupRelationshipMapper;
import com.lcdt.traffic.dao.OwnDriverCertificateMapper;
import com.lcdt.traffic.dao.OwnDriverMapper;
import com.lcdt.traffic.model.DriverGroupRelationship;
import com.lcdt.traffic.model.OwnDriver;
import com.lcdt.traffic.model.OwnDriverCertificate;
import com.lcdt.traffic.service.OwnDriverService;
import com.lcdt.traffic.util.RegisterUtils;
import com.lcdt.traffic.web.dto.OwnDriverDto;
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

/**
 * @AUTHOR liuh
 * @DATE 2017-12-13
 */
@Transactional
@Service
public class OwnDriverServiceImpl implements OwnDriverService {

    Logger logger = LoggerFactory.getLogger(OwnDriverServiceImpl.class);
    @Autowired
    private OwnDriverMapper ownDriverMapper;
    @Autowired
    private OwnDriverCertificateMapper ownDriverCertificateMapper;
    @Autowired
    private DriverGroupRelationshipMapper driverGroupRelationshipMapper;

    @Reference
    public UserService userService;

    @Reference
    public DriverService driverService;

    @Override
    public int addDriver(OwnDriverDto ownDriverDto) {
        OwnDriver ownDriver = new OwnDriver();
        BeanUtils.copyProperties(ownDriverDto, ownDriver); //复制对象属性
        int count = ownDriverMapper.selectDriverPhone(ownDriver);
        if (count != 0) {   //判断手机号是否重复
            throw new RuntimeException("手机号不能重复!");
        } else {
            ownDriverMapper.insert(ownDriver);    //保存车司机本信息
            //设置证件的创建人/id创建人/公司id
            for (OwnDriverCertificate ownDriverCertificate : ownDriverDto.getOwnDriverCertificateList()) {
                ownDriverCertificate.setOwnDriverId(ownDriver.getOwnDriverId());
                ownDriverCertificate.setCreateId(ownDriverDto.getCreateId());
                ownDriverCertificate.setCreateName(ownDriverDto.getCreateName());
                ownDriverCertificate.setCompanyId(ownDriverDto.getCompanyId());
            }
            ownDriverCertificateMapper.insertBatch(ownDriverDto.getOwnDriverCertificateList());  //批量插入车辆证件

            /**下面判断用户表中是否存在该司机电话的账号，不存在的话，则自动保存一条司机的账号信息*/
            String phone = ownDriverDto.getDriverPhone().trim();
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
                    driver.setAffiliatedCompany(ownDriverDto.getAffiliatedCompany());
                    driver.setDriverName(ownDriverDto.getDriverName());
                    driver.setDriverPhone(phone);
                    driver.setCreateId(ownDriverDto.getCreateId());
                    driver.setCreateName(ownDriverDto.getCreateName());
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
    public int modDriver(OwnDriverDto ownDriverDto) {
        OwnDriver ownDriver = new OwnDriver();
        BeanUtils.copyProperties(ownDriverDto, ownDriver); //复制对象属性
        int count = ownDriverMapper.selectDriverPhone(ownDriver);
        if (count != 0) {   //判断手机号是否重复
            throw new RuntimeException("手机号不能重复!");
        } else {
            ownDriverMapper.updateByPrimaryKey(ownDriver);    //更新司机基本信息
            //获取该司机下面的证件odcId用来匹配被删除的证件
            List<Long> odcIdList = ownDriverCertificateMapper.selectOvcIdByOwnDriverId(ownDriver.getOwnDriverId());
            List<OwnDriverCertificate> list1 = new ArrayList<>();
            List<OwnDriverCertificate> list2 = new ArrayList<>();
            if (null != ownDriverDto.getOwnDriverCertificateList()) {
                for (OwnDriverCertificate item : ownDriverDto.getOwnDriverCertificateList()) {  //迭代根据ownDriverId来区分是新增还是插入
                    item.setOwnDriverId(ownDriver.getOwnDriverId());    //设置ownDriverId
                    if (item.getOdcId() == null) {   //没有主键的则为新增
                        item.setCreateId(ownDriverDto.getUpdateId());
                        item.setCreateName(ownDriverDto.getUpdateName());
                        item.setCompanyId(ownDriverDto.getCompanyId());
                        list1.add(item);
                    } else {
                        item.setUpdateId(ownDriverDto.getUpdateId());
                        item.setUpdateName(ownDriverDto.getUpdateName());
                        item.setCompanyId(ownDriverDto.getCompanyId());
                        list2.add(item);

                        if(null!=odcIdList) {
                            Iterator<Long> it = odcIdList.iterator();
                            while(it.hasNext()){
                                Long ovcId = it.next();
                                if(ovcId==item.getOdcId()){
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                if (list1.size() > 0) {
                    ownDriverCertificateMapper.insertBatch(list1);  //批量插入司机证件
                }
                if (list2.size() > 0) {
                    ownDriverCertificateMapper.updateBatch(list2); //批量更新司机证件
                }
                if(odcIdList.size()>0){
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("odcIds",odcIdList);
                    params.put("updateId",ownDriverDto.getUpdateId());
                    params.put("updateName",ownDriverDto.getUpdateName());
                    ownDriverCertificateMapper.deleteByBatch(params);
                }
                /**下面判断用户表中是否存在该司机电话的账号，不存在的话，则自动保存一条司机的账号信息*/
                String phone = ownDriverDto.getDriverPhone().trim();
                if (!userService.isPhoneBeenRegister(phone)) { //为空则保存司机账号信息
                    RegisterDto registerDto = new RegisterDto();
                    registerDto.setUserPhoneNum(ownDriverDto.getDriverPhone());
                    registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                    registerDto.setIntroducer("");
                    registerDto.setEmail("");
                    try {
                        User user = userService.registerUser(registerDto);  //保存账号信息
                        Driver driver = new Driver();
                        driver.setUserId(user.getUserId());
                        driver.setAffiliatedCompany(ownDriverDto.getAffiliatedCompany());
                        driver.setDriverName(ownDriverDto.getDriverName());
                        driver.setDriverPhone(phone);
                        driver.setCreateId(ownDriverDto.getUpdateId());
                        driver.setCreateName(ownDriverDto.getUpdateName());
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
    public int delDriver(OwnDriverDto ownDriverDto) {
        OwnDriver ownDriver = new OwnDriver();
        BeanUtils.copyProperties(ownDriverDto, ownDriver); //复制对象属性
        try {
            ownDriverMapper.deleteByUpdate(ownDriver);
            OwnDriverCertificate ownDriverCertificate = new OwnDriverCertificate();
            /**设置车辆证件更新信息*/
            ownDriverCertificate.setOwnDriverId(ownDriverDto.getOwnDriverId());
            ownDriverCertificate.setUpdateId(ownDriverDto.getUpdateId());
            ownDriverCertificate.setUpdateName(ownDriverDto.getUpdateName());
            ownDriverCertificate.setCompanyId(ownDriverDto.getCompanyId());
            ownDriverCertificateMapper.deleteByUpdate(ownDriverCertificate);
        } catch (Exception e) {
            throw new RuntimeException("删除失败!");
//            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public PageInfo<List<OwnDriver>> ownDriverList(OwnDriver ownDriver, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        PageInfo page = new PageInfo(ownDriverMapper.selectByCondition(ownDriver));
        return page;
    }

    @Override
    public OwnDriverDto ownDriverDetail(Long ownDriverId, Long companyId) {
        return ownDriverMapper.selectDetail(ownDriverId, companyId);
    }

    @Override
    public List<Driver> getGpsInfo(List<String> driverPhoneList) {
        return driverService.getGpsInfo(driverPhoneList);
    }

    @Override
    public int addGroupInfo(List<DriverGroupRelationship> driverGroupRelationshipList) {
        /**先删除之前的关系，然后重新插入关系*/
        DriverGroupRelationship driverGroupRelationship = driverGroupRelationshipList.get(0);
        int result = 0;
        try {
            driverGroupRelationshipMapper.deleteByOwnDriverId(driverGroupRelationship.getOwnDriverId(), driverGroupRelationship.getCompanyId());
            result = driverGroupRelationshipMapper.insertBatch(driverGroupRelationshipList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加分组信息失败");
        }
        return result;
    }
}
