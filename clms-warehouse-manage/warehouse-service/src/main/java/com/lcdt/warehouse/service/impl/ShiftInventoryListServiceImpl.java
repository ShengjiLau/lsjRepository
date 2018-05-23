package com.lcdt.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.InWarehouseOrderDto;
import com.lcdt.warehouse.dto.InorderGoodsInfoDto;
import com.lcdt.warehouse.dto.ShiftGoodsListDTO;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.entity.ShiftGoodsDO;
import com.lcdt.warehouse.entity.ShiftInventoryListDO;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.mapper.ShiftGoodsDOMapper;
import com.lcdt.warehouse.mapper.ShiftInventoryListDOMapper;
import com.lcdt.warehouse.service.InWarehouseOrderService;
import com.lcdt.warehouse.service.ShiftInventoryListService;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */
@Service
public class ShiftInventoryListServiceImpl implements ShiftInventoryListService {
	
	private Logger logger = LoggerFactory.getLogger(ShiftInventoryListServiceImpl.class);
	
	@Autowired
	private ShiftInventoryListDOMapper shiftInventoryListDOMapper;
	
	@Autowired
	private ShiftGoodsDOMapper shiftGoodsDOMapper;
	
	@Autowired
	private InventoryMapper inventoryMapper;
	
	 @Autowired
	private InWarehouseOrderService inWarehouseOrderService;

	/**
	 * 新增移库单所传参数为ShiftInventoryListDTO，需要三步：
	 * 1：创建新的移库单DO，复制从ShiftInventoryListDTO里的移库单属性，插入移库单，
	 * 2：每一种库存计算移库数量，修改库存
	 * 3：插入移库到新库的记录
	 */
	@Override
	@Transactional
	public int insertShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
	
		ShiftInventoryListDO shiftInventoryListDO = new ShiftInventoryListDO(); 
		
