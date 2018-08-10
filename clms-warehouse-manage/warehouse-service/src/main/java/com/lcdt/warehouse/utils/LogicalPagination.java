package com.lcdt.warehouse.utils;

import java.util.LinkedList;
import java.util.List;

import com.lcdt.warehouse.dto.PageBaseDto;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月10日
 * @version 1.0
 * @Description: 逻辑分页 
 */

public class  LogicalPagination <T>{
	
	public static <T> PageBaseDto<T> paging(List<T> totalList, Integer pageNo, Integer pageSize) {
		PageBaseDto<T> pageBaseDto = new PageBaseDto<T>();
		//相信Integer.MAX_VALUE已经足够大
		int listSize = totalList.size();
		/**
		 * 如果List为空
		 */
		if (null == totalList || totalList.isEmpty() ) {
			pageBaseDto.setList(totalList);
			pageBaseDto.setTotal(0);
			return pageBaseDto;
		}
		if (null == pageNo) {
			pageNo = 1;
		}
		if (null == pageSize) {
			pageSize = 0;
		}
		/**
		 * 如果pageSize为0 ， 则返回全部，类似于PageHelper.
		 */
		if (0 == pageSize) {
			pageBaseDto.setTotal(listSize);
			pageBaseDto.setList(totalList);
			return pageBaseDto;
		}
		List<T> pageList = new LinkedList<T>();
		if (pageNo * pageSize > listSize) {
			pageList.addAll(totalList.subList((pageNo - 1) * pageSize, listSize));
		}else {
			pageList.addAll(totalList.subList((pageNo - 1) * pageSize, pageNo * pageSize));
		}
		pageBaseDto.setTotal(listSize);
		pageBaseDto.setList(pageList);
		return pageBaseDto;
	}
	
	
	
	

}
