package com.lcdt.contract.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.ItemInfo;
import com.lcdt.contract.service.ItemInfoService;
import com.lcdt.contract.web.dto.ItemInfoDto;
import com.lcdt.contract.web.dto.PageBaseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 订单添加商品时条件查询
 * @author Sheng-ji Lau
 * @date 2018年3月7日下午2:29:04
 * @version
 */

@RestController
@Api(value="商品条件查询Api",description="商品条件查询")
@RequestMapping("/itemInfo")
public class ItemInfoApi {
	
	@Autowired
	private ItemInfoService itemInfoService;
	
	@ApiOperation(value="相关商品列表",notes="相关商品信息")
	@GetMapping("/getItem")
	public PageBaseDto getItemInfo(ItemInfoDto itemInfoDto) {
		Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
		itemInfoDto.setCompanyId(companyId);
		Long createId=SecurityInfoGetter.getUser().getUserId();
		itemInfoDto.setCreateId(createId);
		List<ItemInfo> itemInfoList=itemInfoService.getItemList(itemInfoDto);
		PageBaseDto pageBaseDto =new PageBaseDto(itemInfoList);
		return pageBaseDto;
	}
	
	
	
	
	
	
	

}
