package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.warehouse.contants.InOrderStatus;
import com.lcdt.warehouse.contants.OutOrderStatus;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.*;
import com.lcdt.warehouse.factory.InventoryFactory;
import com.lcdt.warehouse.mapper.GoodsInfoMapper;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.mapper.OutWarehouseOrderMapper;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.lcdt.warehouse.service.InventoryLogService;
import com.lcdt.warehouse.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public Inventory modifyInventoryPrice(Long inventoryId, Float newprice) {
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
    public void lockInventoryNum(Long inventoryId,Float tryLockNum){
        Assert.notNull(tryLockNum, "不能为空");
        Inventory inventory = selectById(inventoryId);

        if (inventory.getavailableNum() < tryLockNum) {
            throw new RuntimeException("锁定库存量不能大于库存剩余数量");
        }
        inventory.setLockNum(inventory.getLockNum() + tryLockNum);
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
                inventory.setLockNum(inventory.getLockNum() - good.getGoodsNum());
                inventory.setInvertoryNum(inventory.getInvertoryNum() - good.getOutboundQuantity());
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
        logger.info("入库 更新库存数量：{}", existInventory);
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

}
