package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.Timeline;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.traffic.dao.*;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.WaybillSenderNotify;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.service.WaybillRpcService;
import com.lcdt.traffic.service.impl.CustomerCompanyIds;
import com.lcdt.traffic.util.RegisterUtils;
import com.lcdt.traffic.util.WaybillUtil;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.DriverService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.util.ClmsBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lyqishan on 2018/3/19
 */
@Service
public class WaybillRcpServiceImp implements WaybillRpcService {

    Logger logger = LoggerFactory.getLogger(WaybillRcpServiceImp.class);

    @Autowired
    private WaybillMapper waybillMapper;

    @Autowired
    private CustomerCompanyIds customerCompanyIds;

    @Autowired
    private WaybillSenderNotify waybillSenderNotify;

    @Autowired
    private PlanService planService;

    @Autowired
    private SplitGoodsService splitGoodsService;

    @Autowired
    private WaybillItemsMapper waybillItemsMapper;
    @Autowired
    private WaybillTransferRecordMapper waybillTransferRecordMapper;

    @Reference
    private CustomerRpcService customerRpcService;

    @Reference
    private CompanyService companyService;

    @Autowired
    private OwnVehicleMapper ownVehicleMapper;

    @Autowired
    private OwnDriverMapper ownDriverMapper;


    @Autowired
    private ClmsNotifyProducer producer;

    @Reference
    public UserService userService;

    @Reference
    public DriverService driverService;

    @Reference
    private CompanyServiceCountService companyServiceCountService;

