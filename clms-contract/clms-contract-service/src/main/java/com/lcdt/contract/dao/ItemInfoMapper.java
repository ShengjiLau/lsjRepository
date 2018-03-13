package com.lcdt.contract.dao;

import java.util.List;

import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.model.WarehouseInfo;
import com.lcdt.contract.web.dto.ItemInfoDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月7日上午11:28:55
 * @version
 */
public interface ItemInfoMapper {
	
	/**
     * 查询商品信息
     * @param ItemInfoDto
     * @return List
     */
	List<ItemInfo> SelectItemInfo(ItemInfoDto itemInfoDto);
	
	
	/**
     * 查询业务组信息
     * @param Long
     * @return List
     */
	List<String> getGroupsByCompanyId(Long CompanyId);
	
	
	/**
     * 模糊查询供应商名称
     * @param String
     * @return List
     */
//    List<String> getSupplierList(String roughName);
	
	
	/**
     * 查询我的仓库列表
     * @param Integer
     * @return List
     */
	List<WarehouseInfo> getWarehouseList(Long CompanyId,Long createId);
	
	
	
	
	
	
	
	
	
	
}
