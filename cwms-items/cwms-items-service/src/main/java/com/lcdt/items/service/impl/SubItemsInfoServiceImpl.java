package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.CustomValueMapper;
import com.lcdt.items.dao.ItemSpecKeyValueMapper;
import com.lcdt.items.dao.SubItemsInfoMapper;
import com.lcdt.items.model.CustomValue;
import com.lcdt.items.model.ItemSpecKeyValue;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.model.SubItemsInfoDao;
import com.lcdt.items.service.SubItemsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int deleteSubItemsInfo(Long subItemId, Long companyId) {
        int result = 0;
        SubItemsInfo subItemsInfo = new SubItemsInfo();
        subItemsInfo.setItemId(subItemId);
        subItemsInfo.setCompanyId(companyId);
        //子商品删除
        result = subItemsInfoMapper.deleteBySubItemIdAndCompanyId(subItemsInfo);
        //子商品自定义属性删除
//        result += customValueMapper.deleteItemAndSubItemId(null, subItemId.toString(), companyId.toString());
        //子商品规格
        ItemSpecKeyValue itemSpecKeyValue = new ItemSpecKeyValue();
        itemSpecKeyValue.setSubItemId(subItemId);
        itemSpecKeyValue.setCompanyId(companyId);
        result += itemSpecKeyValueMapper.deleteBySubItemIdAndCompanyId(itemSpecKeyValue);
        return result;
    }

    @Override
    public int deleteSubItemsInfoByItemId(Long itemId, Long companyId) {
        int result = 0;
        SubItemsInfo subItemsInfo=new SubItemsInfo();
        subItemsInfo.setItemId(itemId);
        subItemsInfo.setCompanyId(companyId);

        List<SubItemsInfo> subItemsInfoList = subItemsInfoMapper.selectSubItemInfotByItemIdAndCompanyId(subItemsInfo);
        //如果子商品不为空，则组装用 , 分隔开的字符串，以便批量删除了商品的自定义属性值
        if (subItemsInfoList != null) {
            //子商品 subItemId 用 , 分隔开的字符串
            List<Long> subItemIdList = new ArrayList<Long>();
            for (int i = 0; i < subItemsInfoList.size(); i++) {
                subItemIdList.add(subItemsInfoList.get(i).getSubItemId());
            }
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("itemId",null);
            map.put("subItemIdList",subItemIdList);
            map.put("companyId",companyId);
            //删除规格
            result += itemSpecKeyValueMapper.deleteBySubItemIds(map);
            //删除自定义属性值
            result += customValueMapper.deleteItemAndSubItemId(map);
            result += subItemsInfoMapper.deleteSubItemsInfoByItemIdAndCompanyId(subItemsInfo);
        }
        return result;
    }

    @Override
    public int modifySubItemsInfo(SubItemsInfoDao subItemsInfoDao) {
        int result = 0;
        //更新子商品自定义属性
        if (subItemsInfoDao.getCustomValueList() != null) {
            for (CustomValue customValue : subItemsInfoDao.getCustomValueList()) {
                result += customValueMapper.updateByVidAndCompanyId(customValue);
            }
        }
        //更新子商品
        result = subItemsInfoMapper.updateBySubItemIdAndCompanyId(subItemsInfoDao);
        return result;
    }

    @Override
    public SubItemsInfoDao querySubItemsInfoBySubItemId(Long subItemId, Long companyId) {
        SubItemsInfo subItemsInfo = new SubItemsInfo();
        subItemsInfo.setSubItemId(subItemId);
        subItemsInfo.setCompanyId(companyId);
        return subItemsInfoMapper.selectBySubItemIdAndCompanyId(subItemsInfo);
    }

    @Override
    public PageInfo<List<SubItemsInfoDao>> querySubAndSpecAndPropListByItemId(Long itemId, Long companyId,PageInfo pageInfo) {
        List<SubItemsInfoDao> list = null;
        PageInfo page = null;
        SubItemsInfo subItemsInfo=new SubItemsInfo();
        subItemsInfo.setItemId(itemId);
        subItemsInfo.setCompanyId(companyId);
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        list = subItemsInfoMapper.selectSubAndSpecAndPropListByItemIdAndCompanyId(subItemsInfo);
        page = new PageInfo(list);
        return page;
    }

    @Override
    public List<SubItemsInfo> querySubItemsInfoListByItemId(Long itemId, Long companyId) {
        List<SubItemsInfo> list = null;
        SubItemsInfo subItemsInfo=new SubItemsInfo();
        subItemsInfo.setItemId(itemId);
        subItemsInfo.setCompanyId(companyId);
        list = subItemsInfoMapper.selectSubItemInfotByItemIdAndCompanyId(subItemsInfo);
        return list;
    }


}
