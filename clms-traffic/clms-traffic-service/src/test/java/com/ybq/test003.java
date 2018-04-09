package com.ybq;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.lcdt.traffic.util.PlanBO;


public class test003 {

	public static void main(String[] args) {
		Map<String,String> m = new HashMap<String,String>(32,(float) 0.75);
		Collections.synchronizedMap(m);
		
		Map<Integer,String> tm=new TreeMap<Integer,String>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1-o2;
			}	
		});
	tm.put(9,"q");
	tm.put(2,"w");
	tm.put(5,"y");
	
	System.out.println(tm.toString());
	

	
}



	
}
	
	
	
	

