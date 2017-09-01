package com.lcdt.goods.service.impl;

import com.github.pagehelper.PageInfo;
import com.lcdt.goods.dao.GoodsFeildsTemplateMapper;
import com.lcdt.goods.dao.GoodsMapper;
import com.lcdt.goods.dao.GoodsSkuMapper;
import com.lcdt.goods.dto.GoodsDto;
import com.lcdt.goods.dto.GoodsFeildsTemplateDto;
import com.lcdt.goods.dto.GoodsSkuDto;
import com.lcdt.goods.exception.*;
import com.lcdt.goods.model.Goods;
import com.lcdt.goods.model.GoodsFeildsTemplate;
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

    @Autowired
    private GoodsFeildsTemplateMapper goodsFeildsTemplateMapper; //模版字段


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
    public boolean goodsDelete(GoodsDto dto) throws GoodsNotExistException {
        Goods goods = goodsMapper.selectByPrimaryKey(dto.getGoodsId());
        if (goods == null) {
            throw new GoodsNotExistException();
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
    public boolean goodsSkuDelete(Long goodsSkuId) throws GoodsSkuNotExistException {
        GoodsSku goodsSku = goodsSkuMapper.selectByPrimaryKey(goodsSkuId);
        if (goodsSku == null) {
            throw new GoodsSkuNotExistException();
        }
        return goodsSkuMapper.deleteByPrimaryKey(goodsSkuId)==1 ? true : false;
    }


    @Override
    public PageInfo goodsList(Map m) {
        return null;
    }


    @Transactional
    @Override
    public void goodsFieldsTemplateAdd(GoodsFeildsTemplateDto dto) throws GoodsFeildsTemplateExistException {
        Map map = new HashMap<String, Object>();
        map.put("templateName", dto.getTemplateName());
        map.put("companyId", dto.getCompanyId());
        List<Goods> goodsList = goodsMapper.selectByCondition(map);
        if (goodsList != null && goodsList.size() > 0) {
            throw new GoodsFeildsTemplateExistException();
        }
        GoodsFeildsTemplate vo = new GoodsFeildsTemplate();
        vo.setTemplateName(dto.getTemplateName());
        vo.setTemplateContent(vo.getTemplateContent());
        vo.setCompanyId(vo.getCompanyId());
        goodsFeildsTemplateMapper.insert(vo);
    }

    @Transactional
    @Override
    public boolean goodsFieldsTemplateDelete(Long goodFieldsId) throws GoodsFeildsTemplateNotExistException {
        GoodsFeildsTemplate vo = goodsFeildsTemplateMapper.selectByPrimaryKey(goodFieldsId);
        if (vo==null) {
            throw new GoodsFeildsTemplateNotExistException();
        }
        return goodsFeildsTemplateMapper.deleteByPrimaryKey(goodFieldsId)==1 ? true : false;
    }

    @Transactional
    @Override
    public void goodsFieldsTemplateUpdate(GoodsFeildsTemplateDto dto) throws GoodsFeildsTemplateNotExistException {
        GoodsFeildsTemplate vo = goodsFeildsTemplateMapper.selectByPrimaryKey(dto.getGoodFieldsId());
        if (vo==null) {
            throw new GoodsFeildsTemplateNotExistException();
        }
        vo.setTemplateName(dto.getTemplateName());
        vo.setTemplateContent(dto.getTemplateContent());
        goodsFeildsTemplateMapper.updateByPrimaryKey(vo);
    }
}
