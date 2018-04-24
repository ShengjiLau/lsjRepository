package com.lsj;

import java.util.Random;
import java.util.Scanner;

public class Test008 {

	public static void main(String[] args) {
//		Long [] temp = new Long[5];
//		temp[0]=(long) 1;
//		
//		temp[1]=(long) 2;
//
//		temp[2]=(long) 2;
//		temp[3]=(long) 2;
//    for(int i=0;i<temp.length;i++) {
//	System.out.println(temp[i].toString());
//    }
//		Random rand =new Random();
//		int i =rand.nextInt(100);
//		//int j= rand.next();
//		System.out.println(i);
//		
//		
//	}
		
		Scanner in =new Scanner(System.in);
		System.out.println("请输入一个整数");
		
		while(in.hasNextLine()) {
			String tString = in.next();
			int num = in.nextInt();
			//  System.out.println("请输入一个字符串");
			//   String str = in.nextLine();
			System.out.println("num:"+num);
			String str = in.next();
			System.out.println("num="+num+",str="+str);
			String s3 = in.nextLine();
			System.out.println("s3:"+s3);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}


}
