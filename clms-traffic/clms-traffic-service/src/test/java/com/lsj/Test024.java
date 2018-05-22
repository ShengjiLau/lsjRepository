package com.lsj;

import com.alibaba.fastjson.JSONObject;

public class Test024 {

	public static void main(String[] args) {
		JSONObject j = new JSONObject();
		j.put("message", "m1");
		j.put("message", "m2");
		j.put("message", "m3");
		
		System.out.println(j.get("message"));

	}

}
