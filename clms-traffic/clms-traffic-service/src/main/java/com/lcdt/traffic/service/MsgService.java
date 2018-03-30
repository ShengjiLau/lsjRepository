package com.lcdt.traffic.service;

import java.util.List;

import com.lcdt.traffic.model.Msg;
import com.lcdt.traffic.web.dto.MsgDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年3月30日下午1:37:14
 * @version
 */

public interface MsgService {
	
	/**
	 * 添加留言
	 */
	int addMsg(Msg msg);
	
	/**
	 * 修改留言
	 */
	int updateMsg(Msg msg);
	
	/**
	 * 取消留言
	 */
	int cancelMsg(Long msgId);
	
	/**
	 * 通过账单id获取留言
	 */
	Msg selectMsg(Long accountId,Short type);
	
	/**
	 * 条件查询获取留言
	 */
	List<Msg> selectSomeMsg(MsgDto msgDTO);
	
	

}
