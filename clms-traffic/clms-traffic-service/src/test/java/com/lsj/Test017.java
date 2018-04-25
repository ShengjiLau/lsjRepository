package com.lsj;

import com.alibaba.fastjson.JSONObject;

public class Test017 {

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message","test1");
		System.out.println(jsonObject.get("message"));
		jsonObject.put("message","test2");
		System.out.println(jsonObject.get("message"));

	}

}
