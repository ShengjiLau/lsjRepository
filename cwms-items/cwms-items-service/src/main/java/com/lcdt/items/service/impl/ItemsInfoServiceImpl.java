package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.config.SnowflakeIdWorker;
import com.lcdt.items.dao.*;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.*;
import com.lcdt.items.service.ConversionRelService;
import com.lcdt.items.service.ItemClassifyService;
import com.lcdt.items.service.ItemsInfoService;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.items.utils.ItemsInfoDtoToItemsInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
                    dao.setCompanyId(itemsInfoDao.getCompanyId());
                    result+=subItemsInfoService.addSubItemsInfo(dao);
                }
            }
        }
        return result;
    }

    @Override
    public int deleteItemsInfo(Long itemId) {
        int result = 0;
        try {
            ItemsInfo itemsInfo = itemsInfoMapper.selectByPrimaryKey(itemId);
            //子商品 subItemId 用 , 分隔开的字符串
            StringBuffer subItemsInfoIds = new StringBuffer();
            if (itemsInfo != null) {
                //判断商品类型，删除子商品
                if (itemsInfo.getItemType() == 2) {
                    List<SubItemsInfo> subItemsInfoList = subItemsInfoService.querySubItemsInfoListByItemId(itemId);
                    //如果子商品不为空，则组装用 , 分隔开的字符串，以便批量删除了商品的自定义属性值
                    if (subItemsInfoList != null) {
                        for (int i = 0; i < subItemsInfoList.size(); i++) {
                            if (i == 0) {
                                subItemsInfoIds.append(subItemsInfoList.get(i).getSubItemId());
                            } else {
                                subItemsInfoIds.append(",").append(subItemsInfoList.get(i).getSubItemId());
                            }
                        }
                    }
                    //删除子商品规格
                    result += itemSpecKeyValueMapper.deleteBySubItemIds(subItemsInfoIds.toString());
                    result += subItemsInfoService.deleteSubItemsInfoByItemId(itemId);
                }

            }
            //判断是否有多单位，如果有，则删除
            if (itemsInfo.getConverId() != null && itemsInfo.getConverId() > 0) {
                result += conversionRelService.deleteConversionRel(itemsInfo.getConverId());
            }
            //删除自定义属性值
            result += customValueMapper.deleteItemAndSubItemId(itemId.toString(), subItemsInfoIds.toString());
            //删除主商品
            result = itemsInfoMapper.deleteByPrimaryKey(itemId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int modifyItemsInfo(ItemsInfoDao itemsInfoDao) {
        int result = 0;
        try {
            //更新主商品
            result = itemsInfoMapper.updateByPrimaryKey(itemsInfoDao);

            //主商品自定义属性值更新
            if (itemsInfoDao.getCustomValueList() != null) {
                for (CustomValue customValue : itemsInfoDao.getCustomValueList()) {
                    result += customValueMapper.updateByPrimaryKey(customValue);
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
                            dao.setCompanyId(dao.getCompanyId());
                            result += subItemsInfoService.modifySubItemsInfo(dao);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public ItemsInfo queryItemsInfoByItemId(Long itemId) {
        ItemsInfo itemsInfo = null;
        try {
            itemsInfo = itemsInfoMapper.selectByPrimaryKey(itemId);
            StringBuilder subItemIds = null;
            if (itemsInfo.getItemType() == 2) {
                List<SubItemsInfo> subItemsInfoList = subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
                for (int i = 0; i < subItemsInfoList.size(); i++) {
                    if (i == 0) {
                        subItemIds = new StringBuilder(subItemsInfoList.get(i).getSubItemId().toString());
                    } else {
                        subItemIds.append(",").append(subItemsInfoList.get(i).getSubItemId().toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemsInfo;
        }
    }

    @Override
    public PageInfo<List<ItemsInfoDao>> queryItemsByCondition(ItemsInfo itemsInfo, PageInfo pageInfo) {
        List<ItemsInfoDao> resultList = null;
        PageInfo page = null;
        try {
            //使用分页工具进行分页
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
            resultList = itemsInfoMapper.selectByCondition(itemsInfo);
            page = new PageInfo(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return page;
        }
    }

    @Override
    public String getAutoItemCode() {
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        String itemCode = "PN" + snowflakeIdWorker.nextId();
        return itemCode;
    }

    @Override
    public ItemsInfo queryItemsInfoByCodeAndCompanyId(String code, Long companyId) {
        ItemsInfo result = null;
        try {
            ItemsInfo itemsInfo = new ItemsInfo();
            itemsInfo.setCode(code);
            itemsInfo.setCompanyId(companyId);
            result = itemsInfoMapper.selectByCodeAndCompanyId(itemsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