    public Waybill addWaybill(WaybillDto waybillDto) {

        //先判断是否还有剩于运单服务条数(后面计费用)
        if (!companyServiceCountService.checkCompanyProductCount(waybillDto.getCompanyId(), "waybill_service", 1)) {
            throw new RuntimeException("剩余运单服务次数不足");
        }

        Waybill waybill = new Waybill();
        BeanUtils.copyProperties(waybillDto, waybill);

        //计划传过来，判断对应运单有几条数据，然后生成运单编码
        if (waybill.getWaybillPlanId() != null && waybill.getWaybillPlanId() > 0) {
            Map map = new HashMap();
            map.put("companyId", waybill.getCompanyId());
            map.put("waybillPlanId", waybill.getWaybillPlanId());
            map.put("noCancel", "123");
            List<Waybill> list = waybillMapper.selectWaybillByPlanId(map);
            if (list != null) {
                waybill.setWaybillCode(waybill.getWaybillCode() + "-" + (list.size() + 1));
            } else {
                waybill.setWaybillCode(waybill.getWaybillCode() + "-1");
            }
        }

        //判断运单编码是否存在（相同企业下的运单编码不能重复）
        if (null != waybill.getWaybillCode() && !("").equals(waybill.getWaybillCode())) {
            if (isExistWaybillByCodeAndCompanyId(waybill.getWaybillCode(), waybill.getCompanyId())) {
                throw new RuntimeException("运单编号已存在!");
            }
        }

        if (waybill.getCarrierCompanyId() != null) {
            if (waybill.getCompanyId().equals(waybill.getCarrierCompanyId())) {
                Company carrierCompany = companyService.selectById(waybill.getCarrierCompanyId());
                if (carrierCompany != null) {
                    waybill.setCarrierCompanyName(carrierCompany.getFullName());
                }
            } else {
                //设置承运商名字在本企业的客户名称
                Customer carrierCustomer = customerRpcService.queryCustomer(waybill.getCompanyId(), waybill.getCarrierCompanyId());
                if (carrierCustomer != null && carrierCustomer.getCustomerName() != null) {
                    waybill.setCarrierCompanyName(carrierCustomer.getCustomerName());
                }
            }
        }

        //判断前端车辆是否输入还是选择
        if (waybill.getVechicleId() == null || waybill.getVechicleId().equals("")) {
            OwnVehicle ownVehicle = new OwnVehicle();
            ownVehicle.setVehicleNum(waybill.getVechicleNum());
            ownVehicle.setCompanyId(waybill.getCompanyId());
            OwnVehicle vehicle = ownVehicleMapper.selectAddWaybillVehicleNum(ownVehicle);
            if (vehicle != null) {
                waybill.setVechicleId(vehicle.getOwnVehicleId());
            } else {
                ownVehicle.setCreateId(waybill.getCreateId());
                ownVehicle.setCreateName(waybill.getCreateName());
                ownVehicle.setAffiliatedCompany(waybill.getCarrierCompanyName());
                ownVehicle.setVehicleCategory((short) 0);
                ownVehicleMapper.insert(ownVehicle);
                waybill.setVechicleId(ownVehicle.getOwnVehicleId());
            }
        }
        //判断前端司机输入还是选择
        if (waybill.getDriverId() == null || waybill.getDriverId().equals("")) {
            OwnDriver ownDriver = new OwnDriver();
            ownDriver.setCompanyId(waybill.getCompanyId());
            ownDriver.setDriverPhone(waybill.getDriverPhone());
            ownDriver.setAffiliatedCompany(waybill.getCarrierCompanyName());
            ownDriver.setCreateId(waybill.getCreateId());
            ownDriver.setCreateName(waybill.getCreateName());
            ownDriver.setDriverName(waybill.getDriverName());
            ownDriver.setDriverCategory((short) 0);
            String phone = waybill.getDriverPhone();
            OwnDriver driver = ownDriverMapper.selectByAddWaybillDriverPhone(ownDriver);
            if (driver != null) {
                ownDriver.setDriverId(driver.getDriverId());
                waybill.setDriverName(driver.getDriverName());
            } else {
                /**判断是否已经开通cLMS司机账号，若没有开通，则自动开通,新增一条司机账号信息*/
                if (!driverService.isExistDriver(phone)) {
                    if (!userService.isPhoneBeenRegister(phone)) { //为空则保存clms司机账号信息
                        RegisterDto registerDto = new RegisterDto();
                        registerDto.setUserPhoneNum(phone);
                        registerDto.setPassword(RegisterUtils.md5Encrypt(phone.substring(5)));
                        logger.debug("司机账号默认密码：" + phone.substring(5));
                        registerDto.setIntroducer("");
                        registerDto.setEmail("");
                        registerDto.setRegisterFrom("管车宝小程序");
                        try {
                            User user = userService.registerUser(registerDto);  //保存账号信息
                            Driver driverUser = new Driver();
                            driverUser.setUserId(user.getUserId());
                            driverUser.setAffiliatedCompany(waybill.getCarrierCompanyName());
                            driverUser.setDriverName(waybill.getDriverName());
                            driverUser.setDriverPhone(phone);
                            driverUser.setCreateId(waybill.getCreateId());
                            driverUser.setCreateName(waybill.getCreateName());
                            driverService.addDriver(driverUser);    //保存司机信息
                            /*将司机账号的user_id更新到我的司机表里*/
                            ownDriver.setDriverId(user.getUserId());
                            ownDriverMapper.insert(ownDriver);
                        } catch (PhoneHasRegisterException e) {
                            e.printStackTrace();
                            throw new RuntimeException("保存司机账号信息失败！");
                        }
                    } else {
                        User user = userService.selectUserByPhone(phone);
                        Driver driverUser = new Driver();
                        driverUser.setUserId(user.getUserId());
                        driverUser.setAffiliatedCompany(waybill.getCarrierCompanyName());
                        driverUser.setDriverName(waybill.getDriverName());
                        driverUser.setDriverPhone(phone);
                        driverUser.setCreateId(waybill.getCreateId());
                        driverUser.setCreateName(waybill.getCreateName());
                        driverService.addDriver(driverUser);    //保存司机信息
                        /*将司机账号的user_id更新到我的司机表里*/
                        ownDriver.setDriverId(user.getUserId());
                        ownDriverMapper.insert(ownDriver);
                    }
                } else {
                    //开通过司机账号，测直接添加到我的司机里
                    User user = userService.selectUserByPhone(phone);
                    ownDriver.setDriverId(user.getUserId());
                    ownDriverMapper.insert(ownDriver);
                }
            }
            waybill.setDriverId(ownDriver.getDriverId());
        }
        //新增运单
        waybillMapper.insert(waybill);
        //运单货物详细
        if (waybillDto.getWaybillItemsDtoList() != null && waybillDto.getWaybillItemsDtoList().size() > 0) {
            List<WaybillItems> waybillItemsList= waybillDto.getWaybillItemsDtoList().stream()
                    .map(item -> {
                        WaybillItems waybillItems = new WaybillItems();
                        BeanUtils.copyProperties(item, waybillItems);
                        waybillItems.setCreateId(waybill.getCreateId());
                        waybillItems.setCreateName(waybill.getCreateName());
                        waybillItems.setCompanyId(waybill.getCompanyId());
                        waybillItems.setWaybillId(waybill.getId());
                        return waybillItems;
                    }).collect(Collectors.toList());
            //运单货物详细批量插入
            waybillItemsMapper.insertForBatch(waybillItemsList);
        }
        //运单生成时添加一条派车记录
        if (waybill.getId() != null) {
            WaybillTransferRecord waybillTransferRecord = new WaybillTransferRecord();
            waybillTransferRecord.setWaybillId(waybill.getId());
            waybillTransferRecord.setVechicleId(waybill.getVechicleId());
            waybillTransferRecord.setVechicleNum(waybill.getVechicleNum());
            waybillTransferRecord.setDriverId(waybill.getDriverId());
            waybillTransferRecord.setDriverName(waybill.getDriverName());
            waybillTransferRecord.setDriverPhone(waybill.getDriverPhone());
            waybillTransferRecord.setCompanyId(waybill.getCompanyId());
            waybillTransferRecord.setCarrierCompanyId(waybill.getCarrierCompanyId());
            waybillTransferRecord.setCreateId(waybill.getCreateId());
            waybillTransferRecord.setCreateName(waybill.getCreateName());

            waybillTransferRecordMapper.insert(waybillTransferRecord);
        }

        Waybill result = waybillMapper.selectByPrimaryKey(waybill.getId());

        //扣减运单费用
        if (result != null) {
            companyServiceCountService.reduceCompanyProductCount(waybillDto.getCompanyId(), "waybill_service", 1,waybillDto.getCreateName(),"生成运单...");
        }

        //新建运单路由
        addWaybillRoute(waybill,waybillDto.getCreateName(),waybillDto.getCreatePhone(),0);

        return waybill;
    }

