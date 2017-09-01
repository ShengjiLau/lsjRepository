package com.lcdt.goods.service;

import com.lcdt.goods.dto.GoodsPropertyDto;
import com.lcdt.goods.dto.GoodsPropertyValueDto;
import com.lcdt.goods.exception.GoodsPropertyExistException;
import com.lcdt.goods.exception.GoodsPropertyNotExistException;
import com.lcdt.goods.exception.GoodsPropertyValueExistException;
import com.lcdt.goods.exception.GoodsPropertyValueNotExistException;
import com.lcdt.goods.model.GoodsPropertyValue;

/**
 * Created by ybq on 2017/8/31.
 */
public interface GoodsPropertyService {


    /***
     * 属性增加
     * @param dto
     */
    void goodsPropertyAdd(GoodsPropertyDto dto) throws GoodsPropertyExistException;


    /***
     * 属性编辑
     * @param dto
     */
    void goodsPropertyUpdate(GoodsPropertyDto dto) throws GoodsPropertyNotExistException;

    /***
     * 属性删除
     * @param goodsPropertyId
     * @return
     */
    boolean goodsPropertyDelete(Long goodsPropertyId) throws GoodsPropertyNotExistException;


    /***
     * 属性值增加
     * @param dto
     */
    void goodsPropertyValueAdd(GoodsPropertyValueDto dto) throws GoodsPropertyValueExistException;


    /***
     * 属性值增加
     * @param dto
     */
    void goodsPropertyValueUpdate(GoodsPropertyValueDto dto) throws GoodsPropertyValueNotExistException;


    /***
     * 属性值删除
     * @param propertyValueId
     * @return
     */
    boolean goodsPropertyValueDelete(Long propertyValueId) throws GoodsPropertyValueNotExistException;

}
