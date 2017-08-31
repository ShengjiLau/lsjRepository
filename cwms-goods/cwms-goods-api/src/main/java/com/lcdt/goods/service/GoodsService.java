package com.lcdt.goods.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.goods.dto.GoodsDto;
import com.lcdt.goods.exception.GoodsExistException;
import com.lcdt.goods.exception.GoodsNoExistException;
import com.lcdt.goods.exception.GoodsSkuExistException;
import com.lcdt.goods.exception.GoodsSkuNoExistException;

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
    boolean goodsDelete(GoodsDto dto) throws GoodsNoExistException;


    /***
     * 删除从商品
     * @param goodsSkuId
     * @return
     */
    boolean goodsSkuDelete(Long goodsSkuId) throws GoodsSkuNoExistException;



    /***
     * 商品列表
     * @param m
     * @return
     */
    PageInfo goodsList(Map m);



}
