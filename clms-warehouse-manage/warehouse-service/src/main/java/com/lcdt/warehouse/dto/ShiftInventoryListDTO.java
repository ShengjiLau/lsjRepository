package com.lcdt.warehouse.dto;

import java.util.List;

import com.lcdt.warehouse.entity.ShiftInventoryListDO;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月18日
 * @version
 * @Description: TODO 
 */
public class ShiftInventoryListDTO extends ShiftInventoryListDO{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 110151561515L;
	
	@ApiModelProperty("移库商品信息list")
	private List<ShiftGoodsListDTO> shiftGoodsListDTOList;
	
	@ApiModelProperty("查询的开始时间")
	private String beginTime;
	
	@ApiModelProperty("查询的结束时间")
	private String endTime;
	
	@ApiModelProperty("页码")
	private Integer pageNo;
	
	@ApiModelProperty("页数")
	private Integer pageSize;
	
	@ApiModelProperty("查询条件商品信息")
	private String goodsInfo;

	public List<ShiftGoodsListDTO> getShiftGoodsListDTOList() {
		return shiftGoodsListDTOList;
	}

	public void setShiftGoodsListDTOList(List<ShiftGoodsListDTO> shiftGoodsListDTOList) {
		this.shiftGoodsListDTOList = shiftGoodsListDTOList;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	
	
	
	
	
	

}
