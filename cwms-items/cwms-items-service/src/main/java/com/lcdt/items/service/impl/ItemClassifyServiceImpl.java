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
    public int deleteItemsClassifyAndChildren(Long classifyId) {
        int result=0;
        try{
            result+=delRecursion(classifyId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 递归删除此classifyId的分类和此id下的所有子分类
     * @param classifyId
     */
    private int delRecursion(Long classifyId){
        int size=0;
        List<ItemClassify> children=queryItemClassifyByPid(classifyId);
        if(children!=null&&children.size()>0){
            for(int i=0;i<children.size();i++){
                size+=delRecursion(children.get(i).getClassifyId());
            }
        }
        size+=deleteItemClassify(classifyId);
        return size;
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
    public List<ItemClassify> queryItemClassifyByCompanyIdAndPid(Long companyId, Long pid) {
        List<ItemClassify> itemClassifyList = null;
        try {
            ItemClassify itemClassify=new ItemClassify();
            itemClassify.setCompanyId(companyId);
            itemClassify.setPid(pid);
            itemClassifyList = itemClassifyMapper.selectClassifyByCompanyIdAndPid(itemClassify);
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

    @Override
    public List<ItemClassify> queryItemClassifyByCompanyId(Long companyId) {
        List<ItemClassify> itemClassifyList=null;
        try{
            itemClassifyList=itemClassifyMapper.selectClassifyByCompanyId(companyId);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return itemClassifyList;
        }
    }

    @Override
    public List<ItemClassify> queryItemClassifyAndAllChildren(Long companyId) {
        List<ItemClassify> itemClassifyList=null;
        try{
            ItemClassify itemClassify=new ItemClassify();
            itemClassify.setCompanyId(companyId);
            itemClassify.setPid(0L);
            itemClassifyList=itemClassifyMapper.selectClassifyByCompanyIdAndPid(itemClassify);
            for(ItemClassify list:itemClassifyList){
                list.setClassifyList(queryItemClassifyAllChildren(list.getClassifyId()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return itemClassifyList;
        }
    }

    @Override
    public String queryCassifyIdAndAllChildrenClassifyIds(Long classifyId) {
        return queryChildrenClassifyIds(classifyId);
    }

    /**
     * 递归查询子分类
     * @param pid
     * @return
     */
    private List<ItemClassify> queryItemClassifyAllChildren(Long pid){
        List<ItemClassify> itemClassifyList=null;
        try{
            itemClassifyList=itemClassifyMapper.selectClassifyByPid(pid);
            if(itemClassifyList!=null&&itemClassifyList.size()>0){
                for(ItemClassify list:itemClassifyList){
                    list.setClassifyList(queryItemClassifyAllChildren(list.getClassifyId()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return itemClassifyList;
        }
    }

    /**
     * 递归查询子分类并返回字符串
     * @param pid
     * @return
     */
    private String queryChildrenClassifyIds(Long pid){
        List<ItemClassify> itemClassifyList=null;
        StringBuilder classifyIds=new StringBuilder(pid.toString());
        try{
            itemClassifyList=itemClassifyMapper.selectClassifyByPid(pid);
            if(itemClassifyList!=null&&itemClassifyList.size()>0){
                for(ItemClassify list:itemClassifyList){
                    classifyIds.append(",").append(queryChildrenClassifyIds(list.getClassifyId()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return classifyIds.toString();
        }
    }


}
