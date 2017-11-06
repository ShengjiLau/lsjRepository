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
        try {
            ItemClassify itemClassify = new ItemClassify();
            itemClassify.setClassifyName(itemClassifyDto.getClassifyName());
            itemClassify.setCompanyId(itemClassify.getCompanyId());
            itemClassify.setPid(itemClassify.getPid());
            int result = itemClassifyMapper.insert(itemClassify);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return 0;
        }
    }

    @Override
    public int deleteItemClassify(Long classifyId) {
        try {
            return itemClassifyMapper.deleteByPrimaryKey(classifyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return 0;
        }
    }

    @Override
    public int modifyByPrimaryKey(ItemClassifyDto itemClassifyDto) {
        try {
            ItemClassify itemClassify = new ItemClassify();
            itemClassify.setClassifyId(itemClassifyDto.getClassifyId());
            itemClassify.setClassifyName(itemClassifyDto.getClassifyName());
            itemClassify.setCompanyId(itemClassify.getCompanyId());
            itemClassify.setPid(itemClassify.getPid());
            int result = itemClassifyMapper.updateByPrimaryKey(itemClassify);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return 0;
        }
    }

    @Override
    public ItemClassify queryByPrimaryKey(Long classifyId) {
        try {
            return itemClassifyMapper.selectByPrimaryKey(classifyId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    @Override
    public List<ItemClassify> queryItemClassifyByCompanyId(Long companyId, Long pid) {
        try {
            return itemClassifyMapper.selectClassifyByCompanyIdAndPid(companyId, pid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    @Override
    public List<ItemClassify> queryItemClassifyByPid(Long pid) {
        try {
            return itemClassifyMapper.selectClassifyByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}
