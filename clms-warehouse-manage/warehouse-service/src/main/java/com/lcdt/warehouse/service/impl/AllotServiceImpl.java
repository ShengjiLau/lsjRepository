package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.userinfo.rpc.GroupWareHouseRpcService;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.entity.*;
import com.lcdt.warehouse.mapper.*;
import com.lcdt.warehouse.service.*;
import com.lcdt.warehouse.vo.ConstantVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liz on 2018/5/8.
 */
@Service
public class AllotServiceImpl implements AllotService{
    @Autowired
    private AllotMapper allotMapper;
    @Autowired
    private AllotProductMapper allotProductMapper;
    @Autowired
    private WarehousseMapper warehousseMapper;
    @Autowired
    private OutWarehouseOrderMapper outWarehouseOrderMapper;
    @Autowired
    private InWarehouseOrderMapper inWarehouseOrderMapper;
    @Autowired
    OutOrderGoodsInfoService outOrderGoodsInfoService;
    @Autowired
    InorderGoodsInfoService inorderGoodsInfoService;
    @Autowired
    OutWarehouseOrderService outWarehouseOrderService;
    @Autowired
    InWarehouseOrderService inWarehouseOrderService;
    @Autowired
    InventoryService inventoryService;
    @Reference
    GroupWareHouseRpcService groupWareHouseRpcService;
    @Autowired
    GoodsInfoMapper goodsInfoMapper;
    @Autowired
    InventoryMapper inventoryMapper;
    @Autowired
    InventoryLogService inventoryLogService;

