package com.lcdt.items.service.impl;

import com.lcdt.items.dao.ItemsExtendPropertyMapper;
import com.lcdt.items.model.ItemsExtendProperty;
import com.lcdt.items.model.ItemsExtendPropertyDao;
import com.lcdt.items.service.ItemsExtendPropertyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-06
 */
public class ItemExtendPropertyServiceImpl implements ItemsExtendPropertyService {

    @Autowired
    ItemsExtendPropertyMapper itemsExtendPropertyMapper;

    @Override
    public int insertByBatch(List<ItemsExtendProperty> itemsExtendPropertyList) {
        return itemsExtendPropertyMapper.insertByBatch(itemsExtendPropertyList);
    }

    @Override
    public int delByBatch(ItemsExtendProperty itemsExtendProperty) {
        return itemsExtendPropertyMapper.delByBatch(itemsExtendProperty);
    }

    @Override
    public int updateByBatch(ItemsExtendPropertyDao itemsExtendPropertyDao) {
        return itemsExtendPropertyMapper.updateByBatch(itemsExtendPropertyDao);
    }
}
