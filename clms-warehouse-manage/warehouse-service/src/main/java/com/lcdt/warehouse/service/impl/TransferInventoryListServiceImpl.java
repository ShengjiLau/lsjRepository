package com.lcdt.warehouse.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.contants.InventoryBusinessType;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.ShiftGoodsListDTO;
import com.lcdt.warehouse.dto.TransferInventoryListDTO;
import com.lcdt.warehouse.dto.TransferListDTO;
import com.lcdt.warehouse.entity.Inventory;
import com.lcdt.warehouse.entity.InventoryLog;
import com.lcdt.warehouse.entity.TransferGoodsDO;
import com.lcdt.warehouse.entity.TransferInventoryListDO;
import com.lcdt.warehouse.mapper.InventoryLogMapper;
import com.lcdt.warehouse.mapper.InventoryMapper;
import com.lcdt.warehouse.mapper.TransferGoodsDOMapper;
import com.lcdt.warehouse.service.InventoryService;
import com.lcdt.warehouse.service.TransferInventoryListService;
import com.lcdt.warehouse.utils.ShiftGoodsBO;
import com.lcdt.warehouse.vo.TransferInventoryListVO;
import com.lcdt.warehouse.mapper.TransferInventoryListDOMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月1日
 * @version
 * @Description: TODO 
 */
@Service
@Slf4j
public class TransferInventoryListServiceImpl implements TransferInventoryListService{
	
	@Autowired
	private TransferInventoryListDOMapper TransferInventoryListDOMapper;
	
	@Autowired
	private TransferGoodsDOMapper TransferGoodsDOMapper;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
    private InventoryMapper inventoryMapper;
	
	@Autowired
	private InventoryLogMapper logMapper;
	
