package com.lcdt.traffic.service.impl;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.traffic.dao.MsgMapper;
import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.service.MsgService;
import com.lcdt.traffic.vo.ConstantVO;
import com.lcdt.traffic.web.dto.MsgDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月30日
 * @version 1.0
 */
@Service
public class MsgServiceImpl implements MsgService {

	@Autowired
	private MsgMapper msgMapper;

	@Override
	public int addMsg(Msg msg) {
		msg.setCreateDate(new Date());
		msg.setIsDeleted(ConstantVO.NORMAL_STATUS);
		msg.setOperatorId(SecurityInfoGetter.getUser().getUserId());
		msg.setOperatorName(SecurityInfoGetter.getUser().getRealName());

		return msgMapper.insert(msg);
	}

	@Override
	public int updateMsg(Msg msg) {
		msg.setCreateDate(new Date());
		msg.setOperatorId(SecurityInfoGetter.getUser().getUserId());
		msg.setOperatorName(SecurityInfoGetter.getUser().getRealName());
		return msgMapper.updateByPrimaryKeySelective(msg);
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
