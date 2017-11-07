package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.dao.ItemClassifyMapper;
import com.lcdt.items.dto.ItemClassifyDto;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.service.ItemClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lyqishan on 2017/10/30
 */
@Transactional
@Service
public class ItemClassifyServiceImpl implements ItemClassifyService {

    @Autowired
    private ItemClassifyMapper itemClassifyMapper;

    @Override
    public int addItemClassify(ItemClassifyDto itemClassifyDto) {
        int result = 0;
        try {
            ItemClassify itemClassify = new ItemClassify();
            itemClassify.setClassifyName(itemClassifyDto.getClassifyName());
            itemClassify.setCompanyId(itemClassify.getCompanyId());
            itemClassify.setPid(itemClassify.getPid());
            result = itemClassifyMapper.insert(itemClassify);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int deleteItemClassify(Long classifyId) {
        int result = 0;
        try {
            result = itemClassifyMapper.deleteByPrimaryKey(classifyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public int modifyByPrimaryKey(ItemClassifyDto itemClassifyDto) {
        int result = 0;
        try {
            ItemClassify itemClassify = new ItemClassify();
            itemClassify.setClassifyId(itemClassifyDto.getClassifyId());
            itemClassify.setClassifyName(itemClassifyDto.getClassifyName());
            itemClassify.setCompanyId(itemClassify.getCompanyId());
            itemClassify.setPid(itemClassify.getPid());
            result = itemClassifyMapper.updateByPrimaryKey(itemClassify);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public ItemClassify queryByPrimaryKey(Long classifyId) {
        ItemClassify itemClassify = null;
        try {
            itemClassify = itemClassifyMapper.selectByPrimaryKey(classifyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemClassify;
        }
    }

    @Override
    public List<ItemClassify> queryItemClassifyByCompanyId(Long companyId, Long pid) {
        List<ItemClassify> itemClassifyList = null;
        try {
            itemClassifyList = itemClassifyMapper.selectClassifyByCompanyIdAndPid(companyId, pid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemClassifyList;
        }
    }

    @Override
    public List<ItemClassify> queryItemClassifyByPid(Long pid) {
        List<ItemClassify> itemClassifyList = null;
        try {
            itemClassifyList = itemClassifyMapper.selectClassifyByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return itemClassifyList;
        }
    }
}
