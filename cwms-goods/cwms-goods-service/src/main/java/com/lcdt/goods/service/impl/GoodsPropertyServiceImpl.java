package com.lcdt.goods.service.impl;

import com.lcdt.goods.dao.GoodsPropertyMapper;
import com.lcdt.goods.dao.GoodsPropertyValueMapper;
import com.lcdt.goods.dto.GoodsPropertyDto;
import com.lcdt.goods.dto.GoodsPropertyValueDto;
import com.lcdt.goods.exception.*;
import com.lcdt.goods.model.Goods;
import com.lcdt.goods.model.GoodsProperty;
import com.lcdt.goods.model.GoodsPropertyValue;
import com.lcdt.goods.service.GoodsPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ybq on 2017/8/31.
 */
public class GoodsPropertyServiceImpl implements GoodsPropertyService {

    @Autowired
    private GoodsPropertyMapper goodsPropertyMapper;

    @Autowired
    private GoodsPropertyValueMapper goodsPropertyValueMapper;


    @Transactional
    @Override
    public void goodsPropertyAdd(GoodsPropertyDto dto) throws GoodsPropertyExistException {
        Map map = new HashMap<String, Object>();
        map.put("propertyName", dto.getPropertyName());
        map.put("companyId", dto.getCompanyId());
        List<GoodsProperty> goodsPropertyList = goodsPropertyMapper.selectByCondition(map);
        if (goodsPropertyList != null && goodsPropertyList.size() > 0) {
            throw new GoodsPropertyExistException();
        }
        GoodsProperty vo = new GoodsProperty();
        vo.setPropertyName(dto.getPropertyName());
        vo.setPropertyValue(dto.getPropertyValue());
        vo.setCompanyId(dto.getCompanyId());
        goodsPropertyMapper.insert(vo);
    }

    @Transactional
    @Override
    public void goodsPropertyUpdate(GoodsPropertyDto dto) throws GoodsPropertyNotExistException {
        GoodsProperty vo = goodsPropertyMapper.selectByPrimaryKey(dto.getGoodsPropertyId());
        if (vo==null) {
            throw new GoodsPropertyNotExistException();
        }
        vo.setPropertyValue(dto.getPropertyValue());
        vo.setPropertyName(dto.getPropertyName());
        goodsPropertyMapper.updateByPrimaryKey(vo);
    }

    @Override
    public boolean goodsPropertyDelete(Long goodsPropertyId) throws GoodsPropertyNotExistException {
        GoodsProperty vo = goodsPropertyMapper.selectByPrimaryKey(goodsPropertyId);
        if (vo==null) {
            throw new GoodsPropertyNotExistException();
        }
        return goodsPropertyMapper.deleteByPrimaryKey(goodsPropertyId)==1 ? true :false;
    }

    @Transactional
    @Override
    public void goodsPropertyValueAdd(GoodsPropertyValueDto dto) throws GoodsPropertyValueExistException {
        Map map = new HashMap<String, Object>();
        map.put("goodsPropertyId", dto.getGoodsPropertyId());
        map.put("companyId", dto.getCompanyId());
        List<GoodsPropertyValue> goodsPropertyValueList = goodsPropertyValueMapper.selectByCondition(map);
        if (goodsPropertyValueList != null && goodsPropertyValueList.size() > 0) {
            throw new GoodsPropertyValueExistException();
        }
        GoodsPropertyValue vo = new GoodsPropertyValue();
        vo.setPropertyName(vo.getPropertyName());
        vo.setValue(dto.getValue());
        vo.setGoodsPropertyId(dto.getGoodsPropertyId());
        goodsPropertyValueMapper.insert(vo);

    }

    @Transactional
    @Override
    public void goodsPropertyValueUpdate(GoodsPropertyValueDto dto) throws GoodsPropertyValueNotExistException {
        GoodsPropertyValue vo = goodsPropertyValueMapper.selectByPrimaryKey(dto.getPropertyValueId());
        if (vo==null) {
            throw new GoodsPropertyValueNotExistException();
        }
        vo.setPropertyName(dto.getPropertyName());
        vo.setValue(dto.getValue());
        goodsPropertyValueMapper.updateByPrimaryKey(vo);
    }


    @Override
    public boolean goodsPropertyValueDelete(Long propertyValueId) throws GoodsPropertyValueNotExistException {
        GoodsPropertyValue vo = goodsPropertyValueMapper.selectByPrimaryKey(propertyValueId);
        if (vo==null) {
            throw new GoodsPropertyValueNotExistException();
        }
        return goodsPropertyValueMapper.deleteByPrimaryKey(propertyValueId)==1 ? true: false;
    }
}
