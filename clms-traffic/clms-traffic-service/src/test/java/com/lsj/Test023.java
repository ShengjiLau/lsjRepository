package com.lsj;

import java.util.regex.Pattern;

public class Test023 {

	public static void main(String[] args) {
		String patten="^(([0-9]+)([,])){0,}$";
		
		String str ="111,22,33,";
		
		boolean b= Pattern.matches(patten, str);
		System.out.println(b);
	
	
		String pattern ="^([0-9]{4})-([0-9]{2})-([0-9]{2})";
		
		String str2 ="2018-04-27";
		
		boolean b2= Pattern.matches(pattern, str2);
		System.out.println(b2);
	
	
	
	}

}
