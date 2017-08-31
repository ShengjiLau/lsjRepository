package com.lcdt.goods.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.goods.dao.GoodsMapper;
import com.lcdt.goods.dao.GoodsSkuMapper;
import com.lcdt.goods.dto.GoodsDto;
import com.lcdt.goods.dto.GoodsSkuDto;
import com.lcdt.goods.exception.GoodsExistException;
import com.lcdt.goods.exception.GoodsNoExistException;
import com.lcdt.goods.exception.GoodsSkuExistException;
import com.lcdt.goods.exception.GoodsSkuNoExistException;
import com.lcdt.goods.model.Goods;
import com.lcdt.goods.model.GoodsSku;
import com.lcdt.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ybq on 2017/8/30.
 */

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper; //商品

    @Autowired
    private GoodsSkuMapper goodsSkuMapper; //商品属性


    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void goodsAdd(GoodsDto dto) throws GoodsExistException, GoodsSkuExistException {
        Map map = new HashMap<String, Object>();
        map.put("goodsCode", dto.getGoodsCode());
        map.put("companyId", dto.getCompanyId());
        List<Goods> goodsList = goodsMapper.selectByCondition(map);
        if (goodsList != null && goodsList.size() > 0) {
            throw new GoodsExistException();
        }

        //商品主信息
        Goods goods = new Goods();
        goods.setGoodsCode(dto.getGoodsCode());
        goods.setGoodsName(dto.getGoodsName());
        goods.setPrice(dto.getPrice());
        goods.setSpec(dto.getSpec());
        goods.setCompanyId(dto.getCompanyId());
        goodsMapper.insert(goods);

        //子商品信息
        List<GoodsSkuDto> list = dto.getGoodsSkuDtoList();
        if (list!=null && list.size()>0) {
            for (GoodsSkuDto dto1 : list) {
                Map map1 = new HashMap<String, Object>();
                map1.put("skuCode", dto1.getSkuCode());
                map1.put("goodsId", goods.getGoodsId());
                List<GoodsSku> goodsSkuList = goodsSkuMapper.selectByCondition(map1);
                if (goodsSkuList != null && goodsSkuList.size() > 0) {
                    throw new GoodsSkuExistException();
                }
                GoodsSku goodsSku = new GoodsSku();
                goodsSku.setGoodsId(goods.getGoodsId()); //主商品ID
                goodsSku.setCompanyId(goods.getCompanyId());
                goodsSku.setSkuCode(dto1.getSkuCode());
                goodsSku.setSkuName(dto1.getSkuName());
                goodsSku.setSkuPrice(dto1.getSkuPrice());
                goodsSku.setSkuSpec(dto1.getSkuSpec());
                goodsSkuMapper.insert(goodsSku);

            }
        }
    }

    @Transactional
    @Override
    public boolean goodsDelete(GoodsDto dto) throws GoodsNoExistException {
        Goods goods = goodsMapper.selectByPrimaryKey(dto.getGoodsId());
        if (goods == null) {
            throw new GoodsNoExistException();
        }
        Map map1 = new HashMap<String, Object>();
        map1.put("companyId", dto.getCompanyId());
        map1.put("goodsId", goods.getGoodsId());

        List<GoodsSku> goodsSkuList = goodsSkuMapper.selectByCondition(map1);
        if (goodsSkuList != null && goodsSkuList.size() > 0) {
            for (GoodsSku obj :goodsSkuList) {
                goodsSkuMapper.deleteByPrimaryKey(obj.getGoodsSkuId());
            }
        }
       return goodsMapper.deleteByPrimaryKey(dto.getGoodsId())==1 ? true : false;
    }


    @Transactional
    @Override
    public boolean goodsSkuDelete(Long goodsSkuId) throws GoodsSkuNoExistException {
        GoodsSku goodsSku = goodsSkuMapper.selectByPrimaryKey(goodsSkuId);
        if (goodsSku == null) {
            throw new GoodsSkuNoExistException();
        }
        return goodsSkuMapper.deleteByPrimaryKey(goodsSkuId)==1 ? true : false;
    }



    @Override
    public PageInfo goodsList(Map m) {
        return null;
    }
}
