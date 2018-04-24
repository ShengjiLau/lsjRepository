package com.lsj;

import java.util.ArrayList;
import java.util.List;

public class Test010 {

	public static void main(String[] args) {
		List<Long> list = new ArrayList<Long>();
		list.add((long)1);
		list.add((long)2);
		list.add((long)3);
		
		
		Long[] l= new Long[list.size()];
		for(int i=0;i<list.size();i++) {
			l[i]=(Long) list.get(i);
			System.out.println(l[i]);
		}
		
		

	}

}
