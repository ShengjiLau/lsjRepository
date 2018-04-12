package com.lsj;

import org.apache.commons.lang.ArrayUtils;

public class Test007 {

	public static void main(String[] args) {
		Long[] q=null;
		Long[] w= {(long) 1,(long) 2,(long) 3,(long) 4,(long) 5,(long) 6};
		Long[] e= {(long) 23,(long) 56,(long) 15,(long) 89};
		q=(Long[]) ArrayUtils.addAll(q,w);
	//	System.out.println(q.toString());
		for(int i=0;i<q.length;i++) {
			System.out.println(q[i]);
		}
		
		System.out.println("divide");
		
		
		
		
		q=(Long[]) ArrayUtils.addAll(q,e);
		//System.out.println(q.toString());
		
		for(int i=0;i<q.length;i++) {
			System.out.println(q[i]);
		}

	}

}