    @Override
    public int modifyOwnWaybill(WaybillModifyParamsDto waybillDto) {
        int result = 0;
        Waybill waybill = queryOwnWaybill(waybillDto.getId(), waybillDto.getCompanyId());
        waybillDto.setCarrierCompanyId(waybill.getCarrierCompanyId());
        BeanUtils.copyProperties(waybillDto, waybill);
        //更新运单
        result += waybillMapper.updateByPrimaryKeyAndCompanyId(waybill);
        //运单货物详细
        result += modifyWaybillItems(waybillDto.getWaybillItemsDtoList(), waybill);
        return result;
    }

    @Override
    public int modifyCustomerWaybill(WaybillModifyParamsDto waybillDto) {
        int result = 0;
        Waybill waybill = queryCustomerWaybill(waybillDto.getId(), waybillDto.getCarrierCompanyId());
        waybillDto.setCompanyId(waybill.getCompanyId());
        BeanUtils.copyProperties(waybillDto, waybill);
        ClmsBeanUtil.copyPropertiesIgnoreNull(waybillDto, waybill);
        //新增运单
        result += waybillMapper.updateByPrimaryKeyAndCarrierCompanyId(waybill);
        //运单货物详细
        result += modifyWaybillItems(waybillDto.getWaybillItemsDtoList(), waybill);
        return result;
    }

