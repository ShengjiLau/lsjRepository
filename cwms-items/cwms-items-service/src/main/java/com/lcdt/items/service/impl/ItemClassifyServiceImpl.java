package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.dao.ItemClassifyMapper;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemClassifyDao;
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
    public ItemClassify addItemClassify(ItemClassify itemClassify) {
        int result = itemClassifyMapper.insert(itemClassify);
        if (result > 0) {
            return itemClassify;
        } else {
            return null;
        }

    }

    @Override
    public int deleteItemClassify(Long classifyId) {
        int result = 0;
        result = itemClassifyMapper.deleteByPrimaryKey(classifyId);
        return result;
    }

    @Override
    public int deleteItemsClassifyAndChildren(Long classifyId) {
        int result = 0;
        result += delRecursion(classifyId);
        return result;
    }

    /**
     * 递归删除此classifyId的分类和此id下的所有子分类
     *
     * @param classifyId
     */
    private int delRecursion(Long classifyId) {
        int size = 0;
        List<ItemClassify> children = queryItemClassifyByPid(classifyId);
        if (children != null && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                size += delRecursion(children.get(i).getClassifyId());
            }
        }
        size += deleteItemClassify(classifyId);
        return size;
    }

    @Override
    public int modifyByPrimaryKey(ItemClassify itemClassify) {
        int result = 0;
        result = itemClassifyMapper.updateByPrimaryKey(itemClassify);
        return result;
    }

    @Override
    public ItemClassify queryByPrimaryKey(Long classifyId) {
        ItemClassify itemClassify = itemClassifyMapper.selectByPrimaryKey(classifyId);
        return itemClassify;
    }

    @Override
    public List<ItemClassify> queryItemClassifyByCompanyIdAndPid(Long companyId, Long pid) {
        List<ItemClassify> itemClassifyList = null;
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setCompanyId(companyId);
        itemClassify.setPid(pid);
        itemClassifyList = itemClassifyMapper.selectClassifyByCompanyIdAndPid(itemClassify);
        return itemClassifyList;
    }

    @Override
    public List<ItemClassify> queryItemClassifyByPid(Long pid) {
        List<ItemClassify> itemClassifyList = null;
        itemClassifyList = itemClassifyMapper.selectClassifyByPid(pid);
        return itemClassifyList;
    }

    @Override
    public List<ItemClassify> queryItemClassifyByCompanyId(Long companyId) {
        List<ItemClassify> itemClassifyList = null;
        itemClassifyList = itemClassifyMapper.selectClassifyByCompanyId(companyId);
        return itemClassifyList;
    }

    @Override
    public List<ItemClassifyDao> queryItemClassifyAndChildren(Long companyId) {
        List<ItemClassifyDao> itemClassifyDaoList = null;
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setCompanyId(companyId);
        itemClassify.setPid(0L);
        itemClassifyDaoList = itemClassifyMapper.selectClassifyAndChildren(itemClassify);
        return itemClassifyDaoList;
    }

    @Override
    public String queryCassifyIdAndAllChildrenClassifyIds(Long classifyId) {
        return queryChildrenClassifyIds(classifyId);
    }

    @Override
    public List<ItemClassify> testFunction(Long classifyId) {

        return itemClassifyMapper.testFunction(classifyId);
    }

    /**
     * 递归查询子分类并返回字符串
     *
     * @param pid
     * @return
     */
    private String queryChildrenClassifyIds(Long pid) {
        List<ItemClassify> itemClassifyList = null;
        StringBuilder classifyIds = new StringBuilder(pid.toString());
        itemClassifyList = itemClassifyMapper.selectClassifyByPid(pid);
        if (itemClassifyList != null && itemClassifyList.size() > 0) {
            for (ItemClassify list : itemClassifyList) {
                classifyIds.append(",").append(queryChildrenClassifyIds(list.getClassifyId()));
            }
        }
        return classifyIds.toString();
    }

}
