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
	
	private final static Integer TATAL_ZERO = 0;
	
	private final static Integer PAGE_ONE = 1;
	
	private final static Integer PAGE_SIZE_ZERO = 0;
	
	public static <T> PageBaseDto<T> paging(List<T> totalList, Integer pageSize, Integer pageNo) {
		PageBaseDto<T> pageBaseDto = new PageBaseDto<T>();
		//相信Integer.MAX_VALUE已经足够大
		int totalSize = totalList.size();
		if (null == totalList || totalList.isEmpty() ) {
			pageBaseDto.setList(totalList);
			pageBaseDto.setTotal(TATAL_ZERO);
			return pageBaseDto;
		}
		if (null == pageNo) {
			pageNo = PAGE_ONE;
		}
		if (null == pageSize) {
			pageSize = PAGE_SIZE_ZERO;
		}
		/**
		 * 如果pageSize为0 ， 则返回全部，类似于PageHelper.
		 */
		if (0 == pageSize) {
			pageBaseDto.setTotal(totalSize);
			pageBaseDto.setList(totalList);
			return pageBaseDto;
		}
		List<T> pageList = new LinkedList<T>();
		if (pageNo * pageSize > totalSize) {
			pageList.addAll(totalList.subList((pageNo - PAGE_ONE) * pageSize, totalSize));
		}else {
			pageList.addAll(totalList.subList((pageNo - PAGE_ONE) * pageSize, pageNo * pageSize));
		}
		pageBaseDto.setTotal(totalSize);
		pageBaseDto.setList(pageList);
		return pageBaseDto;
	}
	
	
	
	

}