    //更新货物，如果有id则更新，如果没有id，则新增
    private int modifyWaybillItems(List<WaybillItemsModifyParamsDto> waybillItemsDtoList, Waybill waybill) {
        int result = 0;
        if (waybillItemsDtoList != null && waybillItemsDtoList.size() > 0) {

            //有id的需要更新的分为一组，没有id需要新增的分为一组
            Map<String, List<WaybillItemsModifyParamsDto>> waybillItemsMap = waybillItemsDtoList.stream()
                    .collect(Collectors.groupingBy(item -> {
                        if (item.getId() != null && item.getId() > 0) {
                            return "update";
                        } else {
                            return "add";
                        }
                    }));

            //需要更新的 List<WaybillItemsModifyParamsDto> 转为 List<WaybillItems>
            List<WaybillItems> waybillItemsUpdateList = waybillItemsMap.get("update").stream()
                    .map(item -> {
                        WaybillItems waybillItem = waybillItemsMapper.selectByPrimaryKey(item.getId());
                        BeanUtils.copyProperties(item, waybillItem);
                        waybillItem.setUpdateId(waybill.getUpdateId());
                        waybillItem.setUpdateName(waybill.getUpdateName());
                        waybillItem.setCompanyId(waybill.getCompanyId());
                        return waybillItem;
                    }).collect(Collectors.toList());

            //需要新增的 List<WaybillItemsModifyParamsDto> 转为 List<WaybillItems>
            List<WaybillItems> waybillItemsAddList = waybillItemsMap.get("add").stream()
                    .map(item -> {
                        WaybillItems waybillItem = new WaybillItems();
                        BeanUtils.copyProperties(item, waybillItem);
                        waybillItem.setCreateId(waybill.getCreateId());
                        waybillItem.setCreateName(waybill.getCreateName());
                        waybillItem.setCompanyId(waybill.getCompanyId());
                        waybillItem.setWaybillId(waybill.getId());
                        return waybillItem;
                    }).collect(Collectors.toList());

            if (waybillItemsUpdateList.size() > 0) {
                //运单货物详细修改
                result += waybillItemsMapper.updateForBatch(waybillItemsUpdateList);
            }
            if (waybillItemsAddList.size() > 0) {
                //运单货物详细批量插入
                result += waybillItemsMapper.insertForBatch(waybillItemsAddList);
            }
        }
        return result;
    }

    @Override
    public int modifyOwnQuantity(WaybillModifyParamsDto waybillDto) {
        WaybillDao waybillDao = queryOwnWaybill(waybillDto.getId(), waybillDto.getCompanyId());
        waybillDao.setUpdateId(waybillDto.getUpdateId());
        waybillDao.setUpdateName(waybillDto.getUpdateName());
        return modifyWaybillItemsAmountNum(waybillDto, waybillDao);
    }

    @Override
    public int modifyCustomerQuantity(WaybillModifyParamsDto waybillDto) {
        WaybillDao waybillDao = queryCustomerWaybill(waybillDto.getId(), waybillDto.getCarrierCompanyId());
        waybillDao.setUpdateId(waybillDto.getUpdateId());
        waybillDao.setUpdateName(waybillDto.getUpdateName());
        return modifyWaybillItemsAmountNum(waybillDto, waybillDao);
    }

    //我的运单、客户运单：货物详细调量公用方法
    private int modifyWaybillItemsAmountNum(WaybillModifyParamsDto waybillDto, WaybillDao waybillDao) {
        int result = 0;
        if (waybillDto.getWaybillItemsDtoList() != null && waybillDto.getWaybillItemsDtoList().size() > 0) {
            List<WaybillItems> waybillItemsUpdateList = waybillDto.getWaybillItemsDtoList().stream()
                    .filter(dto -> dto.getId() != null && dto.getId() > 0)
                    .map(dto -> {
                        WaybillItems waybillItem = waybillItemsMapper.selectByPrimaryKey(dto.getId());
                        ClmsBeanUtil.copyPropertiesIgnoreNull(dto, waybillItem);
                        waybillItem.setUpdateId(waybillDto.getUpdateId());
                        waybillItem.setUpdateName(waybillDto.getUpdateName());
                        waybillItem.setCompanyId(waybillDto.getCompanyId());

                        //对waybillDao里面的货物详细计划数量
                        waybillDao.getWaybillItemsList().forEach(item -> {
                            item.setAmount(item.getAmount() - waybillItem.getAmount());
                        });
                        return waybillItem;
                    }).collect(Collectors.toList());

            if (waybillItemsUpdateList.size() > 0) {
                //运单货物详细修改
                result = waybillItemsMapper.updateForBatch(waybillItemsUpdateList);
            }
            //计划编辑
            planService.adjustPlanAndSplitAmount(waybillDao);
        }
        return result;
    }

