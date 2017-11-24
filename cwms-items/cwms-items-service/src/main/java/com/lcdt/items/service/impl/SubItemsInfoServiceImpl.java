package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.CustomValueMapper;
import com.lcdt.items.dao.ItemSpecKeyValueMapper;
import com.lcdt.items.dao.SubItemsInfoMapper;
import com.lcdt.items.dto.ItemSpecKeyValueDto;
import com.lcdt.items.dto.SubItemsInfoDto;
import com.lcdt.items.model.CustomValue;
import com.lcdt.items.model.ItemSpecKeyValue;
import com.lcdt.items.model.SubItemsInfo;
import com.lcdt.items.service.SubItemsInfoService;
import com.lcdt.items.utils.SubItemsInfoDtoToSubItemsInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public int addSubItemsInfo(SubItemsInfoDto subItemsInfoDto) {
        int result = 0;
        SubItemsInfo subItemsInfo = SubItemsInfoDtoToSubItemsInfoUtil.parseSubItemsInfo(subItemsInfoDto);
        result = subItemsInfoMapper.insert(subItemsInfo);

        //子商品自定义属性值
        if (subItemsInfoDto.getCustomValueList() != null) {
            for (int i = 0; i < subItemsInfoDto.getCustomValueList().size(); i++) {
                subItemsInfoDto.getCustomValueList().get(i).setSubItemId(subItemsInfo.getSubItemId());
            }
            result += customValueMapper.insertForBatch(subItemsInfoDto.getCustomValueList());
        }

        if (subItemsInfoDto.getItemSpecKeyValueDtoList() != null) {
            List<ItemSpecKeyValue> itemSpecKeyValueList = new ArrayList<ItemSpecKeyValue>();
            for (int i = 0; i < subItemsInfoDto.getItemSpecKeyValueDtoList().size(); i++) {
                ItemSpecKeyValueDto itemSpecKeyValueDto = subItemsInfoDto.getItemSpecKeyValueDtoList().get(i);
                ItemSpecKeyValue itemSpecKeyValue = new ItemSpecKeyValue();
                itemSpecKeyValue.setSpkId(itemSpecKeyValueDto.getSpkId());
                itemSpecKeyValue.setSpName(itemSpecKeyValueDto.getSpName());
                itemSpecKeyValue.setSpvId(itemSpecKeyValueDto.getSpvId());
                itemSpecKeyValue.setSpValue(itemSpecKeyValueDto.getSpValue());
                itemSpecKeyValue.setCompanyId(subItemsInfo.getCompanyId());
                itemSpecKeyValue.setSubItemId(subItemsInfo.getSubItemId());
                itemSpecKeyValueList.add(itemSpecKeyValue);
            }
            result += itemSpecKeyValueMapper.insertForBatch(itemSpecKeyValueList);
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
    public int modifySubItemsInfo(SubItemsInfoDto subItemsInfoDto) {
        int result = 0;
        try {
            SubItemsInfo subItemsInfo = SubItemsInfoDtoToSubItemsInfoUtil.parseSubItemsInfo(subItemsInfoDto);

            //更新子商品自定义属性
            if (subItemsInfoDto.getCustomValueList() != null) {
                for (CustomValue customValue : subItemsInfoDto.getCustomValueList()) {
                    result += customValueMapper.updateByPrimaryKey(customValue);
                }
            }
            //更新子商品
            result = subItemsInfoMapper.updateByPrimaryKey(subItemsInfo);
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
        try {
            list = subItemsInfoMapper.selectSubItemsInfoListByItemId(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
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
        try {
            result = subItemsInfoMapper.deleteSubItemsInfoByItemId(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
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


}
