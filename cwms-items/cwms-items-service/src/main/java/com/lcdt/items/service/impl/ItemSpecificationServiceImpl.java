package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.dao.ItemSpecKeyMapper;
import com.lcdt.items.dao.ItemSpecValueMapper;
import com.lcdt.items.dto.ItemSpecKeyDto;
import com.lcdt.items.dto.ItemSpecValueDto;
import com.lcdt.items.model.ItemSpecKey;
import com.lcdt.items.model.ItemSpecValue;
import com.lcdt.items.model.ItemSpecificationDao;
import com.lcdt.items.service.ItemSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/22
 */
@Service
@Transactional
public class ItemSpecificationServiceImpl implements ItemSpecificationService {

    @Autowired
    private ItemSpecKeyMapper itemSpecKeyMapper;
    @Autowired
    private ItemSpecValueMapper itemSpecValueMapper;

    @Override
    public int addSpecification(ItemSpecKeyDto itemSpecKeyDto) {
        //获取属性名是否存在
        List<ItemSpecKey> itemSpecKeyList = itemSpecKeyMapper.selectSpecificationBySpName(itemSpecKeyDto.getCompanyId(), itemSpecKeyDto.getSpName());
        if (itemSpecKeyList.size() > 0) {
            throw new RuntimeException("商品属性名已经存在");    //如果属性名已存在就直接抛出异常
        }
        int reslut = 0;
        try {
            ItemSpecKey itemSpecKey = new ItemSpecKey();
            itemSpecKey.setSpName(itemSpecKeyDto.getSpName());
            itemSpecKey.setCompanyId(itemSpecKeyDto.getCompanyId());
            reslut += itemSpecKeyMapper.insert(itemSpecKey);
            if (itemSpecKeyDto.getItemSpecValueList() != null) {
                for (ItemSpecValue dto : itemSpecKeyDto.getItemSpecValueList()) {
                    ItemSpecValue itemSpecValue = new ItemSpecValue();
                    itemSpecValue.setSpValue(dto.getSpValue());
                    itemSpecValue.setSpkId(itemSpecKey.getSpkId());
                    itemSpecValue.setCompanyId(itemSpecKey.getCompanyId());
                    reslut += itemSpecValueMapper.insert(itemSpecValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reslut;
        }
    }

    @Override
    public int deleteSpecification(Long spkId,Long companyId) {
        int reslut = 0;
        try {
            reslut += itemSpecValueMapper.deleteBySpkIdAndCompanyId(spkId,companyId);
            reslut += itemSpecKeyMapper.deleteBySpkIdAndCompanyId(spkId,companyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reslut;
        }
    }

    @Override
    public int deleteItemSpecvalue(Long spvId,Long companyId) {
        int reslut = 0;
        try {
            reslut += itemSpecValueMapper.deleteBySpvIdAndCompanyId(spvId,companyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reslut;
        }
    }

    @Override
    public int modifySpecification(ItemSpecKeyDto itemSpecKeyDto) {
        int reslut = 0;
        try {
            ItemSpecKey itemSpecKey = new ItemSpecKey();
            itemSpecKey.setSpkId(itemSpecKeyDto.getSpkId());
            itemSpecKey.setSpName(itemSpecKeyDto.getSpName());
            itemSpecKey.setCompanyId(itemSpecKeyDto.getCompanyId());
            reslut += itemSpecKeyMapper.updateByPrimaryKey(itemSpecKey);
            /**
             * //mod by liuh
             * 修改属性值会有三种情况：
             * 1.修改原来的规格值得更新
             * 2.新增规格值的需要新增
             * 3.删除的规格值需要删除
             * 介于处理这三种情况处理比较繁琐，所以直接采用删除关联值，然后重新新增的方式
             */
            itemSpecValueMapper.deleteBySpkIdAndCompanyId(itemSpecKeyDto.getSpkId(),itemSpecKeyDto.getCompanyId());   //删除规格关联的属性值
            if (itemSpecKeyDto.getItemSpecValueList() != null) {
                for (ItemSpecValue dto : itemSpecKeyDto.getItemSpecValueList()) {
                    ItemSpecValue itemSpecValue = new ItemSpecValue();
                    itemSpecValue.setSpvId(dto.getSpvId());
                    itemSpecValue.setSpValue(dto.getSpValue());
                    itemSpecValue.setCompanyId(dto.getCompanyId());
                    itemSpecValue.setSpkId(itemSpecKey.getSpkId());
                    reslut += itemSpecValueMapper.insert(itemSpecValue);
                }
            }
            /*if (itemSpecKeyDto.getItemSpecValueDtoList() != null) {
                for (ItemSpecValueDto dto : itemSpecKeyDto.getItemSpecValueDtoList()) {
                    ItemSpecValue itemSpecValue = new ItemSpecValue();
                    itemSpecValue.setSpvId(dto.getSpvId());
                    itemSpecValue.setSpValue(dto.getSpValue());
                    itemSpecValue.setCompanyId(dto.getCompanyId());
                    itemSpecValue.setSpkId(itemSpecKey.getSpkId());
                    if (itemSpecValue.getSpvId() == null || itemSpecValue.getSpvId() == 0) {
                        reslut += itemSpecValueMapper.insert(itemSpecValue);
                    } else {
                        reslut += itemSpecValueMapper.updateByPrimaryKey(itemSpecValue);
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reslut;
        }
    }

    @Override
    public List<ItemSpecificationDao> querySpecification(Long companyId) {
        List<ItemSpecificationDao> resultList = null;
        try {
            resultList = itemSpecKeyMapper.selectSpecificationList(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return resultList;
        }
    }

    @Override
    public int deleteSpecificationKeyAndValueBySpkId(Long spkId, Long companyId) {
        int result = 0;
        result += itemSpecKeyMapper.deleteBySpkIdAndCompanyId(spkId,companyId);
        result += itemSpecValueMapper.deleteBySpkIdAndCompanyId(spkId,companyId);
        return result;
    }
}
