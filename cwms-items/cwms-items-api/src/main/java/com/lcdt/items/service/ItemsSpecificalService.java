package com.lcdt.items.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.items.dto.ItemsSpecificalDto;
import com.lcdt.items.model.ItemsSpecifical;

import java.util.List;

/**
 * Created by lyqishan on 2017/11/9
 */

public interface ItemsSpecificalService {
    /**
     * 增加子商品规格
     * @param itemsSpecificalDto
     * @return
     */
    int addItemsSpecifical(ItemsSpecificalDto itemsSpecificalDto);

    /**
     * 删除子商品规格
     * @param specId
     * @return
     */
    int deleteItemsSpecifical(Long specId);

    /**
     * 修改子商品规格
     * @param itemsSpecificalDto
     * @return
     */
    int modifyItemsSpecifical(ItemsSpecificalDto itemsSpecificalDto);

    /**
     * 根据子商品规格 specId 查询单个子商品规格
     * @param specId
     * @return
     */
    ItemsSpecifical queryItemsSpecifical(Long specId);

    /**
     * 根据companyId查询此企业下的所有子商品规格
     * @param companyId
     * @param pageInfo
     * @return
     */
    List<ItemsSpecifical> queryItemsSpecificalListByCompanyId(Long companyId, PageInfo pageInfo);
}
