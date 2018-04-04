package com.lcdt.traffic.rpc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.SplitGoodsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.dto.*;
import com.lcdt.traffic.model.*;
import com.lcdt.traffic.notify.WaybillSenderNotify;
import com.lcdt.traffic.service.PlanService;
import com.lcdt.traffic.service.SplitGoodsService;
import com.lcdt.traffic.service.WaybillRpcService;
import com.lcdt.traffic.service.impl.CustomerCompanyIds;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.util.ClmsBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by lyqishan on 2018/3/19
 */
@Service
public class WaybillRcpServiceImp implements WaybillRpcService {

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

    @Reference
    private CustomerRpcService customerRpcService;

    @Reference
    private CompanyService companyService;

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
        PageHelper.startPage(pageNo, pageSize);
        Map map= ClmsBeanUtil.beanToMap(dto);
        resultList = waybillMapper.selectOwnByCondition(map);
        page = new PageInfo(resultList);

        return page;
    }

    @Override
    public PageInfo queryCustomerWaybillList(WaybillCustListParamsDto dto) {
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
        Map map= ClmsBeanUtil.beanToMap(dto);
        Map cMapIds = customerCompanyIds.getCustomerCompanyIds(map);
        map.put("companyIds", cMapIds.get("companyIds"));
        map.put("carrierCompanyId", map.get("companyId"));
        map.remove("companyId");
        map.remove("customerName");
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectCustomerByCondition(map);
        for (int i = 0; i < resultList.size(); i++) {
            Customer customer=customerRpcService.queryCustomer(resultList.get(i).getCarrierCompanyId(),resultList.get(i).getCompanyId());
            resultList.get(i).setWaybillSource(customer.getCustomerName());
        }
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public int modifyOwnWaybillStatus(WaybillModifyStatusDto dto) {
        int result=0;
        Map map=ClmsBeanUtil.beanToMap(dto);
        result=waybillMapper.updateOwnWaybillStatus(map);

        //发送消息通知
        modifyOwnWaybillStatusToSendNotify(map);
        //返回计划相关信息
        modifyWaybillPlanInfo(map);
        return result;
    }

    @Override
    public int modifyCustomerWaybillStatus(WaybillModifyStatusDto dto) {
        int result=0;
        Map map=ClmsBeanUtil.beanToMap(dto);
        result=waybillMapper.updateCustomerWaybillStatus(map);

        //发送消息通知
        modifyCustomerWaybillStatusToSendNotify(map);
        //返回计划相关信息
        modifyWaybillPlanInfo(map);
        return result;
    }

    @Override
    public int modifyOwnWaybillReceipt(WaybillModifyReceiptDto dto) {
        int result=0;
        Map map=ClmsBeanUtil.beanToMap(dto);
        result=waybillMapper.updateOwnWaybillStatus(map);
        return result;
    }

    @Override
    public int modifyCustomerWaybillReceipt(WaybillModifyReceiptDto dto) {
        int result=0;
        Map map=ClmsBeanUtil.beanToMap(dto);
        result=waybillMapper.updateCustomerWaybillStatus(map);
        //发送消息通知
        waybillSenderNotify.customerReceiptSendNotify(dto.getWaybillIds(),dto.getUpdateId());
        return result;
    }


    @Override
    public int modifyOwnWaybillStatusByWaybillPlanId(Map map) {
        int result=0;
        result=waybillMapper.updateOwnWaybillStatusByWaybillPlanId(map);
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
        map.put("waybillStatus", dto.getWaybillStatus());
        PageHelper.startPage(pageNo, pageSize);
        resultList = waybillMapper.selectDriverByCondition(map);
        for (int i = 0; i < resultList.size(); i++) {
            Company  company=companyService.selectById(resultList.get(i).getCompanyId());
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
        int result = waybillMapper.updateWaybillByDriver(map);
        if (result > 0) {
            List<WaybillDao> resultList = waybillMapper.selectWaybillByWaybillIds(dto.getWaybillIds().toString());
            waybill = resultList.get(0);
        } else {
            throw new RuntimeException("修改失败");
        }
        //司机卸货，发送通知
        waybillSenderNotify.driverUnloadinSendNotify(dto.getWaybillIds().toString(),dto.getUpdateId());

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
        waybillSenderNotify.driverReceiptSendNotify(dto.getWaybillIds().toString(),dto.getUpdateId());

        return waybill;
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
                    List<SplitGoodsDetail> splitGoodsDetailList=new ArrayList<SplitGoodsDetail>();
                    for(WaybillItems item:itemsList){
                        //根据派单货物明细id更新派单货物明细数量
                        SplitGoodsDetail sp=new SplitGoodsDetail();
                        float amount=item.getAmount();
                        Long splitGoodsDetailId=item.getSplitGoodsDetailId();
                        sp.setUpdateId((Long)map.get("updateId"));
                        sp.setUpdateName((String)map.get("updateName"));
                        sp.setUpdateTime(new Date());
                        sp.setRemainAmount(amount);
                        sp.setSplitGoodsDetailId(splitGoodsDetailId);
                        splitGoodsDetailList.add(sp);
                    }
                    splitGoodsService.waybillCancel4SplitGoods(splitGoodsDetailList, dao.getWaybillPlanId());

                }

            }

        }
        if(waybillStatus==ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
            List<Waybill> list=waybillMapper.selectWaybillPlanId(map);
            if(list!=null&&list.size()>0){
                for(Waybill waybill:list){
                    map.put("waybillPlanId",waybill.getWaybillPlanId());
                    List<Waybill> waybillList=waybillMapper.selectWaybillByPlanId(map);
                    boolean flag=true;
                    for(Waybill bill:waybillList){
                        if(bill.getWaybillStatus()!=ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
                            flag=false;
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
                        waybillPlan.setFinishDate(new Date());
                        planService.updatePlanStatusByWaybill(waybillPlan);
                    }
                }
            }
        }
    }

    //我的运单状态更改时，发送的消息
    private void modifyOwnWaybillStatusToSendNotify(Map map){
        short status=(short)map.get("waybillStatus");
        String waybillIds=(String)map.get("waybillIds");
        Long userId=(Long)map.get("updateId");
        Long companyId=(Long) map.get("companyId");

        //门卫入厂
        if(status== ConstantVO.WAYBILL_STATUS_HAVE_FACTORY) {
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

    //客户运单状态更改时，发送的消息
    private void modifyCustomerWaybillStatusToSendNotify(Map map){
        short status=(short)map.get("waybillStatus");
        String waybillIds=(String)map.get("waybillIds");
        Long userId=(Long)map.get("updateId");

        //客户运单 承运商卸货
        if(status==ConstantVO.WAYBILL_STATUS_IS_UNLOADING) {
            waybillSenderNotify.customerUnloadingSendNotify(waybillIds,userId);
        }
        //客户运单 已完成
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_FINISH){
            waybillSenderNotify.customerFinishSendNotify(waybillIds,userId);
        }
        //客户运单 已取消
        if(status==ConstantVO.WAYBILL_STATUS_HAVE_CANCEL){
            waybillSenderNotify.customerCancelSendNotify(waybillIds,userId);
        }

    }

}
