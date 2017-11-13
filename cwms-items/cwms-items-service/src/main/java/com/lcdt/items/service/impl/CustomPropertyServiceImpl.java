package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.dao.CustomPropertyMapper;
import com.lcdt.items.dao.ItemClassifyMapper;
import com.lcdt.items.dao.ItemsExtendPropertyMapper;
import com.lcdt.items.model.CustomProperty;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemsExtendProperty;
import com.lcdt.items.model.ItemsExtendPropertyDao;
import com.lcdt.items.service.CustomPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-06
 */
@Service
@Transactional
public class CustomPropertyServiceImpl implements CustomPropertyService{

    @Autowired
    private CustomPropertyMapper customPropertyMapper;

    @Autowired
    private ItemsExtendPropertyMapper itemsExtendPropertyMapper;

    @Autowired
    private ItemClassifyMapper itemClassifyMapper;

    @Override
    public List<CustomProperty> customPropertyList(Long companyId) {
        return customPropertyMapper.selectByCompanyId(companyId);
    }

    @Override
    public int insertCustomProperty(CustomProperty customProperty) {
        List customPropertyList =  customPropertyMapper.selectByPropertyName(customProperty); //获取属性名称是否重复
        if(customPropertyList.size()>0){    //重复直接返回-1，交给上层处理
            return -1;
        }
        //查询企业下的所有商品分类
        List<ItemClassify> itemClassifyList = itemClassifyMapper.selectClassifyByCompanyId(customProperty.getCompanyId());
        List<ItemsExtendProperty> itemsExtendPropertyList = new ArrayList<ItemsExtendProperty>();

        /*循环取出商品分类id和分类名，并set到对象，然后add到新增的itemsExtendPropertyList对象里*/
        for (int i = 0; i < itemClassifyList.size(); i++) {
            /*new一个商品扩展属性对象，并set相关需要插入的数据*/
            ItemsExtendProperty itemsExtendProperty = new ItemsExtendProperty();
            itemsExtendProperty.setCompanyId(customProperty.getCompanyId());
            itemsExtendProperty.setTitle(customProperty.getPropertyName());
            itemsExtendProperty.setFieldType(customProperty.getPropertyType());
            /**！！！这个地方注意一下，现在是默认0，就是文本框，后续如果有其他类型，这个地方对应获取相应的值*/
            itemsExtendProperty.setControlName(new Short("0"));
            itemsExtendProperty.setClassifyId(itemClassifyList.get(i).getClassifyId());
            itemsExtendProperty.setClassifyName(itemClassifyList.get(i).getClassifyName());
            itemsExtendPropertyList.add(itemsExtendProperty);
        }
        customPropertyMapper.insert(customProperty);    //插入自定义属性
        itemsExtendPropertyMapper.insertByBatch(itemsExtendPropertyList);   //批量插入商品扩展属性数据
        return 0;
    }

    @Override
    public int updateByCustomId(CustomProperty customProperty) {
        List customPropertyList =  customPropertyMapper.selectByPropertyName(customProperty); //获取属性名称是否重复
        if(customPropertyList.size()>0){    //重复直接返回-1，交给上层处理
            return -1;
        }
        CustomProperty oldCustomProrerty = customPropertyMapper.selectByPrimaryKey(customProperty.getCustomId());   //查询需要修改的自定义属性数据
        /*声明一个商品属性扩展dao对象，并set注入相关更新的数据和条件*/
        ItemsExtendPropertyDao itemsExtendPropertyDao = new ItemsExtendPropertyDao();
        itemsExtendPropertyDao.setTitle(customProperty.getPropertyName());  //要更新的内容，属性名
        itemsExtendPropertyDao.setTitleExtend(oldCustomProrerty.getPropertyName()); //被更新的属性名+companyId定位修改数据
        itemsExtendPropertyDao.setCompanyId(customProperty.getCompanyId());     //注入companyId

        int rows = customPropertyMapper.updateByPrimaryKey(customProperty);     //更新自定义属性表数据
        itemsExtendPropertyMapper.updateByBatch(itemsExtendPropertyDao);        //更新商品扩展表数据
        return rows;
    }

    @Override
    public int delByCustomId(Long customId) {
        CustomProperty customProperty =  customPropertyMapper.selectByPrimaryKey(customId); //获取该条自定义属性内容
        /*构造商品扩展对象并set注入对应的属性名称和公司id*/
        ItemsExtendProperty itemsExtendProperty = new ItemsExtendProperty();
        itemsExtendProperty.setTitle(customProperty.getPropertyName());
        itemsExtendProperty.setCompanyId(customProperty.getCompanyId());

        int rows = customPropertyMapper.deleteByPrimaryKey(customId);   //删除自定义属性记录
        itemsExtendPropertyMapper.delByBatch(itemsExtendProperty);  //同时删除对应商品扩展属性

        return rows;    //不为零则删除成功
    }
}