		BeanUtils.copyProperties(shiftInventoryListDTO, shiftInventoryListDO);
		shiftInventoryListDO.setCompanyId(SecurityInfoGetter.getCompanyId());
		shiftInventoryListDO.setGmtCreate(new Date());
		shiftInventoryListDO.setCreateUserId(SecurityInfoGetter.getUser().getUserId());
		shiftInventoryListDO.setCreateUser(SecurityInfoGetter.getUser().getRealName());
		int i = shiftInventoryListDOMapper.insert(shiftInventoryListDO);
		
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		
		//新建移库到新库的记录
		List<ShiftGoodsDO> shiftGoodsDOList = new ArrayList<ShiftGoodsDO>();
		int h = 0;
			
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			BigDecimal shiftPlanNum = new BigDecimal(0);
			for(int b = 0; b < shiftGoodsListDTOList.get(a).getShiftGoodsDOList().size(); b++) {
				shiftPlanNum.add(shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).getShiftPlanNum());
				shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).setShiftInventoryId(shiftInventoryListDO.getShiftId());
			}
			shiftGoodsDOList.addAll(shiftGoodsListDTOList.get(a).getShiftGoodsDOList());
			Float lockNum = shiftPlanNum.floatValue();
		    inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInventoryId(),null,lockNum);
		}
		
		int j = shiftGoodsDOMapper.insertShiftGoodsByBatch(shiftGoodsDOList);
		
		if (i > 0 && j == shiftGoodsDOList.size() && shiftGoodsListDTOList.size() == h) {
			return 1;
		}else {
			return -1;
		}
	}

	/**
	 * 完成移库单步骤有四：
	 * 1：修改移库单ShiftInventoryList的finished状态为1
	 * 2：修改库存，解除新建时锁定的库存，修改完成时的库存量
	 * 3：修改移库时的移库商品信息
	 * 4：新建移库目标库的入库单
	 */
	@Override
	@Transactional
	public int completeShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		int i = shiftInventoryListDOMapper.updateFinishedById(shiftInventoryListDTO.getShiftId(),(byte) 1);
		
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		List<ShiftGoodsDO> shiftGoodsDOList = new ArrayList<ShiftGoodsDO>();
		List<InorderGoodsInfoDto> goodsInfoDtoList = new ArrayList<InorderGoodsInfoDto>(shiftGoodsDOList.size());
		
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			BigDecimal shiftPlanNum = new BigDecimal(0);
			BigDecimal shiftNum = new BigDecimal(0);
			shiftGoodsDOList.addAll(shiftGoodsListDTOList.get(a).getShiftGoodsDOList());
			ShiftGoodsListDTO sgdl = shiftGoodsListDTOList.get(a);
			for(int b = 0; b < sgdl.getShiftGoodsDOList().size(); b++) {
				shiftPlanNum.add(shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).getShiftPlanNum());
				shiftNum.add(shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).getShiftNum());
				
				InorderGoodsInfoDto inorderGoodsInfoDto = new InorderGoodsInfoDto();
				inorderGoodsInfoDto.setBatch(sgdl.getGoodsBatch());
				inorderGoodsInfoDto.setGoodsBarcode(sgdl.getBarCode());
				inorderGoodsInfoDto.setGoodsCode(sgdl.getGoodsCode());
				inorderGoodsInfoDto.setGoodsSpec(sgdl.getGoodsSpec());
				inorderGoodsInfoDto.setUnit(sgdl.getBaseUnit());
				inorderGoodsInfoDto.setStrogeLocationCode(sgdl.getStorageLocationCode());
				inorderGoodsInfoDto.setReceivalbeAmount(sgdl.getUsableInventory());
				goodsInfoDtoList.add(inorderGoodsInfoDto);
			}
			
			Float lockNum = shiftPlanNum.floatValue();
			Float inventoryNum = shiftNum.floatValue();
		   inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInventoryId(),inventoryNum,lockNum);
		}
		
		shiftGoodsDOMapper.updateShiftGoodsByBatch(shiftGoodsDOList);	
		InWarehouseOrderDto inWarehouseOrder = new InWarehouseOrderDto();
		inWarehouseOrder.setCompanyId(SecurityInfoGetter.getCompanyId());
		inWarehouseOrder.setCreateDate(new Date());
		inWarehouseOrder.setCreateId(SecurityInfoGetter.getUser().getUserId());
		inWarehouseOrder.setCreateName(SecurityInfoGetter.getUser().getRealName());
		inWarehouseOrder.setCustomerId(shiftInventoryListDTO.getCustomerId());
		inWarehouseOrder.setCustomerName(shiftInventoryListDTO.getCustomerName());
		inWarehouseOrder.setGroupId(shiftInventoryListDTO.getGroupId());
		inWarehouseOrder.setGroupName(shiftInventoryListDTO.getGroupName());
		inWarehouseOrder.setWarehouseId(shiftInventoryListDTO.getWarehouseId());
		inWarehouseOrder.setWarehouseName(shiftInventoryListDTO.getWarehouseName());
		inWarehouseOrder.setGoodsInfoDtoList(goodsInfoDtoList);
		
		inWarehouseOrderService.addInWarehouseOrder(inWarehouseOrder);
		
		
		if (i > 0) {
			return 1;
		}else {
			return -1;
		}	
	}
	
	
	/**
	 * 依据条件查询移库单列表
	 */
	@Override
	public PageInfo<ShiftInventoryListDTO> getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO1) {
		shiftInventoryListDTO1.setCompanyId(SecurityInfoGetter.getCompanyId());
		
		if (null == shiftInventoryListDTO1.getPageNo()) {
			shiftInventoryListDTO1.setPageNo(1);
		}
		if (null == shiftInventoryListDTO1.getPageSize()) {
			shiftInventoryListDTO1.setPageSize(0);
		}
		//分页
		PageHelper.startPage(shiftInventoryListDTO1.getPageNo(), shiftInventoryListDTO1.getPageSize());
		List<ShiftInventoryListDO> shiftInventoryListDOList = shiftInventoryListDOMapper.getShiftInventoryListDOByCondition(shiftInventoryListDTO1);
		
		//如果查询结果为空，直接返回
		if (null == shiftInventoryListDOList || 0 == shiftInventoryListDOList.size()) {
			return null;
		}
		
		List<ShiftInventoryListDTO> shiftInventoryListDTOList = new ArrayList<ShiftInventoryListDTO>();
		for (int a = 0; a < shiftInventoryListDOList.size(); a++) {
			ShiftInventoryListDTO shiftInventoryListDTO2 = new ShiftInventoryListDTO();
			BeanUtils.copyProperties(shiftInventoryListDOList.get(a),shiftInventoryListDTO2);
			Long[] inventoryIds = ConvertStringToLong(shiftInventoryListDOList.get(a).getInventoryShiftedId());
			if (null != inventoryIds) {
				 List<ShiftGoodsListDTO> ShiftGoodsListDTOList = inventoryMapper.getInventoryAndGoodsInfo(inventoryIds);
				 List<ShiftGoodsDO> shiftGoodsDOList = shiftGoodsDOMapper.getShiftGoodsDOList(shiftInventoryListDOList.get(a).getShiftId());	
				 List<ShiftGoodsListDTO> ShiftGoodsListDTOList2 = new ArrayList<ShiftGoodsListDTO>();
				
				 for (int i = 0; i < ShiftGoodsListDTOList.size(); i++) {					
					 if (null != shiftInventoryListDTO1.getGoodsInfo() && !shiftInventoryListDTO1.getGoodsInfo().equals(ShiftGoodsListDTOList.get(i).getGoodsName())) {
							 ShiftGoodsListDTOList2.add(ShiftGoodsListDTOList.get(i));
							 continue;
					 }
					 
					 for (int j = 0; j < shiftGoodsDOList.size(); j++) {
						 if (ShiftGoodsListDTOList.get(i).getInventoryId().longValue() ==
								 shiftGoodsDOList.get(j).getInventoryId().longValue()) {
							 ShiftGoodsListDTOList.get(i).getShiftGoodsDOList().add(shiftGoodsDOList.get(j));
						 }
					 }
				 }
				 
				 ShiftGoodsListDTOList.removeAll(ShiftGoodsListDTOList2);
				 shiftInventoryListDTO2.setShiftGoodsListDTOList(ShiftGoodsListDTOList);
				 shiftInventoryListDTOList.add(shiftInventoryListDTO2);
			}
		}
		PageInfo<ShiftInventoryListDTO> page = new PageInfo<ShiftInventoryListDTO>(shiftInventoryListDTOList);
       return page;
    }
	
	/**
	 * 通过移库单主键id查询移库单信息
	 */
	@Override
	public ShiftInventoryListDTO getShiftInventoryListDetails(Long shiftInventoryListId) {
		ShiftInventoryListDTO shiftInventoryListDTO = new ShiftInventoryListDTO();
		ShiftInventoryListDO shiftInventoryListDO = shiftInventoryListDOMapper.selectByPrimaryKey(shiftInventoryListId);
		BeanUtils.copyProperties(shiftInventoryListDO,shiftInventoryListDTO);
		Long[] inventoryIds = ConvertStringToLong(shiftInventoryListDO.getInventoryShiftedId());
		if (null != inventoryIds) {
			 List<ShiftGoodsListDTO> shiftGoodsListDTOList = inventoryMapper.getInventoryAndGoodsInfo(inventoryIds);
			 List<ShiftGoodsDO> shiftGoodsDOList = shiftGoodsDOMapper.getShiftGoodsDOList(shiftInventoryListId);
			 for (int i = 0; i < shiftGoodsListDTOList.size(); i++) {
				 for (int j = 0; j < shiftGoodsDOList.size(); j++) {
					 if (shiftGoodsListDTOList.get(i).getInventoryId().longValue() ==
							 shiftGoodsDOList.get(j).getInventoryId().longValue()) {
						 shiftGoodsListDTOList.get(i).getShiftGoodsDOList().add(shiftGoodsDOList.get(j));
					 }
				 }
			 }
			 shiftInventoryListDTO.setShiftGoodsListDTOList(shiftGoodsListDTOList);
		}
		
		return shiftInventoryListDTO;
	}
	
	/**
	 * 通过移库单主键id取消移库单
	 */
	@Override
	public int deleteShiftInventoryList(Long shiftInventoryListId) {
		 ShiftInventoryListDO shiftInventoryListDO = shiftInventoryListDOMapper.selectByPrimaryKey(shiftInventoryListId);
		 Long [] inventoryIds = ConvertStringToLong(shiftInventoryListDO.getInventoryShiftedId());
		 List<ShiftGoodsDO> shiftGoodsDOList = shiftGoodsDOMapper.getShiftGoodsDOList(shiftInventoryListId);
		
		 for (int i = 0; i < inventoryIds.length; i++) {
			 BigDecimal shiftPlanNum = new BigDecimal(0);
			for (int j = 0; j < shiftGoodsDOList.size(); j++) {
				if (inventoryIds[i].longValue() == shiftGoodsDOList.get(j).getInventoryId().longValue()) {
					shiftPlanNum.add(shiftGoodsDOList.get(j).getShiftPlanNum());
				}
			}
			Float lockNum = 0 - shiftPlanNum.floatValue();
			inventoryMapper.updateInventoryLockNum(inventoryIds[i],null,lockNum);
		 }
		int result = shiftInventoryListDOMapper.updateFinishedById(shiftInventoryListId,(byte) 2);
		
		return result;
	}
	
	
	
	/**
	 * 将规定格式的字符串转化为Long类型数组
	 * @param String
	 * @return Long[]
	 */
	public Long[] ConvertStringToLong(String s) {
		String patten="^(([0-9]+)([,])){0,}$";
		String str = s+",";
		boolean b= Pattern.matches(patten, str);
		if (b) {
			String [] ss = str.split(",");
			Long [] group = new Long[ss.length];
			for (int i = 0; i < ss.length; i++) {
				group[i]=Long.valueOf(ss[i]);
			}
			return group;
		}else {
			return null;
		}
	}
	
	
	
	
	
	
	
	

}
