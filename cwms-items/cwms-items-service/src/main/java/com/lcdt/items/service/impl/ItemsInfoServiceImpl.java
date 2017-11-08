package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.ItemsInfoMapper;
import com.lcdt.items.dto.ItemsInfoDto;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.service.ItemsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/26
 */
@Transactional
@Service
public class ItemsInfoServiceImpl implements ItemsInfoService {

    @Autowired
    private ItemsInfoMapper itemsInfoMapper;

    @Override
    public int addItemsInfo(ItemsInfoDto itemsInfoDto) {
        int result=0;
        try{
            ItemsInfo itemsInfo=new ItemsInfo();
            itemsInfo.setSubject(itemsInfoDto.getSubject());
            itemsInfo.setCode(itemsInfoDto.getCode());
            itemsInfo.setBarCode(itemsInfoDto.getBarCode());
            itemsInfo.setTradeType(itemsInfoDto.getTradeType());
            itemsInfo.setPurchasePrice(itemsInfoDto.getPurchasePrice());
            itemsInfo.setRetailPrice(itemsInfoDto.getRetailPrice());
            itemsInfo.setRetailPrice(itemsInfoDto.getRetailPrice());
            itemsInfo.setIntroduction(itemsInfoDto.getIntroduction());
            itemsInfo.setImage1(itemsInfoDto.getImage1());
            itemsInfo.setImage2(itemsInfoDto.getImage2());
            itemsInfo.setImage3(itemsInfoDto.getImage3());
            itemsInfo.setImage4(itemsInfoDto.getImage4());
            itemsInfo.setImage5(itemsInfoDto.getImage5());
            itemsInfo.setClassifyId(itemsInfoDto.getClassifyId());
            itemsInfo.setClassifyName(itemsInfoDto.getClassifyName());
            itemsInfo.setUnitId(itemsInfoDto.getUnitId());
            itemsInfo.setUnitName(itemsInfoDto.getUnitName());
            itemsInfo.setConverId(itemsInfoDto.getConverId());


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    @Override
    public int deleteItemsInfo(Long itemId) {
        return 0;
    }

    @Override
    public int modifyItemsInfo(ItemsInfoDto itemsInfoDto) {
        return 0;
    }

    @Override
    public ItemsInfo queryItemsInfoByItemId(Long itemId) {
        return null;
    }

    @Override
    public List<ItemsInfo> queryItemsInfoByCompanyId(Long companyId, PageInfo pageInfo) {
        return null;
    }
}
