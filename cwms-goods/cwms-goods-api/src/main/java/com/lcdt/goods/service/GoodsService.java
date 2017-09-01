package com.lcdt.goods.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.goods.dto.GoodsDto;
import com.lcdt.goods.dto.GoodsFeildsTemplateDto;
import com.lcdt.goods.exception.*;

import java.util.Map;

/**
 * Created by ybq on 2017/8/30.
 */
public interface GoodsService {

    /**
     * 商品录入
     * @param dto
     */
    void goodsAdd(GoodsDto dto) throws GoodsExistException, GoodsSkuExistException;

    /**
     * 商品主删除
     * @param dto
     */
    boolean goodsDelete(GoodsDto dto) throws GoodsNotExistException;


    /***
     * 删除从商品
     * @param goodsSkuId
     * @return
     */
    boolean goodsSkuDelete(Long goodsSkuId) throws GoodsSkuNotExistException;

    /***
     * 商品列表
     * @param m
     * @return
     */
    PageInfo goodsList(Map m);


    /***
     * 商品字段模版保存
     * @param dto
     */
    void goodsFieldsTemplateAdd(GoodsFeildsTemplateDto dto) throws GoodsFeildsTemplateExistException;


    /***
     * 商品字段模版删除
     * @param goodFieldsId
     */
    boolean goodsFieldsTemplateDelete(Long goodFieldsId) throws GoodsFeildsTemplateNotExistException;


    /***
     * 商品字段模版更新
     * @param dto
     */
    void goodsFieldsTemplateUpdate(GoodsFeildsTemplateDto dto) throws GoodsFeildsTemplateNotExistException;

}
