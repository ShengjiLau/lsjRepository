package com.lsj;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test04 {

	public static void main(String[] args) {
		JSONObject job =new JSONObject();
		job.put("1","q");
		job.put("2","w");
		job.put("3","a");
		JSONArray jar = new JSONArray();
		jar.add(job);
		jar.add("hello");
		jar.add(1,"w");
		System.out.println(jar.toJSONString());
		

	}

}
