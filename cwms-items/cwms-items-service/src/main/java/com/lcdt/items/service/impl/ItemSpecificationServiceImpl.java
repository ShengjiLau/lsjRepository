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
        int reslut = 0;
        try {
            ItemSpecKey itemSpecKey = new ItemSpecKey();
            itemSpecKey.setSpName(itemSpecKeyDto.getSpName());
            itemSpecKey.setCompanyId(itemSpecKeyDto.getCompanyId());
            reslut += itemSpecKeyMapper.insert(itemSpecKey);
            if (itemSpecKeyDto.getItemSpecValueDtoList() != null) {
                for (ItemSpecValueDto dto : itemSpecKeyDto.getItemSpecValueDtoList()) {
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
    public int deleteSpecification(Long spkId) {
        int reslut = 0;
        try {
            reslut += itemSpecValueMapper.deleteBySpkId(spkId);
            reslut += itemSpecKeyMapper.deleteByPrimaryKey(spkId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reslut;
        }
    }

    @Override
    public int deleteItemSpecvalue(Long spvId) {
        int reslut = 0;
        try {
            reslut += itemSpecValueMapper.deleteByPrimaryKey(spvId);
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
            if (itemSpecKeyDto.getItemSpecValueDtoList() != null) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return reslut;
        }
    }

    @Override
    public List<ItemSpecificationDao> querySpecification(Long companyId) {
        List<ItemSpecificationDao> resultList=null;
        try{
            resultList=itemSpecKeyMapper.selectSpecificationList(companyId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return resultList;
        }
    }
}
