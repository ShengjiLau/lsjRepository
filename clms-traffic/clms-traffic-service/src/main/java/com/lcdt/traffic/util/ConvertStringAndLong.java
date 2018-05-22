package com.lcdt.traffic.util;

/**
 * @author Sheng-ji Lau
 * @date 2018年5月21日
 * @version
 * @Description: TODO 
 */
public class ConvertStringAndLong {
	
	
	/**
	 * 此方法用于将一定格式的String字符串转化为规定格式的Long类型数组
	 * @param String
	 * @return long[]
	 */
	public static Long[] convertStrToLong(String str) {
		String [] ss = str.split(",");
		Long [] lids = new Long[ss.length];
		for(int i = 0;i < ss.length;i++) {
			lids[i] = Long.valueOf(ss[i]);
		}
		return lids;	
	}
	
	/**
	 * 这个方法用于将Long类型数组转化为规定格式的String字符串
	 * @param long[]
	 * @return String
	 */
	public static String convertLoToStr(Long[] longId) {
		StringBuilder sbd = new StringBuilder();
		for(Long l:longId) {
			sbd.append(l);sbd.append(",");
		}
		sbd.substring(0,sbd.length() - 1);
		
		return sbd.toString();
	}


	
	
	
	
	
	
	
	
	
	
	
	

}
