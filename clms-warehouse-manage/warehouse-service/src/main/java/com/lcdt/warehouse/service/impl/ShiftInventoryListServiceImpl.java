package com.lcdt.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.contants.InventoryBusinessType;
import com.lcdt.warehouse.dto.ShiftGoodsListDTO;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.InventoryLog;
import com.lcdt.warehouse.entity.ShiftGoodsDO;
import com.lcdt.warehouse.entity.ShiftInventoryListDO;
import com.lcdt.warehouse.mapper.InventoryLogMapper;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.mapper.ShiftGoodsDOMapper;
import com.lcdt.warehouse.mapper.ShiftInventoryListDOMapper;
import com.lcdt.warehouse.service.ShiftInventoryListService;
import com.lcdt.warehouse.utils.ShiftGoodsBO;
import com.lcdt.warehouse.vo.ShiftInventoryListVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version 1.0
 * @Description: 移库单的增删改查，涉及到库存、移库商品信息以及生成入库单。
 */
@Service 
@Primary
@Slf4j
public class ShiftInventoryListServiceImpl implements ShiftInventoryListService {
	
	//private Logger logger = LoggerFactory.getLogger(ShiftInventoryListServiceImpl.class);
	
	@Autowired
	private ShiftInventoryListDOMapper shiftInventoryListDOMapper;
	
	@Autowired
	private ShiftGoodsDOMapper shiftGoodsDOMapper;
	
	@Autowired
	private InventoryMapper inventoryMapper;
	
