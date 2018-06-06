package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.Timeline;
import com.lcdt.traffic.dao.SplitGoodsMapper;
import com.lcdt.traffic.dao.WaybillItemsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.dao.WaybillTransferRecordMapper;
import com.lcdt.traffic.dto.WaybillDto;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillDao;
import com.lcdt.traffic.model.WaybillItems;
import com.lcdt.traffic.model.WaybillTransferRecord;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.WaybillSenderNotify;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.GprsLocationBo;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by lyqishan on 2017/12/20
 */
@Transactional
@Service
public class WaybillServiceImpl implements WaybillService {


    Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Reference
    public DriverService driverService;

    @Autowired
    private WaybillMapper waybillMapper; //运单
    @Autowired
    private WaybillItemsMapper waybillItemsMapper; //运单货物详细

    @Autowired
    private PlanService planService;

    @Autowired
    private ClmsNotifyProducer producer;

    @Autowired
    private SplitGoodsMapper splitGoodsMapper;

    @Reference
    private CustomerRpcService customerRpcService;

    @Autowired
    private WaybillSenderNotify waybillSenderNotify;

    @Autowired
    private SplitGoodsService splitGoodsService;

    @Reference
    private CompanyService companyService;

    @Autowired
    private WaybillTransferRecordMapper waybillTransferRecordMapper;;

