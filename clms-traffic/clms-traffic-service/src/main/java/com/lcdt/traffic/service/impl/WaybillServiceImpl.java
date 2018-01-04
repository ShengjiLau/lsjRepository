package com.lcdt.traffic.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.traffic.dao.WaybillItemsMapper;
import com.lcdt.traffic.dao.WaybillMapper;
import com.lcdt.traffic.model.Waybill;
import com.lcdt.traffic.model.WaybillDao;
import com.lcdt.traffic.model.WaybillItems;
import com.lcdt.traffic.service.WaybillService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.WaybillDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2017/12/20
 */
@Transactional
@Service
public class WaybillServiceImpl implements WaybillService {
    @Autowired
    private WaybillMapper waybillMapper; //运单
    @Autowired
    private WaybillItemsMapper waybillItemsMapper; //运单货物详细
    @Reference
    private CustomerRpcService customerRpcService;

    @Override
    public int addWaybill(WaybillDto waybillDto) {
        int result = 0;
        Waybill waybill = new Waybill();
        BeanUtils.copyProperties(waybillDto, waybill);
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
        return result;
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
        modifySplitGoodsAmount((short) map.get("waybillStatus"));
        return waybillMapper.updateOwnWaybillStatus(map);
    }

    @Override
    public int modifyCustomerWaybillStatus(Map map) {
        modifySplitGoodsAmount((short) map.get("waybillStatus"));
        return waybillMapper.updateCustomerWaybillStatus(map);
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
        modifySplitGoodsAmount((short) map.get("waybillStatus"));
        return waybillMapper.updateOwnWaybillStatusByWaybillPlanId(map);
    }

    /**
     * 取消运单时，需要将运单数量还原到派单，运单状态置为取消
     * @param waybillStatus
     * @return
     */
    private int modifySplitGoodsAmount(short waybillStatus){
        if(waybillStatus== ConstantVO.WAYBILL_STATUS_HAVE_CANCEL){
            return 1;
        }else{
            return 0;
        }
    }

}