	/**
	 * 新建库存商品转换单
	 */
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackForClassName = {"RuntimeException","Exception"}, timeout = 30, propagation = Propagation.REQUIRED)
	public int insertTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO) {
		TransferInventoryListDO transferInventoryListDO = new TransferInventoryListDO();
		BeanUtils.copyProperties(transferInventoryListDTO, transferInventoryListDO);
		transferInventoryListDO.setCompanyId(SecurityInfoGetter.getCompanyId());
		transferInventoryListDO.setCreateUser(SecurityInfoGetter.getUser().getRealName());
		transferInventoryListDO.setCreateUserId(SecurityInfoGetter.getUser().getUserId());
		transferInventoryListDO.setGmtCreate(new Date());
		transferInventoryListDO.setListStatus(TransferInventoryListVO.UNFINISHED);
		List<TransferGoodsDO> transferGoodsDOList = transferInventoryListDTO.getTransferGoodsDOList();
		for (int i = 0; i < transferGoodsDOList.size(); i++) {
			TransferGoodsDO transferGoodsDO = transferGoodsDOList.get(i);
			transferGoodsDO.setTransferId(transferInventoryListDO.getTransfersId());
			if (0 == transferGoodsDO.getIsMaterial()) {
				inventoryService.lockInventoryNum(transferGoodsDO.getInventoryId(), transferGoodsDO.getTransferNum().doubleValue());
			}
		}
		
		int result = TransferInventoryListDOMapper.insert(transferInventoryListDO);
		int transferGoodsResult = TransferGoodsDOMapper.insertTransferGoodsDOByBatch(transferGoodsDOList);
		log.debug("插入转换商品的数量为：" + transferGoodsResult);
		return result;
	}
	
	
	/**
	 * 完成库存商品转换单
	 */
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackForClassName = {"RuntimeException","Exception"}, timeout = 30, propagation = Propagation.REQUIRED)
	public int completeTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO) {
		TransferInventoryListDTO transferInventoryListDTOOld = TransferInventoryListDOMapper.getTransferInventoryListDTO(transferInventoryListDTO.getTransfersId());
		List<TransferGoodsDO> transferGoodsDOListOld = transferInventoryListDTOOld.getTransferGoodsDOList();
		for (int i = 0; i < transferGoodsDOListOld.size(); i++) {
			TransferGoodsDO transferGoodsDO = transferGoodsDOListOld.get(i);
			if (0 == transferGoodsDO.getIsMaterial()) {
				inventoryService.unLockInventoryNum(transferGoodsDO.getInventoryId(), transferGoodsDO.getTransferNum().doubleValue());
			}
		}
		TransferInventoryListDO transferInventoryListDO = new TransferInventoryListDO();
		transferInventoryListDO.setTransfersId(transferInventoryListDTO.getTransfersId());
		transferInventoryListDO.setListStatus(TransferInventoryListVO.FISHINED);
		transferInventoryListDO.setGmtComplete(new Date());
		int result = TransferInventoryListDOMapper.updateByPrimaryKeySelective(transferInventoryListDO);
		int transferGoodsResult = TransferGoodsDOMapper.updateTransferGoodsDOByBatchSelective(transferInventoryListDTO.getTransferGoodsDOList());
		
		TransferInventoryListDTO transferInventoryListDTONew = TransferInventoryListDOMapper.getTransferInventoryListDTO(transferInventoryListDTO.getTransfersId());
		List<TransferGoodsDO> transferGoodsDOListNew = transferInventoryListDTONew.getTransferGoodsDOList();
		for (int i = 0; i < transferGoodsDOListNew.size(); i++) {
			TransferGoodsDO transferGoodsDO = transferGoodsDOListNew.get(i);
			if (0 == transferGoodsDO.getIsMaterial()) {
				Inventory inventory = inventoryMapper.selectById(transferGoodsDO.getInventoryId());
				if (0 > (inventory.getInvertoryNum() - transferGoodsDO.getTransferNum().doubleValue())) {
					throw new RuntimeException(transferGoodsDO.getGoodsName() + "库存量不足");
				}
				inventory.setInvertoryNum(inventory.getInvertoryNum() - transferGoodsDO.getTransferNum().doubleValue());
				inventoryMapper.updateById(inventory);
				addInventoryLog(inventory, transferInventoryListDTONew, transferGoodsDO);
			}
			if (1 == transferGoodsDO.getIsMaterial()) {
				Inventory inventory = addInventory(transferGoodsDO,transferInventoryListDTONew);
				addInventoryLog(inventory, transferInventoryListDTONew, transferGoodsDO);
			}
		}
		log.debug("修改转换商品的数量为：" + transferGoodsResult);
		return result;
	}

	
	/**
	 * 依据条件查询转换单列表
	 */
	@Override
	@Transactional(readOnly = true)
	public PageBaseDto<TransferInventoryListDTO> getTransferInventoryListDTOList(TransferListDTO transferListDTO) {
		int pageNo; 
		int pageSize;
		pageNo = null == transferListDTO.getPageNo()? TransferInventoryListVO.FIRST_PAGE_NO : transferListDTO.getPageNo();
		pageSize = null == transferListDTO.getPageSize()? Integer.MAX_VALUE : transferListDTO.getPageSize();
		transferListDTO.setCompanyId(SecurityInfoGetter.getCompanyId());
		List<TransferInventoryListDTO> transferInventoryListDTOList = TransferInventoryListDOMapper.getTransferInventoryListDTOList(transferListDTO);
		if (null == transferInventoryListDTOList || 0 == transferInventoryListDTOList.size()) {
			List<TransferInventoryListDTO> transferInventoryListDTOListEmpty = new ArrayList<TransferInventoryListDTO>();
			return getPageBaseDto(transferInventoryListDTOListEmpty, pageSize, pageNo);
		}
		long total = transferInventoryListDTOList.size();
		Long[] tranferIds = new Long[(int) total];
		for (int i = -1; i++ < (total-1);) {
			tranferIds[i] = transferInventoryListDTOList.get(i).getTransfersId();
		}
		List<TransferGoodsDO> transferGoodsDOList = null;
		if (null == transferListDTO.getMaterialProduct() && null == transferListDTO.getFinishedProduct()) {
			 transferGoodsDOList = TransferGoodsDOMapper.getTransferGoodsDOListByTransferIds(tranferIds);
			 List<TransferInventoryListDTO> transferInventoryListDTOListWithGoods = 
					 getTransferInventoryListDTOListWithGoods(transferInventoryListDTOList,  transferGoodsDOList);
			 return getPageBaseDto(transferInventoryListDTOListWithGoods, pageSize, pageNo);
		}else if (null != transferListDTO.getMaterialProduct() && null == transferListDTO.getFinishedProduct()){
			Map<String,Object>  map = getMap(tranferIds, transferListDTO.getMaterialProduct(), TransferInventoryListVO.IS_MATERIAL);
			transferGoodsDOList = getTransferGoodsDOList(map);
			List<TransferInventoryListDTO> transferInventoryListDTOListWithGoods = 
					 getTransferInventoryListDTOListWithGoods(transferInventoryListDTOList,  transferGoodsDOList);
			Iterator<TransferInventoryListDTO> it = transferInventoryListDTOListWithGoods.iterator();
			while  (it.hasNext()) {
				TransferInventoryListDTO transferInventoryListDTO = it.next();
				if (null == transferInventoryListDTO.getTransferGoodsDOList() || 0 == transferInventoryListDTO.getTransferGoodsDOList().size()) {
					it.remove();
				}
			}
			 return getPageBaseDto(transferInventoryListDTOListWithGoods, pageSize, pageNo);
		}else if(null == transferListDTO.getMaterialProduct() && null != transferListDTO.getFinishedProduct()) {
			Map<String,Object>  map = getMap(tranferIds,transferListDTO.getFinishedProduct(), TransferInventoryListVO.IS_PRODUCT);
			transferGoodsDOList = getTransferGoodsDOList(map);
			List<TransferInventoryListDTO> transferInventoryListDTOListWithGoods = 
					 getTransferInventoryListDTOListWithGoods(transferInventoryListDTOList,  transferGoodsDOList);
			Iterator<TransferInventoryListDTO> it = transferInventoryListDTOListWithGoods.iterator();
			while (it.hasNext()) {
				TransferInventoryListDTO transferInventoryListDTO = it.next();
				if (null == transferInventoryListDTO.getTransferGoodsDOList() || 0 == transferInventoryListDTO.getTransferGoodsDOList().size()) {
					it.remove();
				}
			}
			 return getPageBaseDto(transferInventoryListDTOListWithGoods, pageSize, pageNo);
		}else {
			Map<String,Object>  map1 = getMap(tranferIds, transferListDTO.getMaterialProduct(), TransferInventoryListVO.IS_MATERIAL);
			List<TransferGoodsDO> transferGoodsDOList1 = getTransferGoodsDOList(map1);
			Map<String,Object>  map2 = getMap(tranferIds, transferListDTO.getMaterialProduct(), TransferInventoryListVO.IS_PRODUCT);
			List<TransferGoodsDO> transferGoodsDOList2 = getTransferGoodsDOList(map2);
			transferGoodsDOList = new ArrayList<TransferGoodsDO>(transferGoodsDOList1.size() + transferGoodsDOList2.size());
			transferGoodsDOList.addAll(transferGoodsDOList1);
			transferGoodsDOList.addAll(transferGoodsDOList2);
			List<TransferInventoryListDTO> transferInventoryListDTOListWithGoods = 
					 getTransferInventoryListDTOListWithGoods(transferInventoryListDTOList,  transferGoodsDOList);
			List<TransferInventoryListDTO> transferInventoryListDTOListRemove = new LinkedList<TransferInventoryListDTO>();
			for (int j = -1; j++ < (transferInventoryListDTOListWithGoods.size()-1);) {
				TransferInventoryListDTO transferInventoryListDTO = transferInventoryListDTOListWithGoods.get(j);
				List<TransferGoodsDO> transferGoodsDOList3 = transferInventoryListDTO.getTransferGoodsDOList();
				if (null == transferGoodsDOList3 || 0 == transferGoodsDOList3.size()) {
					transferInventoryListDTOListRemove.add(transferInventoryListDTO);
					continue;
				}
				int flag = 0;
				for (int i = -1; i++ < (transferGoodsDOList3.size()-1);) {
					TransferGoodsDO TransferGoodsDO = transferGoodsDOList3.get(i);
					if (TransferGoodsDO.getIsMaterial() == 0) {
						flag ++;
					}
				}
				if (flag == 0 || flag == transferGoodsDOList3.size()) {
					transferInventoryListDTOListRemove.add(transferInventoryListDTO);
				}
			}
			transferInventoryListDTOListWithGoods.removeAll(transferInventoryListDTOListRemove);
			return getPageBaseDto(transferInventoryListDTOListWithGoods, pageSize, pageNo);
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public TransferInventoryListDTO getTransferInventoryListDTODetail(Long transferInventoryListId) {
		TransferInventoryListDTO transferInventoryListDTO = TransferInventoryListDOMapper.getTransferInventoryListDTO(transferInventoryListId);
		return transferInventoryListDTO;
	}
	
	
	@Override
	public int updateTransferStatus(Long transferInventoryListId) {
		TransferInventoryListDO transferInventoryListDO = new TransferInventoryListDO();
		transferInventoryListDO.setTransfersId(transferInventoryListId);
		transferInventoryListDO.setListStatus(TransferInventoryListVO.CANCELED);
		int result = TransferInventoryListDOMapper.updateByPrimaryKeySelective(transferInventoryListDO);
		return result;
	}
	
	
	/**
	 * 将一些Mapper接口参数封装为Map
	 */
	private HashMap<String,Object> getMap(Long[] array, String str, byte byt){
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("transferIds", array);
		map.put("arg1", str);
		map.put("arg2", byt);
		return (HashMap<String, Object>) map;
	}
	
	/**
	 * 查询TransferGoodsDO列表
	 */
	private List<TransferGoodsDO> getTransferGoodsDOList(Map<String,Object> map){
		List<TransferGoodsDO> transferGoodsDOList = TransferGoodsDOMapper.getTransferGoodsDOListByConditions(map);
		return transferGoodsDOList;
	}
	
	/**
	 * 逻辑分页
	 */
	private PageBaseDto<TransferInventoryListDTO> getPageBaseDto(List<TransferInventoryListDTO> transferInventoryListDTOList, int pageSize, int pageNo){
		PageBaseDto<TransferInventoryListDTO> pageBaseDto = new PageBaseDto<TransferInventoryListDTO>();
		if (0 == transferInventoryListDTOList.size()) {
			pageBaseDto.setList(transferInventoryListDTOList);
			pageBaseDto.setTotal(0);
			return pageBaseDto;
		}
		List<TransferInventoryListDTO> listPage = new LinkedList<TransferInventoryListDTO>();
		if (pageNo*pageSize > transferInventoryListDTOList.size()) {
			listPage.addAll(transferInventoryListDTOList.subList((pageNo - 1)*pageSize, transferInventoryListDTOList.size()));
		}else {
			listPage.addAll(transferInventoryListDTOList.subList((pageNo - 1)*pageSize, pageNo*pageSize));
		}
		pageBaseDto.setList(listPage);
		pageBaseDto.setTotal(transferInventoryListDTOList.size());
		return pageBaseDto;
	}
	
	/**
	 * 将转换单主表与转换单商品表关联起来
	 */
	private List<TransferInventoryListDTO> getTransferInventoryListDTOListWithGoods(
			                              List<TransferInventoryListDTO> transferInventoryListDTOList,
			                                                         List<TransferGoodsDO> transferGoodsDOList){
		List<TransferInventoryListDTO> transferInventoryListDTOListWithGoods = 
				                              new ArrayList<>(transferInventoryListDTOList.size());
		transferInventoryListDTOListWithGoods.addAll(transferInventoryListDTOList);
		 for (int i = 0; i < transferInventoryListDTOListWithGoods.size(); i++) {
				TransferInventoryListDTO transferInventoryListDTO = transferInventoryListDTOListWithGoods.get(i);
				List<TransferGoodsDO> transferGoodsList = new LinkedList<TransferGoodsDO>();
				for (int j = -1; j++ < (transferGoodsDOList.size() - 1);) {
					TransferGoodsDO TransferGoodsDO = transferGoodsDOList.get(j);
					if (transferInventoryListDTO.getTransfersId().longValue() == TransferGoodsDO.getTransferId().longValue()) {
						transferGoodsList.add(TransferGoodsDO);
					}
				}
				transferInventoryListDTO.setTransferGoodsDOList(transferGoodsList);
			}
		return transferInventoryListDTOListWithGoods;
	}

	
	/**
	 * 加入库存
	 */
	private Inventory addInventory(TransferGoodsDO transferGoodsDO, TransferInventoryListDTO transferInventoryListDTO) {
		Inventory inventory = new Inventory();
		inventory.setBatch(transferGoodsDO.getGoodsBatch());
		inventory.setBusinessDesc("6");
		inventory.setCompanyId(transferInventoryListDTO.getCompanyId());
		inventory.setCustomerId(transferInventoryListDTO.getCustomerId());
		inventory.setCustomerName(transferInventoryListDTO.getCustomerName());
		inventory.setGoodsId(transferGoodsDO.getOriginGoodsId());
		inventory.setInvertoryNum(transferGoodsDO.getTransferNum().doubleValue());
		inventory.setStorageLocationId(transferGoodsDO.getWhLocId());
		inventory.setStorageLocationCode(transferGoodsDO.getWhLocCode());
		inventory.setWarehouseId(transferInventoryListDTO.getWarehouseId());
		inventory.setWarehouseName(transferInventoryListDTO.getWarehouseName());
		inventory.setLockNum((double) 0.0);
		return inventoryService.addInventory(inventory);
	}
	
	/**
	 * 生成库存流水
	 */
	private  void addInventoryLog(Inventory inventory, TransferInventoryListDTO transferInventoryListDTO, TransferGoodsDO transferGoodsDO) {
		InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setGoodsId(inventory.getGoodsId());
        inventoryLog.setCompanyId(inventory.getCompanyId());
        inventoryLog.setWarehouseId(inventory.getWarehouseId());
        inventoryLog.setChangeNum(transferGoodsDO.getTransferNum().doubleValue());
        
        inventoryLog.setStorageLocationCode(inventory.getStorageLocationCode());
        inventoryLog.setStorageLocationId(inventory.getStorageLocationId());
        inventoryLog.setOriginalGoodsId(inventory.getOriginalGoodsId());
        inventoryLog.setCustomerName(inventory.getCustomerName());
        inventoryLog.setCustomerId(inventory.getCustomerId());
        inventoryLog.setBusinessNo(transferInventoryListDTO.getListSerialNo());
        inventoryLog.setType(InventoryBusinessType.SHIFT_ORDER);
        if (null != inventory.getBatch()) {
        	inventoryLog.setBatch(inventory.getBatch());
        }		        
        inventoryLog.setCurrentInvetory(inventory.getInvertoryNum()); 
        inventoryLog.setLogTime(new Date());
        inventoryLog.setInventoryId(inventory.getInvertoryId());
        inventoryLog.setOrderId(transferInventoryListDTO.getTransfersId());
        logMapper.saveLog(inventoryLog);    		
	}
	
	
	

}
