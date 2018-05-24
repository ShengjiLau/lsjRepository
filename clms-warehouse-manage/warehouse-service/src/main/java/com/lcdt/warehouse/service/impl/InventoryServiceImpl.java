package com.lcdt.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.items.dto.GoodsListParamsDto;
import com.lcdt.items.model.GoodsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.warehouse.dto.InventoryQueryDto;
import com.lcdt.warehouse.entity.GoodsInfo;
import com.lcdt.warehouse.entity.InWarehouseOrder;
import com.lcdt.warehouse.entity.InorderGoodsInfo;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.factory.InventoryFactory;
import com.lcdt.warehouse.mapper.GoodsInfoMapper;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.warehouse.service.InventoryLogService;
import com.lcdt.warehouse.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

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

    @Reference
    SubItemsInfoService goodsService;

    @Autowired
    private InventoryLogService logService;

    private Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);


    //分页查询 库存列表
    public Page<Inventory> queryInventoryPage(InventoryQueryDto inventoryQueryDto,Long companyId) {
        List<Long> goodsId = queryGoodsIds(inventoryQueryDto, companyId);
        Page<Inventory> page = new Page<>(inventoryQueryDto.getPageNo(), inventoryQueryDto.getPageSize());
        List<Inventory> inventories = inventoryMapper.selectInventoryListByqueryDto(goodsId, page, inventoryQueryDto);
        queryGoodsInfo(companyId, inventories);
        return page.setRecords(inventories);
    }

    private void queryGoodsInfo(Long companyId, List<Inventory> inventories) {
        ArrayList<Long> longs = new ArrayList<>();
        for (Inventory inventory : inventories) {
            longs.add(inventory.getOriginalGoodsId());
        }
        GoodsListParamsDto dto = new GoodsListParamsDto();
        dto.setGoodsIds(longs);
        dto.setCompanyId(companyId);
        PageInfo<GoodsInfoDao> listPageInfo = goodsService.queryByCondition(dto);
        List<GoodsInfoDao> list = listPageInfo.getList();
        for (Inventory inventory : inventories) {
            for (GoodsInfoDao goodsInfoDao : list) {
                if (inventory.getOriginalGoodsId().equals(goodsInfoDao.getGoodsId())) {
                    inventory.setGoods(goodsInfoDao);
                }
            }
        }
    }

    private List<Long> queryGoodsIds(InventoryQueryDto inventoryQueryDto, Long companyId) {
        GoodsListParamsDto dto = new GoodsListParamsDto();
        dto.setClassifyName(inventoryQueryDto.getGoodsCategory());
        dto.setGoodsName(inventoryQueryDto.getGoodsName());
        dto.setCompanyId(companyId);
        dto.setGoodsCode(inventoryQueryDto.getGoodsCode());
        dto.setBarCode(inventoryQueryDto.getGoodsBarCode());
        return goodsService.queryGoodsIdsByCondition(dto);
    }


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
            logService.saveInOrderLog(order, inventory);
            addInventory(inventory);
        }
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

    /**
     * 出库操作
     */
    @Transactional(rollbackFor = Exception.class)
    public void outInventory() {

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
        List<Inventory> inventories = inventoryMapper.selectInventoryList(null, inventory);
        if (inventories == null) {
            return new ArrayList<>();
        }
        return inventories;
    }

}