    @Override
    public PageInfo queryOwnWaybillList(WaybillOwnListParamsDto dto) {
        List<WaybillDao> resultList = null;

        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo() != null) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize() != null) {
            pageSize = dto.getPageSize();
        }

        Map map = ClmsBeanUtil.beanToMap(dto);
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectOwnByCondition(map);
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public PageInfo queryCustomerWaybillList(WaybillCustListParamsDto dto) {
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo() != null) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize() != null) {
            pageSize = dto.getPageSize();
        }
        String customerIds = dto.getCustomerIds();
        Map map = ClmsBeanUtil.beanToMap(dto);
        Map cMapIds = customerCompanyIds.getCustomerCompanyIds(map);
        map.put("companyIds", cMapIds.get("companyIds"));
        map.put("carrierCompanyId", map.get("companyId"));
        map.remove("companyId");
        map.remove("customerName");
        PageHelper.startPage(pageNo, pageSize);
        List<WaybillDao> resultList = waybillMapper.selectCustomerByCondition(map);
        //对customerName重新赋值
        resultList.forEach(result -> {
            Customer customer = customerRpcService.queryCustomer(result.getCarrierCompanyId(), result.getCompanyId());
            result.setWaybillSource(customer.getCustomerName());
        });
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public Waybill modifyOwnWaybillStatus(WaybillModifyStatusDto dto) {
        Waybill waybill = null;
        Map map = ClmsBeanUtil.beanToMap(dto);
        if (dto.getWaybillStatus() == ConstantVO.WAYBILL_STATUS_IS_UNLOADING) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
            if (waybill != null) {
                List<String> phoneList = new ArrayList<>();
                phoneList.add(waybill.getDriverPhone());
                List<Driver> driverList = driverService.getGpsInfo(phoneList);
                for (Driver driver : driverList) {
                    if (driver.getDriverPhone() != null && driver.getDriverPhone().equals(waybill.getDriverPhone())) {
//                        map.put("longitude",1);
//                        map.put("latitude",1);
                        map.put("unloadLocation", driver.getCurrentLocation());
                        map.put("unloadTime", new Date());
                    }
                }
            }
        }
        int result = waybillMapper.updateOwnWaybillStatus(map);

        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("修改失败");
        }
        //发送消息通知
        modifyOwnWaybillStatusToSendNotify(map);
        //返回计划相关信息
        modifyWaybillPlanInfo(map);
        //路由==>运单增加新建路由 by xrr
        addWaybillRoute(waybill,dto.getUpdateName(),dto.getUpdatePhone(),0);
        return waybill;
    }

    @Override
    public Waybill modifyCustomerWaybillStatus(WaybillModifyStatusDto dto) {
        Waybill waybill = null;
        Map map = ClmsBeanUtil.beanToMap(dto);
        if (dto.getWaybillStatus() == ConstantVO.WAYBILL_STATUS_IS_UNLOADING) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
            if (waybill != null) {
                List<String> phoneList = new ArrayList<>();
                phoneList.add(waybill.getDriverPhone());
                List<Driver> driverList = driverService.getGpsInfo(phoneList);
                for (Driver driver : driverList) {
                    if (driver.getDriverPhone() != null && driver.getDriverPhone().equals(waybill.getDriverPhone())) {
//                        map.put("longitude",1);
//                        map.put("latitude",1);
                        map.put("unloadLocation", driver.getCurrentLocation());
                        map.put("unloadTime", new Date());
                    }
                }
            }
        }
        int result = waybillMapper.updateCustomerWaybillStatus(map);

        //发送消息通知
        modifyCustomerWaybillStatusToSendNotify(map);
        //返回计划相关信息
        modifyWaybillPlanInfo(map);

        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("修改失败");
        }

        Customer customer = customerRpcService.queryCustomer(waybill.getCarrierCompanyId(), waybill.getCompanyId());
        waybill.setWaybillSource(customer.getCustomerName());

        //路由==>运单增加新建路由 by xrr
        addWaybillRoute(waybill,dto.getUpdateName(),dto.getUpdatePhone(),0);

        return waybill;
    }

    @Override
    public WaybillDao queryOwnWaybill(Long waybillId, Long companyId) {
        Waybill waybill = new Waybill();
        waybill.setId(waybillId);
        waybill.setCompanyId(companyId);
        return waybillMapper.selectOwnWaybillByIdAndCompanyId(waybill);
    }

    @Override
    public WaybillDao queryCustomerWaybill(Long waybillId, Long carrierCompanyId) {
        Waybill waybill = new Waybill();
        waybill.setId(waybillId);
        waybill.setCarrierCompanyId(carrierCompanyId);
        return waybillMapper.selectCustomerWaybillByIdAndCarrierCompanyId(waybill);
    }

    @Override
    public Waybill modifyOwnWaybillReceipt(WaybillModifyReceiptDto dto) {
        Waybill waybill = null;
        Map map = ClmsBeanUtil.beanToMap(dto);
        int result = waybillMapper.updateOwnWaybillStatus(map);

        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("修改失败");
        }

        return waybill;
    }

    @Override
    public Waybill modifyCustomerWaybillReceipt(WaybillModifyReceiptDto dto) {
        Waybill waybill = null;
        Map map = ClmsBeanUtil.beanToMap(dto);
        int result = waybillMapper.updateCustomerWaybillStatus(map);
        //发送消息通知
        waybillSenderNotify.customerReceiptSendNotify(dto.getWaybillIds(), dto.getUpdateId());

        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("修改失败");
        }

        Customer customer = customerRpcService.queryCustomer(waybill.getCarrierCompanyId(), waybill.getCompanyId());
        waybill.setWaybillSource(customer.getCustomerName());

        //路由==>运单上传回单路由 by xrr
        addWaybillRoute(waybill,dto.getUpdateName(),dto.getUpdatePhone(),1);

        return waybill;
    }


    @Override
    public int modifyOwnWaybillStatusByWaybillPlanId(Map map) {
        int result = 0;
        result = waybillMapper.updateOwnWaybillStatusByWaybillPlanId(map);
        modifyWaybillPlanInfo(map);
        return result;
    }

    @Override
    public PageInfo queryDriverWaybillList(DriverWaybillListParsmsDto dto) {
        List<WaybillDao> resultList = null;
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (dto.getPageNo() > 0) {
            pageNo = dto.getPageNo();
        }
        if (dto.getPageSize() > 0) {
            pageSize = dto.getPageSize();
        }
        Map map = new HashMap();

        map.put("driverId", dto.getDriverId());
        ArrayList status = new ArrayList();
        if (dto.getWaybillStatus() != null) {
            //将状态值里的0替换为空
            for (int i = 0; i < dto.getWaybillStatus().length; i++) {
                if (dto.getWaybillStatus()[i].equals("0")) {
                    break;
                } else {
                    status.add(dto.getWaybillStatus()[i]);
                }
            }
        }
        if (status != null) {
            map.put("waybillStatus", status.toArray());
        }
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectDriverByCondition(map);
        for (int i = 0; i < resultList.size(); i++) {
            Company company = companyService.selectById(resultList.get(i).getCarrierCompanyId());
            resultList.get(i).setWaybillSource(company.getFullName());
        }
        page = new PageInfo(resultList);
        return page;
    }


    @Override
    public Waybill modifyWaybillStatusByDriver(DriverWaybillParamsDto dto) {
        Waybill waybill = null;
        Map map = new HashMap();
        map.put("driverId", dto.getDriverId());
        map.put("updateName", dto.getUpdateName());
        map.put("updateId", dto.getUpdateId());
        map.put("waybillStatus", dto.getWaybillStatus());
        map.put("waybillIds", dto.getWaybillIds());
        if (dto.getWaybillStatus() == ConstantVO.WAYBILL_STATUS_IS_UNLOADING) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
            if (waybill != null) {
                List<String> phoneList = new ArrayList<>();
                phoneList.add(waybill.getDriverPhone());
                List<Driver> driverList = driverService.getGpsInfo(phoneList);
                for (Driver driver : driverList) {
                    if (driver.getDriverPhone() != null && driver.getDriverPhone().equals(waybill.getDriverPhone())) {
//                        map.put("longitude",1);
//                        map.put("latitude",1);
                        map.put("unloadLocation", driver.getCurrentLocation());
                        map.put("unloadTime", new Date());
                    }
                }
            }
        }
        int result = waybillMapper.updateWaybillByDriver(map);
        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("修改失败");
        }
        //司机卸货，发送通知
        waybillSenderNotify.driverUnloadinSendNotify(dto.getWaybillIds().toString(), dto.getUpdateId());

        Company company = companyService.selectById(waybill.getCarrierCompanyId());
        waybill.setWaybillSource(company.getFullName());

        //路由==>运单增加新建路由 by xrr
        addWaybillRoute(waybill,dto.getUpdateName(),dto.getUpdatePhone(),0);

        return waybill;

    }

    @Override
    public Waybill modifyWaybillReceiptByDriver(DriverWaybillParamsDto dto) {
        Waybill waybill = null;
        Map map = new HashMap();
        map.put("driverId", dto.getDriverId());
        map.put("updateName", dto.getUpdateName());
        map.put("updateId", dto.getUpdateId());
        map.put("waybillIds", dto.getWaybillIds());
        map.put("electronicalReceipt", dto.getElectronicalReceipt());
        int result = waybillMapper.updateWaybillByDriver(map);
        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("上传失败");
        }

        //上传电子回单，发消息通知
        waybillSenderNotify.driverReceiptSendNotify(dto.getWaybillIds().toString(), dto.getUpdateId());

        Company company = companyService.selectById(waybill.getCarrierCompanyId());
        waybill.setWaybillSource(company.getFullName());

        //路由==>运单上传回单路由 by xrr
        addWaybillRoute(waybill,dto.getUpdateName(),dto.getUpdatePhone(),1);

        return waybill;
    }

    /**
     * 取消运单时，需要将运单数量还原到派单，运单状态置为取消,运单完成时，判断此plan下的运单是否全部完成，如果是全部完成，则更新计划状态为完成状态
     *
     * @param map
     * @return
     */
    private void modifyWaybillPlanInfo(Map map) {
        short waybillStatus = (short) map.get("waybillStatus");
        if (waybillStatus == ConstantVO.WAYBILL_STATUS_HAVE_CANCEL) {
            List<WaybillDao> list = waybillMapper.selectWaybillByIdOrPlanId(map);
            if (list != null && list.size() > 0) {
                for (WaybillDao dao : list) {
                    if (dao.getWaybillPlanId() != null && !dao.getWaybillPlanId().equals("")) {
                        List<WaybillItems> itemsList = dao.getWaybillItemsList();
                        List<SplitGoodsDetail> splitGoodsDetailList = new ArrayList<SplitGoodsDetail>();
                        for (WaybillItems item : itemsList) {
                            //根据派单货物明细id更新派单货物明细数量
                            SplitGoodsDetail sp = new SplitGoodsDetail();
                            float amount = item.getAmount();
                            Long splitGoodsDetailId = item.getSplitGoodsDetailId();
                            sp.setUpdateId((Long) map.get("updateId"));
                            sp.setUpdateName((String) map.get("updateName"));
                            sp.setUpdateTime(new Date());
                            sp.setRemainAmount(amount);
                            sp.setSplitGoodsDetailId(splitGoodsDetailId);
                            splitGoodsDetailList.add(sp);
                        }
                        //压入派单ID
                        map.put("waybillPlanId", dao.getWaybillPlanId());
                        map.put("splitGoodsId", dao.getSplitGoodsId());
                        map.put("companyId", dao.getCompanyId());
                        splitGoodsService.waybillCancel4SplitGoods(splitGoodsDetailList, map);
                    }

                }

            }

        }
        if (waybillStatus == ConstantVO.WAYBILL_STATUS_HAVE_FINISH) {
            List<Waybill> list = waybillMapper.selectWaybillPlanId(map);
            if (list != null && list.size() > 0) {
                for (Waybill waybill : list) {
                    if (waybill != null) {
                        if (waybill.getWaybillPlanId() != null && !waybill.getWaybillPlanId().equals("")) {
                            map.put("waybillPlanId", waybill.getWaybillPlanId());
                            List<Waybill> waybillList = waybillMapper.selectWaybillByPlanId(map);
                            boolean flag = true;
                            for (Waybill bill : waybillList) {
                                if (bill.getWaybillStatus() != ConstantVO.WAYBILL_STATUS_HAVE_FINISH) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                //此计划下的运单全部完成，根据计划id 更新计划状态为完成
                                WaybillPlan waybillPlan = new WaybillPlan();
                                waybillPlan.setUpdateId((Long) map.get("updateId"));
                                waybillPlan.setUpdateName((String) map.get("updateName"));
                                waybillPlan.setUpdateTime(new Date());
                                waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED);
                                waybillPlan.setWaybillPlanId(waybill.getWaybillPlanId());
                                waybillPlan.setFinishDate(new Date());
                                planService.updatePlanStatusByWaybill(waybillPlan);

                                //router:计划自动完成
                                Timeline event = new Timeline();
                                String wayBillCode = waybill.getWaybillCode() == null ? "" : waybill.getWaybillCode();
                                event.setActionTitle("【计划完成】" + wayBillCode);
                                event.setActionTime(new Date());
                                event.setCompanyId(waybillPlan.getCompanyId());
                                event.setSearchkey("R_PLAN");
                                event.setDataid(waybillPlan.getWaybillPlanId());

                                producer.noteRouter(event);
                            }
                        }
                    }
                }
            }
        }
    }

    //我的运单状态更改时，发送的消息
    private void modifyOwnWaybillStatusToSendNotify(Map map) {
        short status = (short) map.get("waybillStatus");
        String waybillIds = (String) map.get("waybillIds");
        Long userId = (Long) map.get("updateId");
        Long companyId = (Long) map.get("companyId");

        //门卫入厂
        if (status == ConstantVO.WAYBILL_STATUS_HAVE_FACTORY) {
            waybillSenderNotify.enterFactorySenderNotify(waybillIds, companyId, userId);
        }
        //装车完成
        if (status == ConstantVO.WAYBILL_STATUS_HAVE_LOADING) {
            waybillSenderNotify.haveLoadingSendNotify(waybillIds, companyId, userId);
        }

        //门卫出厂
        if (status == ConstantVO.WAYBILL_STATUS_IN_TRANSIT) {
            waybillSenderNotify.transitSendNotify(waybillIds, companyId, userId);
        }
        //我的运单 已完成
        if (status == ConstantVO.WAYBILL_STATUS_HAVE_FINISH) {
            waybillSenderNotify.ownFinishSendNotify(waybillIds, companyId, userId);
        }
        //我的运单 已取消
        if (status == ConstantVO.WAYBILL_STATUS_HAVE_CANCEL) {
            waybillSenderNotify.ownCancelSendNotify(waybillIds, companyId, userId);
        }


    }

    //客户运单状态更改时，发送的消息
    private void modifyCustomerWaybillStatusToSendNotify(Map map) {
        short status = (short) map.get("waybillStatus");
        String waybillIds = (String) map.get("waybillIds");
        Long userId = (Long) map.get("updateId");

        //客户运单 承运商卸货
        if (status == ConstantVO.WAYBILL_STATUS_IS_UNLOADING) {
            waybillSenderNotify.customerUnloadingSendNotify(waybillIds, userId);
        }
        //客户运单 已完成
        if (status == ConstantVO.WAYBILL_STATUS_HAVE_FINISH) {
            waybillSenderNotify.customerFinishSendNotify(waybillIds, userId);
        }
        //客户运单 已取消
        if (status == ConstantVO.WAYBILL_STATUS_HAVE_CANCEL) {
            waybillSenderNotify.customerCancelSendNotify(waybillIds, userId);
        }

    }

    private boolean isExistWaybillByCodeAndCompanyId(String waybillCode, Long companyId) {
        Map map = new HashMap();
        map.put("waybillCode", waybillCode);
        map.put("companyId", companyId);
        List<Waybill> waybillList = waybillMapper.selectByCodeAndCompanyId(map);
        if (waybillList != null && waybillList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    private void addWaybillRoute(Waybill waybill,String operatorName,String operatorPhone ,int operatorType){
        //路由==>运单增加新建路由 by xrr
        Timeline event = new Timeline();
        if(operatorType==1){
            event.setActionTitle("【回单上传】（操作人：" + operatorName + " " + operatorName + "）");
        }else{
            event.setActionTitle("【" + WaybillUtil.map_waybill_status.get(waybill.getWaybillStatus()) + "】（操作人：" + operatorName + " " + operatorPhone + "）");
        }
        event.setActionTime(new Date());
        event.setCompanyId(waybill.getCompanyId());
        event.setSearchkey("WAYBILL_ROUTE");
        event.setDataid(waybill.getId());

        if (waybill.getWaybillStatus() == 5) {
            //卸货描述修改
            event.setActionDes("当前位置：" + waybill.getUnloadLocation());
        }
        event.setActionDes("司机：" + waybill.getDriverName() + " " + waybill.getDriverPhone() + " " + waybill.getVechicleNum());

        producer.noteRouter(event);
    }

}
