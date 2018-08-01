package com.lcdt.warehouse.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.dto.PageBaseDto;
import com.lcdt.warehouse.dto.TransferInventoryListDTO;
import com.lcdt.warehouse.entity.TransferGoodsDO;
import com.lcdt.warehouse.entity.TransferInventoryListDO;
import com.lcdt.warehouse.mapper.TransferGoodsDOMapper;
import com.lcdt.warehouse.service.TransferInventoryListService;
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
		int result = TransferInventoryListDOMapper.insert(transferInventoryListDO);
		List<TransferGoodsDO> transferGoodsDOList = transferInventoryListDTO.getTransferGoodsDOList();
		for (TransferGoodsDO transferGoodsDO : transferGoodsDOList) {
			transferGoodsDO.setTransferId(transferInventoryListDO.getTransfersId());
		}
		int transferGoodsResult = TransferGoodsDOMapper.insertTransferGoodsDOByBatch(transferGoodsDOList);
		log.debug("插入转换商品的数量为：" + transferGoodsResult);
		return result;
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackForClassName = {"RuntimeException","Exception"}, timeout = 30, propagation = Propagation.REQUIRED)
	public int completeTransferInventoryList(TransferInventoryListDTO transferInventoryListDTO) {
		TransferInventoryListDO transferInventoryListDO = new TransferInventoryListDO();
		transferInventoryListDO.setTransfersId(transferInventoryListDTO.getTransfersId());
		transferInventoryListDO.setListStatus(TransferInventoryListVO.FISHINED);
		int result = TransferInventoryListDOMapper.updateByPrimaryKeySelective(transferInventoryListDO);
		int transferGoodsResult = TransferGoodsDOMapper.updateTransferGoodsDOByBatch(transferInventoryListDTO.getTransferGoodsDOList());
		log.debug("修改转换商品的数量为：" + transferGoodsResult);
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public PageBaseDto<TransferInventoryListDTO> getTransferInventoryListDTOList(TransferInventoryListDTO transferInventoryListDTO) {
		int pageNo; 
		int pageSize;
		pageNo = null == transferInventoryListDTO.getPageNo()? 1 : transferInventoryListDTO.getPageNo();
		pageSize = null == transferInventoryListDTO.getPageSize()? 0 : transferInventoryListDTO.getPageSize();
		PageHelper.startPage(pageNo, pageSize);
		List<TransferInventoryListDTO> TransferInventoryListDTOList = TransferInventoryListDOMapper.getTransferInventoryListDTOList(transferInventoryListDTO);
		
		
		
		PageBaseDto pageBaseDto = new PageBaseDto();
		
		return pageBaseDto;
	}

	@Override
	@Transactional(readOnly = true)
	public TransferInventoryListDTO getTransferInventoryListDTODetail(Long transferInventoryListId) {
		TransferInventoryListDO transferInventoryListDO = TransferInventoryListDOMapper.selectByPrimaryKey(transferInventoryListId);
		TransferInventoryListDTO transferInventoryListDTO = new TransferInventoryListDTO();
		BeanUtils.copyProperties(transferInventoryListDO, transferInventoryListDTO);
		
		
		
		
		
		
		
		return transferInventoryListDTO;
	}

}