    @Override
    public Waybill addWaybill(WaybillDto waybillDto) {
        int result = 0;
        Waybill waybill = new Waybill();
        BeanUtils.copyProperties(waybillDto, waybill);

        //计划传过来，判断对应运单有几条数据，然后生成运单编码
        if (waybill.getWaybillPlanId() != null && waybill.getWaybillPlanId() > 0) {
            Map map = new HashMap();
            map.put("companyId", waybill.getCompanyId());
            map.put("waybillPlanId", waybill.getWaybillPlanId());
            map.put("noCancel","123");
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

        //新增运单
        result += waybillMapper.insert(waybill);
        //运单货物详细
        if (waybillDto.getWaybillItemsDtoList() != null && waybillDto.getWaybillItemsDtoList().size() > 0) {
            List<WaybillItems> waybillItemsList = new ArrayList<WaybillItems>();
            for (int i = 0; i < waybillDto.getWaybillItemsDtoList().size(); i++) {
                WaybillItems waybillItems = new WaybillItems();
                BeanUtils.copyProperties(waybillDto.getWaybillItemsDtoList().get(i), waybillItems);
                waybillItems.setCreateId(waybill.getCreateId());
                waybillItems.setCreateName(waybill.getCreateName());
                waybillItems.setCompanyId(waybill.getCompanyId());
                waybillItems.setWaybillId(waybill.getId());
                waybillItemsList.add(waybillItems);
            }
            //运单货物详细批量插入
            result += waybillItemsMapper.insertForBatch(waybillItemsList);
        }
        //运单生成时添加一条派车记录
        if(waybill.getId()!=null){
            WaybillTransferRecord  waybillTransferRecord=new WaybillTransferRecord();
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

        waybill = waybillMapper.selectByPrimaryKey(waybill.getId());
        //路由==>运单增加新建 by xrr
        Timeline event = new Timeline();
        event.setActionTitle("【运单生成】");
        event.setActionTime(new Date());
        event.setCompanyId(waybill.getCompanyId());
        event.setSearchkey("WAYBILL_ROUTE");
        event.setDataid(waybill.getId());
        event.setActionDes("司机："+waybill.getDriverName()+" "+waybill.getDriverPhone()+" "+waybill.getVechicleNum());
        producer.noteRouter(event);
        return waybill;
    }

    @Override
    public int deleteWaybill(Long waybillId, Long companyId) {
        return 0;
    }

    @Override
    public int modifyOwnWaybill(WaybillDto waybillDto) {
        int result = 0;
        Waybill waybill = queryOwnWaybill(waybillDto.getId(),waybillDto.getCompanyId());
        BeanUtils.copyProperties(waybillDto, waybill);
        //新增运单
        result += waybillMapper.updateByPrimaryKeyAndCompanyId(waybill);
        //运单货物详细
        if (waybillDto.getWaybillItemsDtoList() != null && waybillDto.getWaybillItemsDtoList().size() > 0) {
            List<WaybillItems> waybillItemsUpdateList = new ArrayList<WaybillItems>();
            List<WaybillItems> waybillItemsList = new ArrayList<WaybillItems>();
            for (int i = 0; i < waybillDto.getWaybillItemsDtoList().size(); i++) {
                WaybillItems waybillItems = new WaybillItems();
                BeanUtils.copyProperties(waybillDto.getWaybillItemsDtoList().get(i), waybillItems);
                if (waybillItems.getId() > 0) {
                    waybillItems.setUpdateId(waybill.getUpdateId());
                    waybillItems.setUpdateName(waybill.getUpdateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItemsUpdateList.add(waybillItems);
                } else {
                    waybillItems.setCreateId(waybill.getCreateId());
                    waybillItems.setCreateName(waybill.getCreateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItems.setWaybillId(waybill.getId());
                    waybillItemsList.add(waybillItems);
                }
            }
            if (waybillItemsUpdateList.size() > 0) {
                //运单货物详细修改
                result += waybillItemsMapper.updateForBatch(waybillItemsList);
            }
            if (waybillItemsList.size() > 0) {
                //运单货物详细批量插入
                result += waybillItemsMapper.insertForBatch(waybillItemsList);
            }
        }
        return result;
    }

    @Override
    public int modifyCustomerWaybill(WaybillDto waybillDto) {
        int result = 0;
        Waybill waybill = new Waybill();
        BeanUtils.copyProperties(waybillDto, waybill);
        //新增运单
        result += waybillMapper.updateByPrimaryKeyAndCarrierCompanyId(waybill);
        //运单货物详细
        if (waybillDto.getWaybillItemsDtoList() != null && waybillDto.getWaybillItemsDtoList().size() > 0) {
            List<WaybillItems> waybillItemsUpdateList = new ArrayList<WaybillItems>();
            List<WaybillItems> waybillItemsList = new ArrayList<WaybillItems>();
            for (int i = 0; i < waybillDto.getWaybillItemsDtoList().size(); i++) {
                WaybillItems waybillItems = new WaybillItems();
                BeanUtils.copyProperties(waybillDto.getWaybillItemsDtoList().get(i), waybillItems);
                if (waybillItems.getId() > 0) {
                    waybillItems.setUpdateId(waybill.getUpdateId());
                    waybillItems.setUpdateName(waybill.getUpdateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItemsUpdateList.add(waybillItems);
                } else {
                    waybillItems.setCreateId(waybill.getCreateId());
                    waybillItems.setCreateName(waybill.getCreateName());
                    waybillItems.setCompanyId(waybill.getCompanyId());
                    waybillItems.setWaybillId(waybill.getId());
                    waybillItemsList.add(waybillItems);
                }
            }
            if (waybillItemsUpdateList.size() > 0) {
                //运单货物详细修改
                result += waybillItemsMapper.updateForBatch(waybillItemsList);
            }
            if (waybillItemsList.size() > 0) {
                //运单货物详细批量插入
                result += waybillItemsMapper.insertForBatch(waybillItemsList);
            }
        }
        return result;
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
    public PageInfo queryPlannedWaybillList(Map map) {
        List<Waybill> resultList = null;
        PageInfo page = null;
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (map.containsKey("pageNo")) {
            if (map.get("pageNo") != null) {
                pageNo = (Integer) map.get("pageNo");
            }
        }
        if (map.containsKey("pageSize")) {
            if (map.get("pageSize") != null) {
                pageSize = (Integer) map.get("pageSize");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectPlannedWaybill(map);
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public void queryWaybillListToPoPosition(Map map) {
        List<Waybill> list = waybillMapper.selectWaybillByPositionSetting(map);
        if (list != null && list.size() > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Waybill waybill : list) {
                        JSONObject result = GprsLocationBo.getInstance().queryLocation(waybill.getDriverPhone());
                        int resid = result.getInteger("resid");
                        if (resid == 0) {   //正确返回
                            Driver driver = new Driver();
                            driver.setDriverPhone(waybill.getDriverPhone());
                            driver.setCurrentLocation(result.getString("location"));
                            driver.setShortCurrentLocation(result.getString("street"));
                            driverService.updateLocation(driver);
                            logger.info("查询成功");
                        } else if (resid == -80) {    //	余额不足,请充值:请联系客服
                            logger.warn("余额不足,请充值:请联系客服");
                        } else if (resid == -130) {    //用户可能关机
                            logger.warn("用户可能关机");
                        } else {      //对于移动手机，定位失败时运营商返回的结果
                            logger.error("接口返回错误");
                        }
                    }
                }
            }).start();
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
}
