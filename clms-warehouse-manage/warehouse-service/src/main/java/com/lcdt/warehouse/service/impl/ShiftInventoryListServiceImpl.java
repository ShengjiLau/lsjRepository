package com.lcdt.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcdt.warehouse.dto.ShiftGoodsListDTO;
import com.lcdt.warehouse.dto.ShiftInventoryListDTO;
import com.lcdt.warehouse.entity.ShiftGoodsDO;
import com.lcdt.warehouse.entity.ShiftInventoryListDO;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.mapper.ShiftGoodsDOMapper;
import com.lcdt.warehouse.mapper.ShiftInventoryListDOMapper;
import com.lcdt.warehouse.service.ShiftInventoryListService;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月10日
 * @version
 * @Description: TODO 
 */
@Service
public class ShiftInventoryListServiceImpl implements ShiftInventoryListService {
	
	@Autowired
	private ShiftInventoryListDOMapper shiftInventoryListDOMapper;
	
	@Autowired
	private ShiftGoodsDOMapper shiftGoodsDOMapper;
	
	@Autowired
	private InventoryMapper inventoryMapper;

	
	/**
	 * 新增移库单所传参数为ShiftInventoryListDTO，需要三步：
	 * 1：创建新的移库单DO，复制从ShiftInventoryListDTO里的移库单属性，插入移库单，
	 * 2：每一种库存计算移库数量，修改库存
	 * 3：插入移库到新库的记录
	 */
	@Override
	public int insertShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
	
		ShiftInventoryListDO shiftInventoryListDO = new ShiftInventoryListDO(); 
		
		BeanUtils.copyProperties(shiftInventoryListDTO, shiftInventoryListDO);
		int i = shiftInventoryListDOMapper.insert(shiftInventoryListDO);
		
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		
		//新建移库到新库的记录
		List<ShiftGoodsDO> shiftGoodsDOList = new ArrayList<ShiftGoodsDO>();
		int h = 0;
			
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			BigDecimal shiftPlanNum = new BigDecimal(0);
			shiftGoodsDOList.addAll(shiftGoodsListDTOList.get(a).getShiftGoodsDOList());
			for(int b = 0; b < shiftGoodsListDTOList.get(a).getShiftGoodsDOList().size(); b++) {
				shiftPlanNum.add(shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).getShiftPlanNum());
			}
			Float lockNum = shiftPlanNum.floatValue();
		    h = inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInventoryId(),null,lockNum);
		}
		
		int j = shiftGoodsDOMapper.insertShiftGoodsByBatch(shiftGoodsDOList);
		
		if (i > 0 && j == shiftGoodsDOList.size() && shiftGoodsListDTOList.size() == h) {
			return 1;
		}else {
			return -1;
		}
	}


	@Override
	public int completeShiftInventoryList(ShiftInventoryListDTO shiftInventoryListDTO) {
		int i = shiftInventoryListDOMapper.updateFinishedById(shiftInventoryListDTO.getShiftId());
		
		List<ShiftGoodsListDTO> shiftGoodsListDTOList = shiftInventoryListDTO.getShiftGoodsListDTOList();
		List<ShiftGoodsDO> shiftGoodsDOList = new ArrayList<ShiftGoodsDO>();
		
		for (int a = 0; a < shiftGoodsListDTOList.size(); a++) {
			BigDecimal shiftPlanNum = new BigDecimal(0);
			BigDecimal shiftNum = new BigDecimal(0);
			shiftGoodsDOList.addAll(shiftGoodsListDTOList.get(a).getShiftGoodsDOList());
			for(int b = 0; b < shiftGoodsListDTOList.get(a).getShiftGoodsDOList().size(); b++) {
				shiftPlanNum.add(shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).getShiftPlanNum());
				shiftNum.add(shiftGoodsListDTOList.get(a).getShiftGoodsDOList().get(b).getShiftNum());
			}
			
			Float lockNum = shiftPlanNum.floatValue();
			Float inventoryNum = shiftNum.floatValue();
		   inventoryMapper.updateInventoryLockNum(shiftGoodsListDTOList.get(a).getInventoryId(),inventoryNum,lockNum);
		}
		
		
		
		
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
