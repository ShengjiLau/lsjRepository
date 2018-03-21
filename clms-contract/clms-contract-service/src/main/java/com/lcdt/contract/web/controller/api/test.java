package com.lcdt.contract.web.controller.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public class test {

	public static void main(String[] args) {
		PageInfo pageInfo =new PageInfo();
		Page page= new Page();
		if(Page.class.isInstance(PageInfo.class)) {
			
			System.out.println("yes");
		}
		
		
		
		

	}

}
