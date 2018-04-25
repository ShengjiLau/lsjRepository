package com.lsj;

public class Test019 {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append(1);
		sb.append(",");
		sb.append(2);
		sb.append(",");
		String s=null;
		if(sb.length()>0) {
			s=sb.substring(0,sb.length()-1);
		}
System.out.println(s);
	}

}
