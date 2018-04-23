package com.lsj;

import com.alibaba.fastjson.JSONObject;

public class Test016 {

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code",-1);
		System.out.println(jsonObject.size());
		jsonObject.put("msg","test");
		System.out.println(jsonObject.size());
		jsonObject.clear();
		System.out.println(jsonObject.size());
	}

}
