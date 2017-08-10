package com.lcdt.web.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomNoUtil {

	/**
	 * 
	 * 创建指定数量的随机字符串
	 * 
	 * 
	 * 
	 * @param numberFlag 是否是数字
	 * @param length 内容长度
	 * 
	 * @return
	 */

	public static String createRandom(boolean numberFlag, int length) {

		String retStr = "";

		String strTable = numberFlag ? "1234567890"

		: "1234567890abcdefghijkmnpqrstuvwxyz";

		int len = strTable.length();

		boolean bDone = true;

		do {

			retStr = "";

			int count = 0;

			for (int i = 0; i < length; i++) {

				double dblR = Math.random() * len;

				int intR = (int) Math.floor(dblR);

				char c = strTable.charAt(intR);

				if (('0' <= c) && (c <= '9')) {

					count++;

				}

				retStr += strTable.charAt(intR);

			}

			if (count >= 2) {

				bDone = false;

			}

		} while (bDone);

		return retStr;

	}
	
	/**
	 * 获得随机数（带字母，最多36个）
	 * @param count
	 * @return
	 */
	public static String generateWord(int count) {  
		if(count >36){
			count =36;
		}
        String[] beforeShuffle = new String[] { "0","1","2", "3", "4", "5", "6", "7",  
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
                "W", "X", "Y", "Z" };  
        List list = Arrays.asList(beforeShuffle);  
        Collections.shuffle(list);  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < list.size(); i++) {  
            sb.append(list.get(i));  
        }  
        String afterShuffle = sb.toString();  
        int start =36-count;
        String result = afterShuffle.substring(start, 36);  
        return result;  
    }
}
