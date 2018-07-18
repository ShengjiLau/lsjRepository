package com.lcdt.contract.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author Sheng-ji Lau
 * @date 2018年7月17日
 * @version
 * @Description: TODO 
 */
public class RequestValidated {
	
	public static Map<String,String> validatedRequest(BindingResult bindingResult) {
		Map<String,String> map = new HashMap<String,String>();
	
		if (bindingResult.hasErrors()) {
			List<FieldError> list = bindingResult.getFieldErrors();
	       	for(FieldError error:list) {
	       		String n = error.getField();
	       		String m = error.getDefaultMessage();
	       		map.put(n,m);
	       	}
		}
       	
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
