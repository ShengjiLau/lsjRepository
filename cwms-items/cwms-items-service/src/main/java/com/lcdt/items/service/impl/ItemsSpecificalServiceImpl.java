package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.ItemsSpecificalMapper;
import com.lcdt.items.dto.ItemsSpecificalDto;
import com.lcdt.items.model.ItemsSpecifical;
import com.lcdt.items.service.ItemsSpecificalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/9
 */
@Service
@Transactional
public class ItemsSpecificalServiceImpl implements ItemsSpecificalService {

    @Autowired
    private ItemsSpecificalMapper itemsSpecificalMapper;

    @Override
    public int addItemsSpecifical(ItemsSpecificalDto itemsSpecificalDto) {
        int result = 0;
        try {
            ItemsSpecifical itemsSpecifical = new ItemsSpecifical();
            itemsSpecifical.setSpecName(itemsSpecificalDto.getSpecName());
            itemsSpecifical.setSpecValue(itemsSpecificalDto.getSpecValue());
            itemsSpecifical.setCompanyId(itemsSpecificalDto.getCompanyId());
            result = itemsSpecificalMapper.insert(itemsSpecifical);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int deleteItemsSpecifical(Long specId) {
        int result = 0;
        try {
            result = itemsSpecificalMapper.deleteByPrimaryKey(specId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int modifyItemsSpecifical(ItemsSpecificalDto itemsSpecificalDto) {
        int result = 0;
        try {
            ItemsSpecifical itemsSpecifical = new ItemsSpecifical();
            itemsSpecifical.setSpecId(itemsSpecificalDto.getSpecId());
            itemsSpecifical.setSpecName(itemsSpecificalDto.getSpecName());
            itemsSpecifical.setSpecValue(itemsSpecificalDto.getSpecValue());
            itemsSpecifical.setCompanyId(itemsSpecificalDto.getCompanyId());
            result = itemsSpecificalMapper.insert(itemsSpecifical);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public ItemsSpecifical queryItemsSpecifical(Long specId) {
        ItemsSpecifical itemsSpecifical = null;
        try {
            itemsSpecifical = itemsSpecificalMapper.selectByPrimaryKey(specId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemsSpecifical;
        }
    }

    @Override
    public List<ItemsSpecifical> queryItemsSpecificalListByCompanyId(Long companyId, PageInfo pageInfo) {
        List<ItemsSpecifical> list = null;
        try {
            PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
            list = itemsSpecificalMapper.selectItemsSpecificalListByCompanyId(companyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return list;
        }
    }
}
