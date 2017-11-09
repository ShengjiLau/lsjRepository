package com.lcdt.items.service;

import com.lcdt.items.dto.ItemClassifyDto;
import com.lcdt.items.model.ItemClassify;

import java.util.List;

/**
 * 商品分类服务 ItemClassifyService
 * Created by lyqishan on 2017/10/30
 */

public interface ItemClassifyService {

    /**
     * 增加商品分类 ItemClassify
     *
     * @param itemClassifyDto
     * @return
     */
    int addItemClassify(ItemClassifyDto itemClassifyDto);

    /**
     * 根据商品分类classifyId删除商品分类
     *
     * @param classifyId
     * @return
     */
    int deleteItemClassify(Long classifyId);


    /**
     * 删除商品分类以及此分类下的所有子分类
     * @param classifyId
     * @return
     */
    int deleteItemsClassifyAndChildren(Long classifyId);

    /**
     * 根据商品classifyId修改商品分类
     *
     * @param itemClassifyDto
     * @return
     */
    int modifyByPrimaryKey(ItemClassifyDto itemClassifyDto);

    /**
     * 根据classifyId查询商品分类
     *
     * @param classifyId
     * @return
     */
    ItemClassify queryByPrimaryKey(Long classifyId);

    /**
     * 根据companyId，和 pid查询一级分类，也可以查询子分类
     * @param companyId
     * @param pid
     * @return
     */
    List<ItemClassify> queryItemClassifyByCompanyIdAndPid(Long companyId,Long pid);

    /**
     * 根据父 pid 查询子类
     * @param pid
     * @return
     */
    List<ItemClassify> queryItemClassifyByPid(Long pid);


    /**
     * 根据企业companyId,查询所有ItemsClassify
     * @param companyId
     * @return
     */
    List<ItemClassify> queryItemClassifyByCompanyId(Long companyId);


}
