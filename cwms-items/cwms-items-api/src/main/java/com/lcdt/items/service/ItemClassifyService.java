package com.lcdt.items.service;

import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemClassifyDao;

import java.util.List;

/**
 * 商品分类服务 ItemClassifyService
 * Created by lyqishan on 2017/10/30
 */

public interface ItemClassifyService {

    /**
     * 增加商品分类 ItemClassify
     *
     * @param itemClassify
     * @return
     */
    ItemClassify addItemClassify(ItemClassify itemClassify);

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
     * @param itemClassify
     * @return
     */
    int modifyByPrimaryKey(ItemClassify itemClassify);

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

    /**
     * 获得此企业下的所有分类树状结构
     * @param companyId
     * @return
     */
    List<ItemClassifyDao> queryItemClassifyAndChildren(Long companyId);

    /**
     * 根据分类 classifyId 查询此分类id和所有子分类id ,以 , 分开，组装成字符串（ 1,2,3,4 ）
     * @param classifyId
     * @return
     */
    String queryCassifyIdAndAllChildrenClassifyIds(Long classifyId);

    List<ItemClassify> testFunction(Long classifyId);

}
