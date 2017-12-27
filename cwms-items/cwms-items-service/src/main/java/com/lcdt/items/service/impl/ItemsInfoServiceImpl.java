package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.config.SnowflakeIdWorker;
import com.lcdt.items.dao.*;
import com.lcdt.items.model.*;
import com.lcdt.items.service.ConversionRelService;
import com.lcdt.items.service.ItemsInfoService;
import com.lcdt.items.service.SubItemsInfoService;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2017/10/26
 */
@Transactional
@Service
public class ItemsInfoServiceImpl implements ItemsInfoService {

    @Autowired
    private ItemsInfoMapper itemsInfoMapper;

    @Autowired
    private SubItemsInfoMapper subItemsInfoMapper;

    @Autowired
    private CustomValueMapper customValueMapper;

    @Autowired
    private ConversionRelService conversionRelService;

    @Autowired
    private SubItemsInfoService subItemsInfoService;

    @Autowired
    private ItemSpecKeyValueMapper itemSpecKeyValueMapper;


    @Override
    public int addItemsInfo(ItemsInfoDao itemsInfoDao) {
        int result = 0;

        if (itemsInfoDao.getConversionRel() != null) {
            conversionRelService.addConversionRel(itemsInfoDao.getConversionRel());
            itemsInfoDao.setConverId(itemsInfoDao.getConversionRel().getConverId());
        }

        //新增商品
        result += itemsInfoMapper.insert(itemsInfoDao);

        //自定义属性值
        if (itemsInfoDao.getCustomValueList() != null) {
            for (int i = 0; i < itemsInfoDao.getCustomValueList().size(); i++) {
                itemsInfoDao.getCustomValueList().get(i).setItemId(itemsInfoDao.getItemId());
            }
            result += customValueMapper.insertForBatch(itemsInfoDao.getCustomValueList());
        }

        //判断商品类型 1、单规格商品，2、多规格商品，3、组合商品
        if (itemsInfoDao.getItemType() == 1 || itemsInfoDao.getItemType() == 2) {
            //判断子商品是否为空
            if (itemsInfoDao.getSubItemsInfoDaoList() != null) {
                for (SubItemsInfoDao dao : itemsInfoDao.getSubItemsInfoDaoList()) {
                    //新增子商品
                    //给dto增加商品itemsId
                    dao.setItemId(itemsInfoDao.getItemId());
                    result += subItemsInfoService.addSubItemsInfo(dao);
                }
            }
        }
        return result;
    }

    @Override
    public int deleteItemsInfo(Long itemId, Long companyId) {
        int result = 0;
        ItemsInfo item = new ItemsInfo();
        item.setItemId(itemId);
        item.setCompanyId(companyId);
        ItemsInfo itemsInfo = itemsInfoMapper.selectByItemIdAndCompanyId(item);
        if (itemsInfo != null) {
            //判断商品类型，删除子商品
            if (itemsInfo.getItemType() == 2 || itemsInfo.getItemType() == 1) {
                subItemsInfoService.deleteSubItemsInfoByItemId(itemId, companyId);
            }
            //判断是否有多单位，如果有，则删除
            if (itemsInfo.getConverId() != null && itemsInfo.getConverId() > 0) {
                result += conversionRelService.deleteConversionRel(itemsInfo.getConverId(), companyId);
            }
            //删除自定义属性值
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("itemId", itemId);
            map.put("subItemIdList", null);
            map.put("companyId", companyId);
            result += customValueMapper.deleteItemAndSubItemId(map);
            //删除主商品
            result = itemsInfoMapper.deleteByItemIdAndCompanyId(itemsInfo);

        }
        return result;
    }

    @Override
    public int modifyItemsInfo(ItemsInfoDao itemsInfoDao) {
        int result = 0;
        if (itemsInfoDao.getConversionRel() != null) {
            conversionRelService.modifyConversionRel(itemsInfoDao.getConversionRel());
            itemsInfoDao.setConverId(itemsInfoDao.getConversionRel().getConverId());
        }
        //更新主商品
        result = itemsInfoMapper.updateByItemIdAndCompanyId(itemsInfoDao);

        //主商品自定义属性值更新
        if (itemsInfoDao.getCustomValueList() != null) {
            for (CustomValue customValue : itemsInfoDao.getCustomValueList()) {
                result += customValueMapper.updateByVidAndCompanyId(customValue);
            }
        }
        //判断商品类型 1、单规格商品，2、多规格商品，3、组合商品
        if (itemsInfoDao.getItemType() == 2 || itemsInfoDao.getItemType() == 1) {
            //判断子商品是否为空
            if (itemsInfoDao.getSubItemsInfoDaoList() != null) {
                for (SubItemsInfoDao dao : itemsInfoDao.getSubItemsInfoDaoList()) {
                    if (dao.getSubItemId() != null && dao.getSubItemId() > 0) {
                        //子商品更新
                        result += subItemsInfoService.modifySubItemsInfo(dao);
                    } else {
                        //子商品添加
                        dao.setItemId(dao.getItemId());
                        result += subItemsInfoService.addSubItemsInfo(dao);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public PageInfo<List<ItemsInfoDao>> queryItemsByCondition(ItemsInfo itemsInfo, PageInfo pageInfo) {
        List<ItemsInfoDao> resultList = null;
        PageInfo page = null;
        //使用分页工具进行分页
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        resultList = itemsInfoMapper.selectByCondition(itemsInfo);
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public PageInfo<List<ItemsInfoDao>> queryItemsByItemsInfo(ItemsInfo itemsInfo, PageInfo pageInfo) {
        List<ItemsInfoDao> resultList = null;
        PageInfo page = null;
        //使用分页工具进行分页
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        resultList = itemsInfoMapper.selectByItemsInfo(itemsInfo);
        page = new PageInfo(resultList);
        return page;
    }

    @Override
    public List<ItemsInfo> queryItemsByCalUnitId(Long calUnitId, Long companyId) {
        ItemsInfo itemsInfo=new ItemsInfo();
        itemsInfo.setCompanyId(companyId);
        itemsInfo.setUnitId(calUnitId);
        return itemsInfoMapper.selectByItemsCalUnitId(itemsInfo);
    }

    @Override
    public ItemsInfoDao queryIetmsInfoDetails(Long itemId, Long companyId) {
        ItemsInfo itemsInfo = new ItemsInfo();
        itemsInfo.setItemId(itemId);
        itemsInfo.setCompanyId(companyId);
        return itemsInfoMapper.selectIetmsInfoDetails(itemsInfo);
    }


}
