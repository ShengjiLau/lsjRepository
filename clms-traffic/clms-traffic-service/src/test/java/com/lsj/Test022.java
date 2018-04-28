package com.lsj;

public class Test022 {

	public static void main(String[] args) {
		StringBuilder sbd=new StringBuilder();
		for(int i=0;i<5;i++) {
			sbd.append(i);sbd.append(",");
		}
		String [] ss= sbd.toString().split(",");
		System.out.println(ss.length);
		System.out.println(sbd.toString());
		Long [] lids=new Long[ss.length];
		for(int i=0;i<ss.length;i++) {
			lids[i]=Long.valueOf(ss[i]);
		}	
		System.out.println(lids.toString());
		for(int i=0;i<lids.length;i++) {
			System.out.println(lids[i]);
		}
		
		
		
	}

}
