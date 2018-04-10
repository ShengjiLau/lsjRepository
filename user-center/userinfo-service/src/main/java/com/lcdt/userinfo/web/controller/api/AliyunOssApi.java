package com.lcdt.userinfo.web.controller.api;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ss on 2017/11/20.
 */
@RestController
public class AliyunOssApi {

	@RequestMapping("/oss")
	public String getPolicy() {
		String endPointWithoutProtocol = "oss-cn-beijing.aliyuncs.com";
		String endpoint = "https://oss-cn-beijing.aliyuncs.com";
		String accessId = "89nsjzR8irwKjep7";
		String accessKey = "F8d08IUID5tFtWI9c88e8qfgbko62s";
		String bucket = "clms-dtd";
		String dir = "clms-web";
		String host = "https://" + bucket + "." + endPointWithoutProtocol;
		OSSClient client = new OSSClient(endpoint, accessId, accessKey);
		try {
			long expireTime = 300;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);

			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessid", accessId);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));
			JSONObject ja1 = JSONObject.fromObject(respMap);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", ja1);
			jsonObject.put("code", 0);

			return jsonObject.toString();
		} catch (Exception e)
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "请求异常");
			jsonObject.put("code", -1);
			return jsonObject.toString();
		}
	}

}
