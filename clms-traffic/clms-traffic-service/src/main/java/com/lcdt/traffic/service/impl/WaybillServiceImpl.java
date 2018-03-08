package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.notify.model.TrafficStatusChangeEvent;
import com.lcdt.traffic.dao.SplitGoodsMapper;
import com.lcdt.traffic.dao.WaybillItemsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.ClmsNotifyProducer;
import com.lcdt.traffic.notify.CommonAttachment;
import com.lcdt.traffic.notify.WaybillSenderNotify;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.util.GprsLocationBo;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillDto;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Driver;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.service.CompanyService;
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
    @Reference
    private CustomerRpcService customerRpcService;

    @Autowired
    private PlanService planService;

    @Autowired
    private ClmsNotifyProducer producer;

    @Autowired
    private SplitGoodsMapper splitGoodsMapper;

    @Reference
    private UserService userService;

    @Reference
    private CompanyService companyService;

    @Autowired
    private WaybillSenderNotify waybillSenderNotify;

    @Override
    public Waybill addWaybill(WaybillDto waybillDto) {
        int result = 0;
        Waybill waybill = new Waybill();
        BeanUtils.copyProperties(waybillDto, waybill);

        //计划传过来，判断对应运单有几条数据，然后生成运单编码
        if(waybill.getWaybillPlanId()!=null&&waybill.getWaybillPlanId()>0){
            Map map=new HashMap();
            map.put("companyId",waybill.getCompanyId());
            map.put("waybillPlanId",waybill.getWaybillPlanId());
            List<Waybill> list=waybillMapper.selectWaybillPlanId(map);
            if(list!=null){
                waybill.setWaybillCode(waybill.getWaybillCode()+"-"+(list.size()+1));
            }else {
                waybill.setWaybillCode(waybill.getWaybillCode()+"-1");
            }
        }

        //判断运单编码是否存在（相同企业下的运单编码不能重复）
        if(null!=waybill.getWaybillCode()&&!("").equals(waybill.getWaybillCode())){
            if(isExistWaybillByCodeAndCompanyId(waybill.getWaybillCode(),waybill.getCompanyId())){
                throw new RuntimeException("运单编号已存在!");
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

        waybill=waybillMapper.selectByPrimaryKey(waybill.getId());
        return waybill;
    }

    @Override
    public int deleteWaybill(Long waybillId, Long companyId) {
        return 0;
    }

    @Override
    public int modifyOwnWaybill(WaybillDto waybillDto) {
        int result = 0;
        Waybill waybill = new Waybill();
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

    /****
     * 返回当前登录人企业绑定企业id
     * 1、登录人-指承运人，承运人登录企业ID及登录人所有的组权限；
     * 2、根据上述条件获取登录人对应的客户表中的绑定客户列表；
     * 3、绑定客户条件：绑定ID不为空，客户列表中的企业ID==登录人企业ID
     * 4、运单查询公共条件（创建企业ID条件）
     * 5、Map(companyId--客户创建企业ID)
     * @return
     */
    private Map customerCompanyIds(Map map) {
        Map resultMap = new HashMap();
        map.put("bindCpid", "111");//标识绑定企业ID不为空的企业
        List<Customer> customerList = customerRpcService.findBindCompanyIds(map);
        if (customerList != null && customerList.size() > 0) { //承运人ID
            /****
             * 1、登录人对应客户列表信息（承运人对应的货主列表信息）；
             * 2、遍历该列表信息，根据客户中绑定企业ID（这里指货主）及创建客户的企业ID（客户本身）;
             * 3、再次遍历客户列表找出客户所对应的竞价组信息
             *
             */
            StringBuffer sb = new StringBuffer();
            sb.append("(");
            for (int i = 0; i < customerList.size(); i++) {
                Customer tempObj = customerList.get(i);
                Long ownCompanyId = tempObj.getBindCpid(); //承运商绑定客户企业ID
                sb.append(" find_in_set('" + ownCompanyId + "',company_id)"); //创建计划企业ID
                if (i != customerList.size() - 1) {
                    sb.append(" or ");
                }
            }
            sb.append(")");

            resultMap.put("companyIds", sb.toString()); //计划创建企业
        }
        return resultMap;
    }

    @Override
    public PageInfo queryOwnWaybillList(Map map) {
        List<WaybillDao> resultList = null;
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
        resultList = waybillMapper.selectOwnByCondition(map);
        page = new PageInfo(resultList);

        return page;
    }

    @Override
    public PageInfo queryCustomerWaybillList(Map map) {
        List<WaybillDao> resultList = null;
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

        Map cMapIds = customerCompanyIds(map);
        map.put("companyIds",cMapIds.get("companyIds"));
        map.put("carrierCompanyId",map.get("companyId"));
        map.remove("companyId");
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectCustomerByCondition(map);
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public int modifyOwnWaybillStatus(Map map) {
        int result=0;
        result=waybillMapper.updateOwnWaybillStatus(map);

        //发送消息通知
        modifyOwnWaybillStatusToSendNotify(map);
        //返回计划相关信息
        modifyWaybillPlanInfo(map);
        return result;
    }

    @Override
    public int modifyCustomerWaybillStatus(Map map) {
        int result=0;
        result=waybillMapper.updateCustomerWaybillStatus(map);

        //发送消息通知
        modifyCustomerWaybillStatusToSendNotify(map);
        //返回计划相关信息
        modifyWaybillPlanInfo(map);
        return result;
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
    public int modifyOwnWaybillStatusByWaybillPlanId(Map map) {
        int result=0;
        result=waybillMapper.updateOwnWaybillStatusByWaybillPlanId(map);
        modifyWaybillPlanInfo(map);
        return result;
    }

    @Override
    public void queryWaybillListToPoPosition(Map map) {
        List<Waybill> list=waybillMapper.selectWaybillByPositionSetting(map);
        if(list!=null&&list.size()>0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(Waybill waybill:list){
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

    /**
     * 取消运单时，需要将运单数量还原到派单，运单状态置为取消,运单完成时，判断此plan下的运单是否全部完成，如果是全部完成，则更新计划状态为完成状态
     * @param map
     * @return
     */
    private void modifyWaybillPlanInfo(Map map){
        short waybillStatus=(short)map.get("waybillStatus");
        if(waybillStatus== ConstantVO.WAYBILL_STATUS_HAVE_CANCEL){
            List<WaybillDao> list=waybillMapper.selectWaybillByIdOrPlanId(map);
            if(list!=null&&list.size()>0){
                for(WaybillDao dao:list){
                    List<WaybillItems> itemsList=dao.getWaybillItemsList();
                    for(WaybillItems item:itemsList){
                        //根据派单货物明细id更新派单货物明细数量
                        float amount=item.getAmount();
                        Long splitGoodsDetailId=item.getSplitGoodsDetailId();
                    }
                }

            }

        }
        if(waybillStatus==ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
            List<Waybill> list=waybillMapper.selectWaybillPlanId(map);
            if(list!=null&&list.size()>0){
                for(Waybill waybill:list){
                    map.put("waybillPlanId",waybill.getWaybillPlanId());
                    List<Waybill> waybillList=waybillMapper.selectWaybillByPlanId(map);
                    boolean flag=false;
                    for(Waybill bill:waybillList){
                        if(bill.getWaybillStatus()!=ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
                            flag=true;
                            break;
                        }
                    }
                    if(flag){
                        //此计划下的运单全部完成，根据计划id 更新计划状态为完成
                        WaybillPlan waybillPlan =new WaybillPlan();
                        waybillPlan.setUpdateId((Long)map.get("updateId"));
                        waybillPlan.setUpdateName((String)map.get("updateName"));
                        waybillPlan.setUpdateTime(new Date());
                        waybillPlan.setPlanStatus(ConstantVO.PLAN_STATUS_COMPLETED);
                        waybillPlan.setWaybillPlanId(waybill.getWaybillPlanId());
                        planService.updatePlanStatusByWaybill(waybillPlan);
                    }
                }
            }
        }
    }

    private boolean isExistWaybillByCodeAndCompanyId(String waybillCode , Long companyId){
        Map map=new HashMap();
        map.put("waybillCode",waybillCode);
        map.put("companyId",companyId);
        List<Waybill> waybillList=waybillMapper.selectByCodeAndCompanyId(map);
        if(waybillList!=null&&waybillList.size()>0){
            return true;
        }else {
            return false;
        }
    }
    //我的运单状态更改时，发送的消息
    private void modifyOwnWaybillStatusToSendNotify(Map map){
        short status=(short)map.get("waybillStatus");
        String waybillIds=(String)map.get("waybillIds");
        Long userId=(Long)map.get("updateId");
        Long companyId=(Long) map.get("companyId");

        //门卫入厂
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_FACTORY) {
            waybillSenderNotify.enterFactorySenderNotify(waybillIds,companyId,userId);
        }
        //装车完成
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_LOADING){
            waybillSenderNotify.haveLoadingSendNotify(waybillIds,companyId,userId);
        }

        //门卫出厂
        if(status==ConstantVO.WAYBILL_STATUS_IN_TRANSIT){
            waybillSenderNotify.transitSendNotify(waybillIds,companyId,userId);
        }
        //我的运单 已完成
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
            waybillSenderNotify.ownFinishSendNotify(waybillIds,companyId,userId);
        }
        //我的运单 已取消
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_CANCEL){
            waybillSenderNotify.ownCancelSendNotify(waybillIds,companyId,userId);
        }



    }

    //我的运单状态更改时，发送的消息
    private void modifyCustomerWaybillStatusToSendNotify(Map map){
        short status=(short)map.get("waybillStatus");
        String waybillIds=(String)map.get("waybillIds");
        Long userId=(Long)map.get("updateId");
        Long companyId=(Long) map.get("companyId");

        //承运商卸货
        if(status==ConstantVO.WAYBILL_STATUS_IS_UNLOADING) {
            waybillSenderNotify.customerUnloadingSendNotify(waybillIds,companyId,userId);
        }
        //我的运单 已完成
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
            waybillSenderNotify.customerFinishSendNotify(waybillIds,companyId,userId);
        }
        //我的运单 已取消
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_CANCEL){
            waybillSenderNotify.customerCancelSendNotify(waybillIds,companyId,userId);
        }

    }

}
