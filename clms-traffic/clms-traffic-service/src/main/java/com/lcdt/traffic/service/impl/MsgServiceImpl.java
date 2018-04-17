package com.lcdt.traffic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcdt.traffic.dao.MsgMapper;
import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.service.MsgService;
import com.lcdt.traffic.web.dto.MsgDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月30日下午1:56:49
 * @version
 */
@Service
public class MsgServiceImpl implements MsgService {
	
	@Autowired
	private MsgMapper msgMapper;

	@Override
	public int addMsg(Msg msg) {
		
		return msgMapper.insert(msg);
	}

	@Override
	public int updateMsg(Msg msg) {
		
		return msgMapper.updateByPrimaryKey(msg);
	}

	@Override
	public int cancelMsg(Long msgId) {
		
		return msgMapper.updateIsDelete(msgId);
	}

	@Override
	public Msg selectMsg(Long accountId,Short type) {
		
		return msgMapper.selectByAccountId(accountId,type);
	}

	@Override
	public  List<Msg> selectSomeMsg(MsgDto msgDto) {
	
		return msgMapper.selectSomeMsg(msgDto);
	}

}