	@Autowired
	private InventoryLogMapper logMapper;
	
	
	/**
	 * 新增移库单所传参数为ShiftInventoryListDTO，需要三步：
	 * 1：创建新的移库单DO，复制从ShiftInventoryListDTO里的移库单属性，插入移库单。
	 * 2：每一种库存计算移库数量，修改库存。
	 * 3：插入移库到新库的记录。
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
		shiftInventoryListDO.setFinished(ShiftInventoryListVO.UNFINISHED);
		String s = null;
		if (null != shiftInventoryListDTO.getShiftGoodsListDTOList() && 0 != shiftInventoryListDTO.getShiftGoodsListDTOList().size()) {
			StringBuilder sbd = new StringBuilder();	
			//将库存id组成字符串存入移库单属性
			for (int i = 0; i < shiftInventoryListDTO.getShiftGoodsListDTOList().size(); i++) {
				sbd.append(shiftInventoryListDTO.getShiftGoodsListDTOList().get(i).getInvertoryId());sbd.append(",");
			}	
			//去掉最后一个“，”
			s = sbd.substring(0, sbd.length()-1);
		}
		shiftInventoryListDO.setInventoryShiftedId(s);
		int result = shiftInventoryListDOMapper.insert(shiftInventoryListDO);
		
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
				//将库存id存入移库商品信息中
				shiftGoodsDO.setInventoryId(shiftGoodsListDTOList.get(a).getInvertoryId());
				//计算计划商品移库数量
				shiftPlanNum = shiftPlanNum.add(shiftGoodsDO.getShiftPlanNum());
				shiftGoodsDO.setShiftInventoryId(shiftInventoryListDO.getShiftId());
			}
			shiftGoodsDOList.addAll(shiftGoodsDOList1);
			Float lockNum = shiftPlanNum.floatValue();
			//修改库存中库存总量以及库存锁定量
			Inventory inventory = inventoryMapper.selectById(shiftGoodsListDTOList.get(a).getInvertoryId());
			//如果需要锁定的库存大于现有库存减去已经锁定的库存量，则提示库存不足
			  if ((inventory.getInvertoryNum()-inventory.getLockNum()) < lockNum) {
				  return ShiftInventoryListVO.UNDERSTOCK;
			  }
		    h = inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInvertoryId(),null,lockNum);
		}
		//数据库插入新的移库商品信息列表
		int j = shiftGoodsDOMapper.insertShiftGoodsByBatch(shiftGoodsDOList);
		
		log.debug("插入的移库商品信息条数为"+j);
		log.debug("插入的移库单数量为"+result);
		log.debug("修改的库存数量为"+h);
		
		if (result > 0) {
			return ShiftInventoryListVO.SUCCESS_NUM;
		}else {
			return ShiftInventoryListVO.FAILED_NUM;
		}
	}

	
	/**
	 * 完成移库单步骤有四：
	 * 1：修改移库单ShiftInventoryList的finished状态为1。
	 * 2：修改库存，解除新建时锁定的库存，修改完成时的库存量。
	 * 3：修改移库时的移库商品信息。
	 * 4：查询移入库位是否存在对应的商品，如果存在修改相应库存，如果不存在则新建库存。
	 */
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,timeout=60,propagation=Propagation.REQUIRED,rollbackForClassName={"RuntimeException","Exception"})
	public int completeShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		//将移库单的状态修改为1，即完成状态
		ShiftInventoryListDO shiftInventoryListDO = new ShiftInventoryListDO();
		shiftInventoryListDO.setShiftId(shiftInventoryListDTO.getShiftId());
		shiftInventoryListDO.setShiftUser(shiftInventoryListDTO.getShiftUser());
		shiftInventoryListDO.setShiftTime(shiftInventoryListDTO.getShiftTime());
		shiftInventoryListDO.setFinished(ShiftInventoryListVO.FISHINED);
		int i = shiftInventoryListDOMapper.updateByPrimaryKeySelective(shiftInventoryListDO);
		
		ShiftInventoryListDO shiftInventoryListDO2 = shiftInventoryListDOMapper.selectByPrimaryKey(shiftInventoryListDTO.getShiftId());
		
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		List<ShiftGoodsDO> shiftGoodsDOList = new LinkedList<ShiftGoodsDO>();
		
		//遍历所有的ShiftGoodsListDTO
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			ShiftGoodsListDTO sgdl = shiftGoodsListDTOList.get(a);
			List<ShiftGoodsDO> shiftGoodsDOList1 = sgdl.getShiftGoodsDOList();
			BigDecimal shiftPlanNum = new BigDecimal(0);
			BigDecimal shiftNum = new BigDecimal(0);
			 Inventory inventory1 = inventoryMapper.selectById(sgdl.getInvertoryId());
			//将所有的ShiftGoodsDO添加到shiftGoodsDOList
			shiftGoodsDOList.addAll(shiftGoodsDOList1);
			
			for(int b = 0; b < shiftGoodsDOList1.size(); b++) {
				ShiftGoodsDO shiftGoodsDO = shiftGoodsDOList1.get(b);				
				ShiftGoodsDO shiftGoodsDO2 = shiftGoodsDOMapper.selectByPrimaryKey(shiftGoodsDO.getShiftGoodsId());
				//计算计划和实际移库商品数量
				shiftPlanNum.add(shiftGoodsDO.getShiftPlanNum());
				shiftNum.add(shiftGoodsDO.getShiftNum());
				
				ShiftGoodsBO shiftGoodsBO = new ShiftGoodsBO();
				shiftGoodsBO.setCompanyId(shiftInventoryListDO2.getCompanyId());
				shiftGoodsBO.setCustomerId(shiftInventoryListDO2.getCustomerId());
				shiftGoodsBO.setWarehouseId(shiftInventoryListDO2.getWarehouseId());
				shiftGoodsBO.setGoodsBatch(sgdl.getGoodsBatch());
				shiftGoodsBO.setGoodsCode(sgdl.getGoodsCode());
				shiftGoodsBO.setStorageLocationCode(shiftGoodsDO.getShiftLocation());
				
				ShiftGoodsListDTO shiftGoodsListDTO1 = inventoryMapper.selectInventoryListByShiftGoodsBO(shiftGoodsBO);
				if (null != shiftGoodsListDTO1) {
					inventoryMapper.updateInventoryLockNum(shiftGoodsListDTO1.getInvertoryId(), ShiftInventoryListVO.ZERO_VALUE-shiftGoodsDO.getShiftNum().floatValue(), ShiftInventoryListVO.ZERO_VALUE.floatValue());
				}else {
					//没有
					Inventory inventory2 = new Inventory();
					inventory2.setGoodsId(inventory1.getGoodsId());
					inventory2.setWareHouseId(shiftInventoryListDO2.getWarehouseId());
			        inventory2.setStorageLocationCode(shiftGoodsDO.getShiftLocation());
			        inventory2.setStorageLocationId(shiftGoodsDO2.getStorageLocationId());
			        inventory2.setCustomerName(shiftInventoryListDO2.getCustomerName());
			        inventory2.setWarehouseName(shiftInventoryListDO2.getWarehouseName());
			        inventory2.setBatch(shiftGoodsDO.getBatch());
			        inventory2.setCustomerId(shiftInventoryListDO2.getCustomerId());
			        inventory2.setInventoryPrice(inventory1.getInventoryPrice());
			        inventory2.setOriginalGoodsId(inventory1.getOriginalGoodsId());
			        inventory2.setBaseUnit(inventory1.getBaseUnit());
			        inventory2.setRemark(shiftGoodsDO.getRemark());
			        inventory2.setCompanyId(shiftInventoryListDO2.getCompanyId());
			        inventoryMapper.insert(inventory2);	
			        
			        InventoryLog inventoryLog = new InventoryLog();
			        inventoryLog.setGoodsId(shiftGoodsDO.getGoodsId());
			        inventoryLog.setCompanyId(shiftInventoryListDO2.getCompanyId());
			        inventoryLog.setWarehouseId(shiftInventoryListDO2.getWarehouseId());
			        inventoryLog.setChangeNum(shiftGoodsDO.getShiftNum().floatValue());
			        inventoryLog.setStorageLocationCode(shiftGoodsDO.getShiftLocation());
			        inventoryLog.setStorageLocationId(shiftGoodsDO.getStorageLocationId());
			        inventoryLog.setOriginalGoodsId(shiftGoodsDO.getOriginalGoodsId());
			        inventoryLog.setCustomerName(shiftInventoryListDO2.getWarehouseName());
			        inventoryLog.setCustomerId(shiftInventoryListDO2.getCustomerId());
			        inventoryLog.setBusinessNo(shiftInventoryListDO2.getShiftInventoryNum());
			        //inventoryLog.setType();
			        inventoryLog.setBatch(shiftGoodsDO.getBatch());
			        //inventoryLog.setLogNo();
			        inventoryLog.setComment(shiftGoodsDO.getRemark());
			        inventoryLog.setCurrentInvetory(inventory1.getInvertoryNum());  
			        logMapper.saveLog(inventoryLog);     
				}
	
			}
			Float lockNum = shiftPlanNum.floatValue();
			Float inventoryNum = shiftNum.floatValue();
			//修改移除库存的库存总量和锁定库存量
			
		 
		  //如果现有库存小于移动库存，则提示库存不足
		  if (inventory1.getInvertoryNum() < inventoryNum) {
			  return ShiftInventoryListVO.UNDERSTOCK;
		  }
		   inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInvertoryId(),inventoryNum,lockNum);
		}
		//修改移库商品信息
		shiftGoodsDOMapper.updateShiftGoodsByBatch(shiftGoodsDOList);
		
	
		
		
		if (i > 0) {
			return ShiftInventoryListVO.SUCCESS_NUM;
		}else {
			return ShiftInventoryListVO.FAILED_NUM;
		}	
	}
	
	
	/**
	 * 依据条件查询移库单列表
	 * 1：通过查询条件找到对应的ShiftInventoryListDO集合。
	 * 2：遍历ShiftInventoryListDO集合，找到对应的库存商品信息和移库商品信息。
	 * 3：如果库存商品信息和查询条件不一致，则去掉相应的ShiftInventoryListDTO。
	 * 4:本次查询较为复杂，无法采用关联查询，采用了逻辑分页。
	 */
	@Override
	@Transactional(readOnly=true)
	public PageInfo<ShiftInventoryListDTO> getShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO1) {
		shiftInventoryListDTO1.setCompanyId(SecurityInfoGetter.getCompanyId());
		if (null == shiftInventoryListDTO1.getPageNo()) {
			shiftInventoryListDTO1.setPageNo(ShiftInventoryListVO.FIRST_PAGE_NO);
		}
		if (null == shiftInventoryListDTO1.getPageSize()) {
			shiftInventoryListDTO1.setPageSize(Integer.MAX_VALUE);
		}
		int pageNo = shiftInventoryListDTO1.getPageNo();
		int pageSize = shiftInventoryListDTO1.getPageSize();
		
		if (null != shiftInventoryListDTO1.getBeginTime() && !"".equals(shiftInventoryListDTO1.getBeginTime())) {
			shiftInventoryListDTO1.setBeginTime(convertTimeFormat(shiftInventoryListDTO1.getBeginTime()));	
		}
		if (null != shiftInventoryListDTO1.getEndTime() && !"".equals(shiftInventoryListDTO1.getEndTime())) {
			shiftInventoryListDTO1.setEndTime(convertTimeFormat(shiftInventoryListDTO1.getEndTime()));
		}
		
		//分页
		//PageHelper.startPage(shiftInventoryListDTO1.getPageNo(), shiftInventoryListDTO1.getPageSize());
		List<ShiftInventoryListDO> shiftInventoryListDOList = shiftInventoryListDOMapper.getShiftInventoryListDOByCondition(shiftInventoryListDTO1);
		//PageInfo<ShiftInventoryListDO> page1 = new PageInfo<ShiftInventoryListDO>(shiftInventoryListDOList);
		
		log.debug("查询得到的移库单数量为"+shiftInventoryListDOList.size());
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
					if (null != shiftInventoryListDTO1.getGoodsInfo() && !"".equals(shiftInventoryListDTO1.getGoodsInfo()) && !shiftInventoryListDTO1.getGoodsInfo().equals(ShiftGoodsListDTOList.get(i).getGoodsName())) {
							 ShiftGoodsListDTOList2.add(ShiftGoodsListDTOList.get(i));
							 continue;
					 } 
					 //遍历所有的移库商品信息shiftGoodsDO，如果shiftGoodsDO里存的InventoryId与ShiftGoodsListDTO里存的InventoryId相同，则将shiftGoodsDO添加到ShiftGoodsListDTO里的ShiftGoodsDOList
					 if (null != shiftGoodsDOList1 && 0 != shiftGoodsDOList1.size()) {
						 if (null != shiftGoodsDOList1 && 0 != shiftGoodsDOList1.size()) {
							 for (int j = 0; j < shiftGoodsDOList1.size(); j++) {
								 if (shiftGoodsDOList1.get(j).getInventoryId().longValue() == ShiftGoodsListDTOList.get(i).getInvertoryId().longValue()) {
									 shiftGoodsDOList2.add(shiftGoodsDOList1.get(j));
								 }
							 }  
						 }
					 }
					 ShiftGoodsListDTOList.get(i).setShiftGoodsDOList(shiftGoodsDOList2);
				 }
				 
				 ShiftGoodsListDTOList.removeAll(ShiftGoodsListDTOList2);
				 if (null != ShiftGoodsListDTOList && 0 != ShiftGoodsListDTOList.size()) {
					 shiftInventoryListDTO2.setShiftGoodsListDTOList(ShiftGoodsListDTOList); 
				 } 
			}
			if (null != shiftInventoryListDTO2.getShiftGoodsListDTOList() && 0 != shiftInventoryListDTO2.getShiftGoodsListDTOList().size()) {
				shiftInventoryListDTOList.add(shiftInventoryListDTO2);
			} 
		}
		
		//实现逻辑分页
		List<ShiftInventoryListDTO> shiftInventoryListDTOList2 = new ArrayList<ShiftInventoryListDTO>();
		if (pageNo*pageSize > shiftInventoryListDTOList.size()) {
			shiftInventoryListDTOList2.addAll(shiftInventoryListDTOList.subList((pageNo - 1)*pageSize, shiftInventoryListDTOList.size()));
		}else {
			shiftInventoryListDTOList2.addAll(shiftInventoryListDTOList.subList((pageNo - 1)*pageSize, pageNo*pageSize));
		}
		
		PageInfo<ShiftInventoryListDTO> page = new PageInfo<ShiftInventoryListDTO>(shiftInventoryListDTOList2);
		page.setTotal(shiftInventoryListDTOList.size());
		return page;
    }
	
	
	
	/**
	 * 通过移库单主键id查询移库单信息。
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
					 if (shiftGoodsListDTOList.get(i).getInvertoryId().longValue() ==
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
	 * 1：通过shiftInventoryListId查询到相关ShiftInventoryListDO信息。
	 * 2：通过ShiftInventoryListDO的nventoryShiftedId查询所有的库存信息。
	 * 3：通过shiftInventoryListId查询到相关的ShiftGoodsDO信息。
	 * 4：遍历库存信息和移库商品ShiftGoodsDO信息，如果移库商品里存的库存id与库存id一致，则将其ShiftPlanNum计算入库存锁定数量。
	 * 5：修改库存锁定数量。
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
		 
		int result = shiftInventoryListDOMapper.updateFinishedById(shiftInventoryListId,ShiftInventoryListVO.CANCELED);
		
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
	
	/**
	 * 将固定格式的时间格式转换为相应的字符串
	 * @param s
	 * @return
	 */
	public String convertTimeFormat(String s) {
		String pattern = "^[0-9]{4}[-][0-9]{2}[-][0-9]{2}";
		if (Pattern.matches(pattern, s)) {
			String s2 = s.replace("-", "");
			return s2;	
		}else {
			return null;
		}
	}
	
	
	
	
	
	

}
