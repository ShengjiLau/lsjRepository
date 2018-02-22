package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.items.dao.ItemClassifyMapper;
import com.lcdt.items.dao.ItemsInfoMapper;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemClassifyDao;
import com.lcdt.items.model.ItemsInfo;
import com.lcdt.items.model.ItemsInfoDao;
import com.lcdt.items.service.ItemClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyqishan on 2017/10/30
 */
@Transactional
@Service
public class ItemClassifyServiceImpl implements ItemClassifyService {

    @Autowired
    private ItemClassifyMapper itemClassifyMapper;
    @Autowired
    private ItemsInfoMapper itemsInfoMapper;

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
    public int deleteItemsClassifyAndChildren(Long classifyId, Long companyId) {
        int result = 0;
        result += delRecursion(classifyId, companyId);
        return result;
    }

    /**
     * 递归删除此classifyId的分类和此id下的所有子分类
     *
     * @param classifyId
     */
    private int delRecursion(Long classifyId, Long companyId) {
        int size = 0;
        Map map=new HashMap();
        map.put("classifyId",classifyId);
        map.put("companyId",companyId);
        List<ItemsInfo> list=itemsInfoMapper.selectByClassifyIdCodeAndCompanyId(map);
        if(list!=null&&list.size()>0){
            throw new RuntimeException("此分类或其子分类已被引用，不用删除");
        }
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setCompanyId(companyId);
        itemClassify.setClassifyId(classifyId);
        itemClassify.setPid(classifyId);
        List<ItemClassify> children = itemClassifyMapper.selectClassifyByCompanyIdAndPid(itemClassify);
        if (children != null && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                size += delRecursion(children.get(i).getClassifyId(), companyId);
            }
        }
        size += itemClassifyMapper.deleteByClassifyIdAndCompanyId(itemClassify);
        return size;
    }

    @Override
    public int modifyByClassifyIdAndCompanyId(ItemClassify itemClassify) {
        int result = 0;
        result = itemClassifyMapper.updateByClassifyIdAndCompanyId(itemClassify);
        return result;
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
    public List<ItemClassify> queryItemClassifyByCompanyId(Long companyId) {
        List<ItemClassify> itemClassifyList = null;
        itemClassifyList = itemClassifyMapper.selectClassifyByCompanyId(companyId);
        return itemClassifyList;
    }

    @Override
    public PageInfo<List<ItemClassifyDao>> queryItemClassifyAndChildren(Long companyId, PageInfo pageInfo) {
        List<ItemClassifyDao> itemClassifyDaoList = null;
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setCompanyId(companyId);
        itemClassify.setPid(0L);
        PageInfo page = null;
        //使用分页工具进行分页
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        itemClassifyDaoList = itemClassifyMapper.selectClassifyAndChildren(itemClassify);
        page = new PageInfo(itemClassifyDaoList);
        return page;
    }

    @Override
    public PageInfo<List<ItemClassify>> queryClassifyByMinChildren(Long classifyId, Long companyId,PageInfo pageInfo) {
        List<ItemClassify> itemClassifyList=null;
        PageInfo page = null;
        //使用分页工具进行分页
        PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        itemClassifyList=itemClassifyMapper.selectClassifyByMinChildren(classifyId,companyId);
        page = new PageInfo(itemClassifyList);
        return page;
    }

    @Override
    public List<ItemClassify> queryIsExistClassify(ItemClassify itemClassify) {
        List<ItemClassify> itemClassifyList=null;
        itemClassifyList=itemClassifyMapper.selectIsExistClassify(itemClassify);
        return itemClassifyList;
    }

}