    @Override
    public Page<AllotDto> allotDtoList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有
        if (m.containsKey("pageNo")) {
            if (m.get("pageNo") != null) {
                pageNo = (Integer) m.get("pageNo");
            }
        }
        if (m.containsKey("pageSize")) {
            if (m.get("pageSize") != null) {
                pageSize = (Integer) m.get("pageSize");
            }
        }
        Page<AllotDto> page = new Page<>(pageNo, pageSize);
        return page.setRecords(allotMapper.selectByCondition(page, m));
    }

    @Override
    public boolean addAllot(AllotDto dto) {
        try{
            Allot allot = new Allot();
            BeanUtils.copyProperties(dto, allot); //复制对象属性
            allotMapper.insert(allot);
            if(dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
                //设置商品的合同id
                for (AllotProduct allotProduct : dto.getAllotProductList()) {
                    allotProduct.setAllotId(allot.getAllotId());
                }
                allotProductMapper.insertBatch(dto.getAllotProductList());  //批量插入商品
            }
            //新增出库单
            saveOutWarehouseOrder(allot, dto);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modifyAllot(AllotDto dto) {
        try{
            Allot allot = new Allot();
            BeanUtils.copyProperties(dto, allot); //复制对象属性

            //得到修改之前调拨单
//            Allot oldAllot = allotMapper.selectByPrimaryKey(allot.getAllotId());

            allotMapper.updateByPrimaryKeySelective(allot);
            //获取该调拨单下面的商品apId用来匹配被删除的商品
            List<Long> apIdList = allotProductMapper.selectApIdsByAllotId(allot.getAllotId());
            List<AllotProduct> list1 = new ArrayList<>();//商品新增list
            List<AllotProduct> list2 = new ArrayList<>();//商品修改list
            if (dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
                for (AllotProduct d : dto.getAllotProductList()) {  //迭代根据allotId来区分是新增还是插入
                    d.setAllotId(dto.getAllotId());    //设置allotId
                    if (d.getApId() == null) {   //没有主键的则为新增
                        list1.add(d);
                    } else {
                        list2.add(d);

                        if(apIdList != null && apIdList.size() > 0) {
                            Iterator<Long> it = apIdList.iterator();
                            while(it.hasNext()){
                                Long ovcId = it.next();
                                if(ovcId.longValue()==d.getApId().longValue()){
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                if (list1.size() > 0) {
                    allotProductMapper.insertBatch(list1);  //批量插入商品
                }
                if (list2.size() > 0) {
                    allotProductMapper.updateBatch(list2); //批量更新商品
                }
                if(apIdList.size()>0){
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("apIds",apIdList);
                    allotProductMapper.deleteByBatch(params);//批量删除商品（is_deleted=1）
                }
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean cancelAllot(Long allotId) {
        try{
            Allot allot = allotMapper.selectByPrimaryKey(allotId);
            allot.setAllotStatus((short)2);//取消
            allotMapper.updateByPrimaryKey(allot);
            AllotDto dto = allotMapper.selectByAllotId(allotId);
            //新增入库单
            saveInWarehouseOrder(allot, dto);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public AllotDto getAllotInfo(Long allotId) {
        AllotDto dto = allotMapper.selectByAllotId(allotId);
        return dto;
    }

    @Override
    public boolean allotPutInStorage(AllotDto dto) {
        try{
            Allot allot = new Allot();
            BeanUtils.copyProperties(dto, allot); //复制对象属性
            allot.setAllotStatus((short)3);
            allotMapper.updateByPrimaryKeySelective(allot);

            if (dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
                allotProductMapper.updateBatch(dto.getAllotProductList());
            }
            //新增入库单
            saveInWarehouseOrder(allot, dto);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //新增出库单
    public void saveOutWarehouseOrder(Allot allot, AllotDto dto){
        OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
        outWarehouseOrder.setOrderStatus(ConstantVO.OUT_ORDER_STATUS_HAVE_OUTBOUND);
        outWarehouseOrder.setGroupId(allot.getGroupId());
        outWarehouseOrder.setGroupName(groupWareHouseRpcService.selectByGroupId(allot.getGroupId()).getGroupName());
        outWarehouseOrder.setCustomerId(allot.getCustomerId());
        outWarehouseOrder.setCustomerName(allot.getCustomerName());
        outWarehouseOrder.setCustomerContactName(allot.getContactName());
        outWarehouseOrder.setCustomerContactPhone(allot.getPhoneNum());
        outWarehouseOrder.setWarehouseId(allot.getWarehouseOutId());
        outWarehouseOrder.setWarehouseName(warehousseMapper.selectByPrimaryKey(allot.getWarehouseOutId()).getWhName());
        outWarehouseOrder.setOutboundType("05");//其他出库
        outWarehouseOrder.setOutboundPlanTime(allot.getAllotOutTime());
        outWarehouseOrder.setOutboundTime(allot.getAllotOutTime());
        outWarehouseOrder.setOutboundRemark(allot.getRemark());
        outWarehouseOrder.setOutboundMan(allot.getCreateName());
        outWarehouseOrder.setCompanyId(allot.getCompanyId());
        outWarehouseOrder.setCreateId(allot.getCreateId());
        outWarehouseOrder.setCreateName(allot.getCreateName());
        outWarehouseOrder.setCreateDate(new Date());
        outWarehouseOrder.setIsDeleted(0);
        outWarehouseOrderMapper.insertOutWarehouseOrder(outWarehouseOrder);
        //插入出库单商品
        if (dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
            List<OutOrderGoodsInfo> outOrderGoodsInfoList = new ArrayList<>();
            for (AllotProduct allotProduct : dto.getAllotProductList()) {
                OutOrderGoodsInfo outOrderGoodsInfo = new OutOrderGoodsInfo();
                outOrderGoodsInfo.setOutorderId(outWarehouseOrder.getOutorderId());
                outOrderGoodsInfo.setInvertoryId(allotProduct.getInventoryId());
                outOrderGoodsInfo.setGoodsId(allotProduct.getGoodsId());
                outOrderGoodsInfo.setGoodsName(allotProduct.getName());
                outOrderGoodsInfo.setGoodsCode(allotProduct.getCode());
                outOrderGoodsInfo.setGoodsBarCode(allotProduct.getBarCode());
                outOrderGoodsInfo.setGoodsSpec(allotProduct.getSpec());
                outOrderGoodsInfo.setGoodsClassifyName(allotProduct.getGoodsClassify());
                outOrderGoodsInfo.setMinUnit(allotProduct.getUnit());
                outOrderGoodsInfo.setUnit(allotProduct.getUnit());
                outOrderGoodsInfo.setUnitData(1);
                outOrderGoodsInfo.setBatch(allotProduct.getBatchNum());
                outOrderGoodsInfo.setStorageLocationId(allotProduct.getWarehouseLocId());
                outOrderGoodsInfo.setStorageLocationCode(allotProduct.getWarehouseLocCode());
                outOrderGoodsInfo.setGoodsNum(allotProduct.getAllotNum());
                outOrderGoodsInfo.setOutboundQuantity(allotProduct.getAllotNum());
                outOrderGoodsInfo.setRemark(allotProduct.getRemark());
                outOrderGoodsInfo.setCompanyId(outWarehouseOrder.getCompanyId());
                outOrderGoodsInfoList.add(outOrderGoodsInfo);

                //减库存（增加锁定库存）
                inventoryService.lockInventoryNum(outOrderGoodsInfo.getInvertoryId(),outOrderGoodsInfo.getGoodsNum());
            }
            //批量插入出库单明细
            outOrderGoodsInfoService.insertBatch(outOrderGoodsInfoList);

            //对接库存，减库存
            outWarehouseOrder = outWarehouseOrderService.queryOutWarehouseOrder(outWarehouseOrder.getCompanyId(),outWarehouseOrder.getOutorderId());
            inventoryService.outInventory(outWarehouseOrder,outOrderGoodsInfoList);
        }
    }
    //新增入库单
    public void saveInWarehouseOrder(Allot allot, AllotDto dto){
        InWarehouseOrder inWarehouseOrder = new InWarehouseOrder();
        inWarehouseOrder.setInOrderStatus(ConstantVO.IN_ORDER_STATUS_HAVE_STORAGE);
        inWarehouseOrder.setGroupId(allot.getGroupId());
        inWarehouseOrder.setGroupName(groupWareHouseRpcService.selectByGroupId(allot.getGroupId()).getGroupName());
        inWarehouseOrder.setCustomerId(allot.getCustomerId());
        inWarehouseOrder.setCustomerName(allot.getCustomerName());
        inWarehouseOrder.setCustomerContactName(allot.getContactName());
        inWarehouseOrder.setCustomerContactPhone(allot.getPhoneNum());
        inWarehouseOrder.setWarehouseId(allot.getWarehouseInId());
        inWarehouseOrder.setWarehouseName(warehousseMapper.selectByPrimaryKey(allot.getWarehouseInId()).getWhName());
        inWarehouseOrder.setStorageType("05");//其他入库
        inWarehouseOrder.setStoragePlanTime(allot.getAllotOutTime());
        inWarehouseOrder.setStorageTime(allot.getAllotOutTime());
        inWarehouseOrder.setStorageRemark(allot.getRemark());
        inWarehouseOrder.setCompanyId(allot.getCompanyId());
        inWarehouseOrder.setCreateId(allot.getCreateId());
        inWarehouseOrder.setCreateName(allot.getCreateName());
        inWarehouseOrder.setCreateDate(new Date());
        inWarehouseOrder.setStorageMan(inWarehouseOrder.getCreateName());
        inWarehouseOrderMapper.insertInWarehouseOrder(inWarehouseOrder);
        //插入出库单商品
        if (dto.getAllotProductList() != null && dto.getAllotProductList().size() > 0) {
            List<InorderGoodsInfo> inorderGoodsInfoList = new ArrayList<>();
            for (AllotProduct allotProduct : dto.getAllotProductList()) {
                InorderGoodsInfo inorderGoodsInfo = new InorderGoodsInfo();
                inorderGoodsInfo.setInorderId(inWarehouseOrder.getInorderId());
                inorderGoodsInfo.setGoodsId(allotProduct.getGoodsId());
                inorderGoodsInfo.setGoodsName(allotProduct.getName());
                inorderGoodsInfo.setGoodsCode(allotProduct.getCode());
                inorderGoodsInfo.setGoodsBarcode(allotProduct.getBarCode());
                inorderGoodsInfo.setGoodsSpec(allotProduct.getSpec());
                inorderGoodsInfo.setGoodsClassify(allotProduct.getGoodsClassify());
                inorderGoodsInfo.setMinUnit(allotProduct.getUnit());
                inorderGoodsInfo.setUnit(allotProduct.getUnit());
                inorderGoodsInfo.setUnitData(1);
                inorderGoodsInfo.setReceivalbeAmount(allotProduct.getAllotNum());
                inorderGoodsInfo.setBatch(allotProduct.getBatchNum());
                inorderGoodsInfo.setStorageLocationId(allotProduct.getWarehouseLocId());
                inorderGoodsInfo.setStorageLocationCode(allotProduct.getWarehouseLocCode());
                inorderGoodsInfo.setInHouseAmount(allotProduct.getAllotNum());
                inorderGoodsInfo.setRemark(allotProduct.getRemark());
                inorderGoodsInfo.setSplit(false);
                inorderGoodsInfo.setCompanyId(inWarehouseOrder.getCompanyId());
                inorderGoodsInfoList.add(inorderGoodsInfo);

                Inventory inventory = inventoryMapper.selectById(allotProduct.getInventoryId());
                //加库存
                Float invertoryNum = inventory.getInvertoryNum() + inorderGoodsInfo.getInHouseAmount();
                inventory.setInvertoryNum(invertoryNum);
                inventoryMapper.updateById(inventory);
                //写入库流水
                inventory.setInvertoryNum(inorderGoodsInfo.getInHouseAmount());
                inventoryLogService.saveInOrderLog(inWarehouseOrder, inventory, invertoryNum);
            }
            //批量插入入库单明细
            inorderGoodsInfoService.insertBatch(inorderGoodsInfoList);
        }
    }
}
