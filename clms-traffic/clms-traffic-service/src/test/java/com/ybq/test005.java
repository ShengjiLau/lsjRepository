package com.ybq;

public class test005 {

	public static void main(String[] args) {
		int i=1;
		int j =1;
		System.out.println("hello1");
		if(i==j) {
			throw new RuntimeException("test");
		}
		
		System.out.println("hello2");
	}

}
