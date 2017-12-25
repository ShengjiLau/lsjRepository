package com.lcdt.converter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

/**
 * Created by ss on 2017/10/30.
 */
public class ClmsMessageConvert extends FastJsonHttpMessageConverter {


	public ClmsMessageConvert() {
		super();
		FastJsonConfig fastJsonConfig = getFastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);

	}


	public JSONObject successObject = new JSONObject();

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
		successObject.put("data", obj);
		successObject.put("message", "请求成功");
		successObject.put("code", 0);
		super.writeInternal(successObject, outputMessage);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return ResponseData.class.isAssignableFrom(clazz);
	}
}
