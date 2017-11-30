package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.CustomValueMapper;
import com.lcdt.items.dao.ItemSpecKeyValueMapper;
import com.lcdt.items.dao.SubItemsInfoMapper;
import com.lcdt.items.model.CustomValue;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.model.SubItemsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/9
 */
@Transactional
@Service
public class SubItemsInfoServiceImpl implements SubItemsInfoService {

    @Autowired
    private SubItemsInfoMapper subItemsInfoMapper;

    @Autowired
    private CustomValueMapper customValueMapper;

    @Autowired
    private ItemSpecKeyValueMapper itemSpecKeyValueMapper;

    @Override
    public int addSubItemsInfo(SubItemsInfoDao subItemsInfoDao) {
        int result = 0;
        result = subItemsInfoMapper.insert(subItemsInfoDao);

        //子商品自定义属性值
        if (subItemsInfoDao.getCustomValueList() != null) {
            for (int i = 0; i < subItemsInfoDao.getCustomValueList().size(); i++) {
                subItemsInfoDao.getCustomValueList().get(i).setSubItemId(subItemsInfoDao.getSubItemId());
            }
            result += customValueMapper.insertForBatch(subItemsInfoDao.getCustomValueList());
        }

        if (subItemsInfoDao.getItemSpecKeyValueList() != null) {
            for (int i = 0; i < subItemsInfoDao.getItemSpecKeyValueList().size(); i++) {
                subItemsInfoDao.getItemSpecKeyValueList().get(i).setSubItemId(subItemsInfoDao.getSubItemId());
            }
            result += itemSpecKeyValueMapper.insertForBatch(subItemsInfoDao.getItemSpecKeyValueList());
        }
        return result;
    }

    @Override
    public int deleteSubItemsInfo(Long subItemId) {
        int result = 0;
        try {
            //子商品删除
            result = subItemsInfoMapper.deleteByPrimaryKey(subItemId);
            //子商品自定义属性删除
            result += customValueMapper.deleteItemAndSubItemId(null, subItemId.toString());

            result += itemSpecKeyValueMapper.deleteBySubItemId(subItemId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int modifySubItemsInfo(SubItemsInfoDao subItemsInfoDao) {
        int result = 0;
        try {
            //更新子商品自定义属性
            if (subItemsInfoDao.getCustomValueList() != null) {
                for (CustomValue customValue : subItemsInfoDao.getCustomValueList()) {
                    result += customValueMapper.updateByPrimaryKey(customValue);
                }
            }
            //更新子商品
            result = subItemsInfoMapper.updateByPrimaryKey(subItemsInfoDao);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public SubItemsInfo querySubItemsInfoBySubItemId(Long subItemId) {
        SubItemsInfo subItemsInfo = null;
        try {
            subItemsInfo = subItemsInfoMapper.selectByPrimaryKey(subItemId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return subItemsInfo;
        }
    }

    @Override
    public List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId) {
        List<SubItemsInfo> list = null;
        list = subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
        return list;

    }

    @Override
    public List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId, PageInfo pageInfo) {
        List<SubItemsInfo> list = null;
        try {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
            list = subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }

    @Override
    public int deleteSubItemsInfoByItemId(Long itemId) {
        int result = 0;
        result = subItemsInfoMapper.deleteSubItemsInfoByItemId(itemId);
        return result;
    }

    @Override
    public int addSubItemsInfoBatch(List<SubItemsInfo> subItemsInfoList) {
        int result = 0;
        try {
            subItemsInfoMapper.insertByBatch(subItemsInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public List<SubItemsInfoDao> querySubAndSpecAndPropListByItemId(Long itemId) {
        List<SubItemsInfoDao> list = null;
        list = subItemsInfoMapper.selectSubAndSpecAndPropListByItemId(itemId);
        return list;
    }


}
