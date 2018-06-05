package com.lcdt.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
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
import com.lcdt.warehouse.vo.ShiftInventoryListVO;

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
	@Transactional(isolation=Isolation.REPEATABLE_READ,timeout=60,propagation=Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception"})
	public int insertShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		
		//新建一个移库单Model,并复制ShiftInventoryListDTO里的相关属性
		ShiftInventoryListDO shiftInventoryListDO = new ShiftInventoryListDO(); 
		BeanUtils.copyProperties(shiftInventoryListDTO, shiftInventoryListDO);
		shiftInventoryListDO.setCompanyId(SecurityInfoGetter.getCompanyId());
		shiftInventoryListDO.setGmtCreate(new Date());
		shiftInventoryListDO.setCreateUserId(SecurityInfoGetter.getUser().getUserId());
		shiftInventoryListDO.setCreateUser(SecurityInfoGetter.getUser().getRealName());
		shiftInventoryListDO.setFinished((byte) 0);
		int i = shiftInventoryListDOMapper.insert(shiftInventoryListDO);
		
		//取得ShiftGoodsListDTO集合
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		
		//新建移库到新库的记录,不涉及遍历，此处采用LinkedList
		List<ShiftGoodsDO> shiftGoodsDOList = new LinkedList<ShiftGoodsDO>();
		int h = 0;
			
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			BigDecimal shiftPlanNum = new BigDecimal(0);
			List<ShiftGoodsDO> shiftGoodsDOList1 = shiftGoodsListDTOList.get(a).getShiftGoodsDOList();
			for(int b = 0; b < shiftGoodsDOList1.size(); b++) {
				ShiftGoodsDO shiftGoodsDO = shiftGoodsDOList1.get(b);
				//计算计划商品移库数量
				shiftPlanNum.add(shiftGoodsDO.getShiftPlanNum());
				shiftGoodsDO.setShiftInventoryId(shiftInventoryListDO.getShiftId());
			}
			shiftGoodsDOList.addAll(shiftGoodsDOList1);
			Float lockNum = shiftPlanNum.floatValue();
			//修改库存中库存总量以及库存锁定量
		    h = inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInventoryId(),null,lockNum);
		}
		//数据库插入新的移库商品信息列表
		int j = shiftGoodsDOMapper.insertShiftGoodsByBatch(shiftGoodsDOList);
		
		logger.debug("插入的移库商品信息条数为"+j);
		logger.debug("插入的移库单数量为"+i);
		logger.debug("修改的库存数量为"+h);
		
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
	@Transactional(isolation=Isolation.REPEATABLE_READ,timeout=60,propagation=Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception"})
	public int completeShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		//将移库单的状态修改为1，即完成状态
		int i = shiftInventoryListDOMapper.updateFinishedById(shiftInventoryListDTO.getShiftId(),(byte) 1);
		
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		List<ShiftGoodsDO> shiftGoodsDOList = new LinkedList<ShiftGoodsDO>();
		//创建一个入库单对应的商品信息集合
		List<InorderGoodsInfoDto> goodsInfoDtoList = new LinkedList<InorderGoodsInfoDto>();
		
		//遍历所有的ShiftGoodsListDTO
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			ShiftGoodsListDTO sgdl = shiftGoodsListDTOList.get(a);
			List<ShiftGoodsDO> shiftGoodsDOList1 = sgdl.getShiftGoodsDOList();
			BigDecimal shiftPlanNum = new BigDecimal(0);
			BigDecimal shiftNum = new BigDecimal(0);
			
			//将所有的ShiftGoodsDO添加到shiftGoodsDOList
			shiftGoodsDOList.addAll(shiftGoodsDOList1);
			
			for(int b = 0; b < shiftGoodsDOList1.size(); b++) {
				ShiftGoodsDO shiftGoodsDO = shiftGoodsDOList1.get(b);
				//计算计划和实际移库商品数量
				shiftPlanNum.add(shiftGoodsDO.getShiftPlanNum());
				shiftNum.add(shiftGoodsDO.getShiftNum());
				
				//创建入库商品信息类
				InorderGoodsInfoDto inorderGoodsInfoDto = new InorderGoodsInfoDto();
				inorderGoodsInfoDto.setBatch(sgdl.getGoodsBatch());
				inorderGoodsInfoDto.setGoodsBarcode(sgdl.getBarCode());
				inorderGoodsInfoDto.setGoodsCode(sgdl.getGoodsCode());
				inorderGoodsInfoDto.setGoodsSpec(sgdl.getGoodsSpec());
				inorderGoodsInfoDto.setUnit(sgdl.getBaseUnit());
				inorderGoodsInfoDto.setStorageLocationCode(sgdl.getStorageLocationCode());
				inorderGoodsInfoDto.setReceivalbeAmount(sgdl.getUsableInventory());
				//将入库商品信息类加入到商品信息集合
				goodsInfoDtoList.add(inorderGoodsInfoDto);
			}
			
			Float lockNum = shiftPlanNum.floatValue();
			Float inventoryNum = shiftNum.floatValue();
			//修改库存的库存总量和锁定库存量
		   inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInventoryId(),inventoryNum,lockNum);
		}
		//修改移库商品信息
		shiftGoodsDOMapper.updateShiftGoodsByBatch(shiftGoodsDOList);
		
		//创建一个新的入库单，并添加入库单的各种属性
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
		//添加入库单
		inWarehouseOrderService.addInWarehouseOrder(inWarehouseOrder);
		
		
		if (i > 0) {
			return 1;
		}else {
			return -1;
		}	
	}
	
	
	/**
	 * 依据条件查询移库单列表
	 * 1：通过查询条件找到对应的ShiftInventoryListDO集合
	 * 2：遍历ShiftInventoryListDO集合，找到对应的库存商品信息和移库商品信息
	 * 3：如果库存商品信息和查询条件不一致，则去掉相应的ShiftInventoryListDTO
	 */
	@Override
	@Transactional(readOnly=true)
	public PageInfo<ShiftInventoryListDTO> getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO1) {
		shiftInventoryListDTO1.setCompanyId(SecurityInfoGetter.getCompanyId());
		
		if (null == shiftInventoryListDTO1.getPageNo() || 0 ==shiftInventoryListDTO1.getPageNo()) {
			shiftInventoryListDTO1.setPageNo(1);
		}
		if (null == shiftInventoryListDTO1.getPageSize()) {
			shiftInventoryListDTO1.setPageSize(0);
		}
		//分页
		PageHelper.startPage(shiftInventoryListDTO1.getPageNo(), shiftInventoryListDTO1.getPageSize());
		List<ShiftInventoryListDO> shiftInventoryListDOList = shiftInventoryListDOMapper.getShiftInventoryListDOByCondition(shiftInventoryListDTO1);
		
		logger.debug("查询得到的移库单数量为"+shiftInventoryListDOList.size());
		//如果查询结果为空，直接返回
		if (null == shiftInventoryListDOList || 0 == shiftInventoryListDOList.size()) {
			PageInfo<ShiftInventoryListDTO> page = new PageInfo<ShiftInventoryListDTO>();
			return page;
		}
		
		List<ShiftInventoryListDTO> shiftInventoryListDTOList = new ArrayList<ShiftInventoryListDTO>();
		//遍历所有的ShiftInventoryListDO
		for (int a = 0; a < shiftInventoryListDOList.size(); a++) {
			ShiftInventoryListDO shiftInventoryListDO = shiftInventoryListDOList.get(a);
			ShiftInventoryListDTO shiftInventoryListDTO2 = new ShiftInventoryListDTO();
			BeanUtils.copyProperties(shiftInventoryListDO,shiftInventoryListDTO2);
			
			Long[] inventoryIds = ConvertStringToLong(shiftInventoryListDO.getInventoryShiftedId());
			if (null != inventoryIds) {
				//得到库存与商品信息
				 List<ShiftGoodsListDTO> ShiftGoodsListDTOList = inventoryMapper.getInventoryAndGoodsInfo(inventoryIds);
				//得到所有相关的移库商品信息集合

				 List<ShiftGoodsDO> shiftGoodsDOList1 = shiftGoodsDOMapper.getShiftGoodsDOList(shiftInventoryListDO.getShiftId());
				 List<ShiftGoodsListDTO> ShiftGoodsListDTOList2 = new ArrayList<ShiftGoodsListDTO>();
				
				 for (int i = 0; i < ShiftGoodsListDTOList.size(); i++) {
					 List<ShiftGoodsDO> shiftGoodsDOList2 = new LinkedList<ShiftGoodsDO>();
					 //如果查询条件传过来的goodsInfo与ShiftGoodsListDTO的商品name不相同，则去除掉相关的ShiftGoodsListDTO，并跳出此次循环
					 if (null != shiftInventoryListDTO1.getGoodsInfo() && !shiftInventoryListDTO1.getGoodsInfo().equals(ShiftGoodsListDTOList.get(i).getGoodsName())) {
							 ShiftGoodsListDTOList2.add(ShiftGoodsListDTOList.get(i));
							 continue;
					 }
					 //遍历所有的移库商品信息shiftGoodsDO，如果shiftGoodsDO里存的InventoryId与ShiftGoodsListDTO里存的InventoryId相同，则将shiftGoodsDO添加到ShiftGoodsListDTO里的ShiftGoodsDOList
					 if (null != shiftGoodsDOList1 && 0 != shiftGoodsDOList1.size()) {
						 for (int j = 0; j < shiftGoodsDOList1.size(); j++) {
							 if (shiftGoodsDOList1.get(j).getInventoryId().longValue() == ShiftGoodsListDTOList.get(i).getInventoryId().longValue()) {
								 shiftGoodsDOList2.add(shiftGoodsDOList1.get(j));
							 }
						 } 
					 }
					 ShiftGoodsListDTOList.get(i).setShiftGoodsDOList(shiftGoodsDOList2);
				 }
				 
				 ShiftGoodsListDTOList.removeAll(ShiftGoodsListDTOList2);
				 shiftInventoryListDTO2.setShiftGoodsListDTOList(ShiftGoodsListDTOList);
			}
			 shiftInventoryListDTOList.add(shiftInventoryListDTO2);
		}
		
		PageInfo<ShiftInventoryListDTO> page = new PageInfo<ShiftInventoryListDTO>(shiftInventoryListDTOList);
		return page;
    }
	
	
	/**
	 * 通过移库单主键id查询移库单信息
	 */
	@Override
	@Transactional(readOnly=true)
	public ShiftInventoryListDTO getShiftInventoryListDetails(Long shiftInventoryListId) {
		//创建一个新的ShiftInventoryListDTO
		ShiftInventoryListDTO shiftInventoryListDTO = new ShiftInventoryListDTO();
		ShiftInventoryListDO shiftInventoryListDO = shiftInventoryListDOMapper.selectByPrimaryKey(shiftInventoryListId);
		BeanUtils.copyProperties(shiftInventoryListDO,shiftInventoryListDTO);
		
		Long[] inventoryIds = ConvertStringToLong(shiftInventoryListDO.getInventoryShiftedId());
		if (null != inventoryIds) {
			//得到与移库单相关的所有库存与商品信息
			 List<ShiftGoodsListDTO> shiftGoodsListDTOList = inventoryMapper.getInventoryAndGoodsInfo(inventoryIds);
			 //得到去库存相关的移库商品信息
			 List<ShiftGoodsDO> shiftGoodsDOList = shiftGoodsDOMapper.getShiftGoodsDOList(shiftInventoryListId);
			 for (int i = 0; i < shiftGoodsListDTOList.size(); i++) {
				 List<ShiftGoodsDO> shiftGoodsDOList2 = new LinkedList<ShiftGoodsDO>();
				 for (int j = 0; j < shiftGoodsDOList.size(); j++) {
					 if (shiftGoodsListDTOList.get(i).getInventoryId().longValue() ==
							 shiftGoodsDOList.get(j).getInventoryId().longValue()) {
						 shiftGoodsDOList2.add(shiftGoodsDOList.get(j));
					 }
				 }
				 shiftGoodsListDTOList.get(i).setShiftGoodsDOList(shiftGoodsDOList2);
			 }
			 shiftInventoryListDTO.setShiftGoodsListDTOList(shiftGoodsListDTOList);
		}
		
		return shiftInventoryListDTO;
	}
	
	
	/**
	 * 通过移库单主键id取消移库单
	 * 1：通过shiftInventoryListId查询到相关ShiftInventoryListDO信息
	 * 2：通过ShiftInventoryListDO的nventoryShiftedId查询所有的库存信息
	 * 3：通过shiftInventoryListId查询到相关的ShiftGoodsDO信息
	 * 4：遍历库存信息和移库商品ShiftGoodsDO信息，如果移库商品里存的库存id与库存id一致，则将其ShiftPlanNum计算入库存锁定数量
	 * 5：修改库存锁定数量
	 */
	@Override
	public int deleteShiftInventoryList(Long shiftInventoryListId) {
		 ShiftInventoryListDO shiftInventoryListDO = shiftInventoryListDOMapper.selectByPrimaryKey(shiftInventoryListId);
		 Long [] inventoryIds = ConvertStringToLong(shiftInventoryListDO.getInventoryShiftedId());
		 List<ShiftGoodsDO> shiftGoodsDOList = shiftGoodsDOMapper.getShiftGoodsDOList(shiftInventoryListId);
		 
		 if (null != inventoryIds && inventoryIds.length > 0) {
			 for (int i = 0; i < inventoryIds.length; i++) {
				 BigDecimal shiftPlanNum = new BigDecimal(0);
				for (int j = 0; j < shiftGoodsDOList.size(); j++) {
					if (inventoryIds[i].longValue() == shiftGoodsDOList.get(j).getInventoryId().longValue()) {
						shiftPlanNum.add(shiftGoodsDOList.get(j).getShiftPlanNum());
					}
				}
				Float lockNum = ShiftInventoryListVO.ZERO_VALUE - shiftPlanNum.floatValue();
				inventoryMapper.updateInventoryLockNum(inventoryIds[i],null,lockNum);
			 }
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
