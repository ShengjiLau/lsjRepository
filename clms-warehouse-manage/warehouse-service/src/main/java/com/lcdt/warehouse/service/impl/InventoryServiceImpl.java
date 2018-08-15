package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.warehouse.contants.InOrderStatus;
import com.lcdt.warehouse.contants.OutOrderStatus;
import com.lcdt.warehouse.dto.AllotDto;
import com.lcdt.warehouse.dto.ImportInventoryDto;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.*;
import com.lcdt.warehouse.factory.InventoryFactory;
import com.lcdt.warehouse.mapper.GoodsInfoMapper;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.mapper.OutWarehouseOrderMapper;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.lcdt.warehouse.service.InventoryLogService;
import com.lcdt.warehouse.service.InventoryService;
import com.lcdt.warehouse.service.WarehouseService;
import com.lcdt.warehouse.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ss
 * @since 2018-05-07
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

    @Autowired
    InventoryMapper inventoryMapper;

    @Reference()
    SubItemsInfoService goodsService;

    @Autowired
    private InventoryLogService logService;

    private Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Autowired
    private WarehouseService warehouseService;

    //分页查询 库存列表
    public Page<Inventory> queryInventoryPage(InventoryQueryDto inventoryQueryDto,Long companyId) {
        logger.info("查询库存列表 参数 ：{}",inventoryQueryDto);
        List<Long> goodsId = queryGoodsIds(inventoryQueryDto, companyId);
        Page<Inventory> page = new Page<>(inventoryQueryDto.getPageNo(), inventoryQueryDto.getPageSize());
        if (CollectionUtils.isEmpty(goodsId)) {
            return page;
        }
        List<Inventory> inventories = inventoryMapper.selectInventoryListByqueryDto(goodsId, page, inventoryQueryDto);
        queryGoodsInfo(companyId, inventories);
        return page.setRecords(inventories);
    }

    public List<Inventory> queryAllInventory(Long companyId,Long wareHouseId,Long goodsId) {
        InventoryQueryDto inventoryQueryDto = new InventoryQueryDto();
        inventoryQueryDto.setWareHouseId(wareHouseId);
        inventoryQueryDto.setCompanyId(companyId);
        ArrayList arrayList = new ArrayList();
        arrayList.add(goodsId);
        List<Inventory> inventories = inventoryMapper.selectInventoryListByqueryDto(arrayList, inventoryQueryDto);
        return inventories;
    }


    private void queryGoodsInfo(Long companyId, List<Inventory> inventories) {
        logger.info("查询 库存 关联 商品信息 {}",inventories);
        List<Long> longs = inventories.stream().map(inventory -> inventory.getOriginalGoodsId()).collect(Collectors.toList());
        logger.info("批量查询商品id {}",longs);
        GoodsListParamsDto dto = new GoodsListParamsDto(longs,companyId);
        PageInfo<GoodsInfoDao> listPageInfo = goodsService.queryByCondition(dto);
        logger.debug("商品查询结果 {}",listPageInfo.getList());
        fillInventoryGoods(inventories, listPageInfo.getList());
    }

    public static void fillInventoryGoods(List<Inventory> inventories, List<GoodsInfoDao> list) {
        inventories.stream().
                forEach(inventory -> list.stream()
                        .filter(goods -> inventory.getOriginalGoodsId().equals(goods.getSubItemId())).findFirst().ifPresent(goodsInfoDao -> inventory.setGoods(goodsInfoDao)));
    }

    private List<Long> queryGoodsIds(InventoryQueryDto inventoryQueryDto, Long companyId) {
        GoodsListParamsDto dto = new GoodsListParamsDto();
        dto.setClassifyName(inventoryQueryDto.getGoodsCategory());
        dto.setGoodsName(inventoryQueryDto.getGoodsName());
        dto.setCompanyId(companyId);
        dto.setGoodsCode(inventoryQueryDto.getGoodsCode());
        dto.setBarCode(inventoryQueryDto.getGoodsBarCode());
        dto.setClassifyId(inventoryQueryDto.getClassifyId());
        List<Long> longs = goodsService.queryGoodsIdsByCondition(dto);
        if (longs == null) {
            return new ArrayList<Long>();
        }
        return longs;
    }


    @Autowired
    InWarehouseOrderService inOrderService;

    /**
     * 入库操作
     *
     * @param goods
     * @param order
     */
    @Transactional(rollbackFor = Exception.class)
    public void putInventory(List<InorderGoodsInfo> goods, InWarehouseOrder order) {
        Assert.notNull(goods, "入库货物不能为空");
        logger.info("入库操作开始 入库单：{}", order);
        for (InorderGoodsInfo good : goods) {
            Inventory inventory = InventoryFactory.createInventory(order, good);
            GoodsInfo goodsInfo = saveGoodsInfo(good);
            inventory.setGoodsId(goodsInfo.getGoodsId());
            //写入库流水
            Inventory updatedInventory = addInventory(inventory);
            logService.saveInOrderLog(order, inventory,updatedInventory.getInvertoryNum());
        }
        order.setInOrderStatus(InOrderStatus.ENTERED);
        inOrderService.updateById(order);
        logger.info("入库成功 入库单：{}", order);
    }

    @Override
    public Inventory modifyInventoryPrice(Long inventoryId, Double newprice) {
        Inventory inventory = baseMapper.selectById(inventoryId);
        if (inventory == null) {
            return new Inventory();
        }
        inventory.setInventoryPrice(newprice);
        baseMapper.updateById(inventory);
        return inventory;
    }

    @Override
    public Inventory modifyInventoryRemark(Long inventoryId, String remark) {
        Inventory inventory = baseMapper.selectById(inventoryId);
        if (inventory == null) {
            return new Inventory();
        }
        inventory.setCostRemark(remark);
        baseMapper.updateById(inventory);
        return inventory;
    }

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    public GoodsInfo saveGoodsInfo(InorderGoodsInfo inOrdergoodsInfo) {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(inOrdergoodsInfo, goodsInfo);
        goodsInfo.setGoodsId(null);
        goodsInfoMapper.insert(goodsInfo);
        return goodsInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void lockInventoryNum(Long inventoryId,Double tryLockNum){
        Assert.notNull(tryLockNum, "不能为空");
        Inventory inventory = selectById(inventoryId);
        if (inventory == null) {
            throw new RuntimeException("库存信息不存在");
        }
        if (inventory.getavailableNum() < tryLockNum) {
            throw new RuntimeException("锁定库存量不能大于库存剩余数量");
        }
        inventory.setLockNum(inventory.getLockNum() + tryLockNum);
        updateById(inventory);
    }

    @Transactional(rollbackFor = Exception.class)
    public void unLockInventoryNum(Long inventoryId, Double unlockNum) {
        Assert.notNull(unlockNum, "不能为空");
        Inventory inventory = selectById(inventoryId);

        if (inventory.getLockNum() < unlockNum) {
            throw new RuntimeException("解锁库存量不能大于 已锁量");
        }
        inventory.setLockNum(CommonUtils.sub(inventory.getLockNum(),unlockNum));
        updateById(inventory);
    }

    @Autowired
    private OutWarehouseOrderMapper outOrderMapper;

    /**
     * 出库操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void outInventory(OutWarehouseOrder order,List<OutOrderGoodsInfo> goodsInfos) {
        Assert.notNull(goodsInfos, "出库货物不能为空");
        logger.info("出库操作开始 出库单：{}", order);
        if (order.isOut()) {
            logger.warn("出库操作错误 出库单已出库：{}", order);
            return;
        }

        for (OutOrderGoodsInfo good : goodsInfos) {
            Inventory inventory = inventoryMapper.selectById(good.getInvertoryId());
            //扣减库存
            //比较锁定量和 出库量
            if (good.getOutboundQuantity() == null) {
                logger.warn("出库失败 出库数量为空:{}",inventory);
                continue;
            }
            if (good.getGoodsNum() == null) {
                logger.warn("出库失败 出库计划数量为空{}",inventory);
                continue;
            }

            if (inventory.getLockNum() >= good.getOutboundQuantity()) {
                inventory.setLockNum(CommonUtils.sub(inventory.getLockNum(),good.getGoodsNum()));
                inventory.setInvertoryNum(CommonUtils.sub(inventory.getInvertoryNum(),good.getOutboundQuantity()));
                inventoryMapper.updateById(inventory);
                logService.saveOutOrderLog(order, good,inventory.getInvertoryNum(),inventory);
                order.setOrderStatus(OutOrderStatus.OUTED);
                outOrderMapper.updateById(order);
                logger.info("出库单 出库成功 {}",order);
            }else{
                throw new RuntimeException("出库失败，库存锁定量 小于 出库量");
            }
        }
    }



    /**
     * 新增库存
     *
     * @param inventory
     * @return
     */
    @Override
    public Inventory addInventory(Inventory inventory) {
        List<Inventory> inventories = querySameInventory(inventory);
        if (inventories.isEmpty()) {
            //没有库存 新增
            logger.info("入库 新增库存:{}", inventory);
            inventoryMapper.insert(inventory);
            return inventory;
        }
        Inventory existInventory = inventories.get(0);
        existInventory.setInvertoryNum(existInventory.getInvertoryNum() + inventory.getInvertoryNum());
        updateInventoryPrice(existInventory,inventory);
        inventoryMapper.updateById(existInventory);
        logger.info("入库更新库存数量：{}", existInventory);
        return existInventory;
    }



    private void updateInventoryPrice(Inventory existInventory, Inventory inventory) {
        //EMPTY
    }


    /**
     * 查找是否有相同库存
     *
     * @param inventory
     * @return
     */
    public List<Inventory> querySameInventory(Inventory inventory) {
        List<Inventory> inventories = inventoryMapper.selectInventoryList(new Page<Inventory>(), inventory);
        if (inventories == null) {
            return new ArrayList<>();
        }
        return inventories;
    }


    //盘库更新库存
    @Transactional(rollbackFor = Exception.class)
    public void updateInventoryByCheck(TCheck tCheck,List<TCheckItem> items){
        for (TCheckItem checkItem : items) {
            Inventory inventory = selectById(checkItem.getInvertoryId());
            if (inventory == null) {
                throw new RuntimeException("库存不存在，请查看后重试");
            }
            System.out.println("inventory.getLockNum():"+inventory.getLockNum());
            if (inventory.getLockNum() != null && inventory.getLockNum().floatValue() > 0) {
                throw new RuntimeException("库存已锁定，无法盘点");
            }

            inventory.setInvertoryNum(checkItem.getCheckAmount());
            updateById(inventory);
            logService.savePankuLog(tCheck, checkItem);
            checkItem.getInvertoryId();
        }

    }
    
    /**
     * 取消调拨时需要重新更新库存
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateInventoryByAllot(InWarehouseOrder inWarehouseOrder,AllotDto allotDto) {
    	List<AllotProduct> allotProductList = allotDto.getAllotProductList();
    	for (AllotProduct allotProduct : allotProductList) {
    		Inventory inventory = selectById(allotProduct.getInventoryId());
    		inventory.setInvertoryNum(inventory.getInvertoryNum()+allotProduct.getAllotNum());
    		updateById(inventory);
    		Inventory inventory2 = new Inventory();
    		BeanUtils.copyProperties(inventory, inventory2);
    		inventory2.setInvertoryNum(allotProduct.getAllotNum());
    		logService.saveInOrderLog(inWarehouseOrder,inventory2,inventory.getInvertoryNum());
    	}

    }
    
    /**
     * 调拨入库时变更库存且生成库存流水
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateInventoryByAllotAndInwarehouseOrder(InWarehouseOrder inWarehouseOrder,AllotDto allotDto) {
    	List<AllotProduct> allotProductList = allotDto.getAllotProductList();
    	for (AllotProduct allotProduct : allotProductList) {
    			Inventory inventory = new Inventory();
    			inventory.setWarehouseId(allotDto.getWarehouseInId());
    			inventory.setBusinessDesc(inWarehouseOrder.getInOrderCode());
    			inventory.setBaseUnit(allotProduct.getUnit());
    			inventory.setBatch(allotProduct.getBatchNum());
    			inventory.setCompanyId(inWarehouseOrder.getCompanyId());
    			inventory.setCustomerId(inWarehouseOrder.getCustomerId());
    			inventory.setCustomerName(inWarehouseOrder.getCustomerName());
    			inventory.setGoodsId(allotProduct.getGoodsId());
    			inventory.setInvertoryNum(allotProduct.getAllotNum());
    			inventory.setLockNum(0.0D);
    			inventory.setOriginalGoodsId(allotProduct.getOriginalGoodsId());
    			inventory.setRemark(allotProduct.getRemark());
    			inventory.setStorageLocationCode(allotProduct.getWarehouseLocCode());
    			inventory.setStorageLocationId(allotProduct.getWarehouseLocId());
    			inventory.setWarehouseName(allotDto.getWarehouseInName());
    			addInventory(inventory);
    			List<Inventory> inventoryList = querySameInventory(inventory);
    			if (null != inventoryList && 0 != inventoryList.size()) {
    				Inventory inventory2 = new Inventory();
        			BeanUtils.copyProperties(inventory, inventory2);
        		    inventory2.setInvertoryNum(allotProduct.getAllotNum());
        		    logService.saveInOrderLog(inWarehouseOrder,inventory2,inventory.getInvertoryNum());
    			}else {
    				 logService.saveInOrderLog(inWarehouseOrder,inventory,inventory.getInvertoryNum());
    			}
    	}
    }



    @Transactional(rollbackFor = Exception.class)
    public List<Inventory> importInventory(List<ImportInventoryDto> dtos){
        GoodsListParamsDto gooddto = new GoodsListParamsDto();
        ArrayList<Inventory> inventories = new ArrayList<>();
        for (ImportInventoryDto dto : dtos) {
            if (StringUtils.isEmpty(dto.getGoodcode())) {
                throw new RuntimeException("商品编码不能为空！");
            }
            gooddto.setGoodsCode(dto.getGoodcode());
            gooddto.setCompanyId(dto.getCompanyId());
            PageInfo<GoodsInfoDao> goodsInfoDaoPageInfo = goodsService.queryByCondition(gooddto);
            if (CollectionUtils.isEmpty(goodsInfoDaoPageInfo.getList()) || goodsInfoDaoPageInfo.getList().size() < 1) {
                throw new RuntimeException("商品sn不存在：" + dto.getGoodcode());
            }
            final List<WarehouseLoc> warehouseLocs = warehouseService.selectByWarehouseCode(dto.getStorageLocationCode(), dto.getWareHouseId(),dto.getCompanyId());

            if(CollectionUtils.isEmpty(warehouseLocs)){
                    throw new RuntimeException("库位填写错误或不存在");
            }
            WarehouseLoc warehouseLoc = warehouseLocs.get(0);
            final HashMap<Object, Object> map = new HashMap<>();
            final Warehouse warehouse = warehouseService.selectById(warehouseLoc.getWhId());
            if (warehouse == null) {
                throw new RuntimeException("仓库不存在");
            }


            //数量检验
            final Double inventoryNum = Double.valueOf(dto.getNum());
            //判断小数位最多4位
            GoodsInfoDao goodsInfoDao = goodsInfoDaoPageInfo.getList().get(0);
            GoodsInfo goodsInfo = saveGoodsInfo(goodsInfoDao);
            Inventory inventory = InventoryFactory.createInventoryFromInventoryImportDto(dto,goodsInfo);
            inventory.setWarehouseName(warehouse.getWhName());
            inventory.setGoodsId(goodsInfo.getGoodsId());
            inventory.setOriginalGoodsId(goodsInfoDao.getGoodsId());
            inventory.setStorageLocationId(warehouseLoc.getWhLocId());
            inventory.setBaseUnit(goodsInfoDao.getUnit());

            Inventory inventory1 = addInventory(inventory);
            logService.saveImportInventoryLog(inventory, dto);
            inventories.add(inventory1);
        }
        return inventories;
    }


    public GoodsInfo saveGoodsInfo(GoodsInfoDao inOrdergoodsInfo) {
        GoodsInfo goodsInfo = new GoodsInfo();
        BeanUtils.copyProperties(inOrdergoodsInfo, goodsInfo);
        goodsInfo.setGoodsId(null);
        goodsInfoMapper.insert(goodsInfo);
        return goodsInfo;
    }
    


}
