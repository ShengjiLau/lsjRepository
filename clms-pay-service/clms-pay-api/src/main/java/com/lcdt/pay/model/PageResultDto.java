package com.lcdt.pay.model;

import com.github.pagehelper.Page;
import com.lcdt.converter.ResponseData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ss on 2017/11/15.
 */
public class PageResultDto<T> implements ResponseData,Serializable{


	public PageResultDto(){

	}

	public PageResultDto(List<T> result) {

		if (result == null) {
			return;
		}

		if (result instanceof Page) {
			this.total = ((Page) result).getTotal();
			this.list = ((Page) result).getResult();
		}else{
			this.list = result;
			this.total = Long.valueOf(result.size());
		}
	}

	List<T> list;

	Long total;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
}
