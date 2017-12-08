package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemClassifyDao;
import com.lcdt.items.model.ItemsInfoDao;

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
     * 删除商品分类以及此分类下的所有子分类
     * @param classifyId
     * @return
     */
    int deleteItemsClassifyAndChildren(Long classifyId,Long companyId);

    /**
     * 根据商品classifyId修改商品分类
     *
     * @param itemClassify
     * @return
     */
    int modifyByClassifyIdAndCompanyId(ItemClassify itemClassify);

    /**
     * 根据companyId，和 pid查询一级分类，也可以查询子分类
     * @param companyId
     * @param pid
     * @return
     */
    List<ItemClassify> queryItemClassifyByCompanyIdAndPid(Long companyId,Long pid);


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
    PageInfo<List<ItemClassifyDao>> queryItemClassifyAndChildren(Long companyId, PageInfo pageInfo);


    PageInfo<List<ItemClassify>> queryClassifyByMinChildren(Long classifyId,Long companyId,PageInfo pageInfo);

}
