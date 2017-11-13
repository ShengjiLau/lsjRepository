package com.lcdt.items.service.impl;

import com.lcdt.items.dao.CustomValueMapper;
import com.lcdt.items.model.CustomValue;
import com.lcdt.items.service.CustomValueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-13
 */
public class CustomValueServiceImpl implements CustomValueService{

    @Autowired
    private CustomValueMapper customValueMapper;

    @Override
    public int addForBatch(List<CustomValue> customValueList) {

        return customValueMapper.insertForBatch(customValueList);
    }

    @Override
    public List<CustomValue> getItemAndSubItem(String itemId, String subItemIds) {
        return customValueMapper.selectItemAndSubItem(itemId,subItemIds);
    }

    @Override
    public int updateForBatch(List<CustomValue> customValueList) {
        int rows = 0;
        for(CustomValue customValue : customValueList){
           rows = rows + customValueMapper.updateByPrimaryKey(customValue);
        }
        return rows;
    }
}
