package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imm.main.IMMClient;
import com.aliyuncs.imm.model.v20170906.ConvertOfficeFormatRequest;
import com.aliyuncs.imm.model.v20170906.ConvertOfficeFormatResponse;
import com.lcdt.userinfo.config.AliyunOssConfig;
import com.lcdt.userinfo.utils.AliyunOss;
import com.lcdt.util.MD5;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ss on 2017/11/20.
 */
@RestController
public class AliyunOssApi {

	@Autowired
	private AliyunOssConfig aliyunOssConfig;

	@RequestMapping("/oss")
	public String getPolicy() {
		String endpoint =aliyunOssConfig.getEndpoint();
		String accessId = aliyunOssConfig.getAccessId();
		String accessKey = aliyunOssConfig.getAccessKey();
		String dir = aliyunOssConfig.getDir();
		String host = aliyunOssConfig.getHost();
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
			JSONObject ja1 = JSONObject.parseObject(JSON.toJSONString(respMap));
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

	@ApiOperation("office文件预览")
	@RequestMapping(value = "/fileView",method = RequestMethod.GET)
	public JSONObject  fileReview(@RequestParam String url)
	{
		if(!StringUtils.isEmpty(url)&&(url.contains("https://") || url.contains("http://")))
		{
			url = url.replace("http://clms-dtd.oss-cn-beijing.aliyuncs.com/","").replace("https://clms-dtd.oss-cn-beijing.aliyuncs.com/","").replace("http://img.datuodui.com/","").replace("https://img.datuodui.com/","");
			//校验原oss文件key是否存在
			if(!AliyunOss.getInstance().validOssKey(url))
			{
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "该附件已经丢失，请重新上传！");
				jsonObject.put("viewUrl", "");
				jsonObject.put("code", -1);
				return jsonObject;
			}
			String fileName = "";
			int dot = url.lastIndexOf('.');
			if ((dot >-1) && (dot < (url.length()))) {
				fileName= url.substring(0, dot);
			}
			//校验源文件预览文件key是否存在
			String key ="attachments/"+new MD5().getMD5ofStr(fileName).toLowerCase()+"/1.pdf";
			if(AliyunOss.getInstance().validOssKey(key))
			{
				//如果key已经存在，则跳过生成预览文件
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "预览文件已经存在！");
				jsonObject.put("viewUrl", aliyunOssConfig.getEndpoint()+"/"+key);
				jsonObject.put("code", 0);
				return jsonObject;
			}
			//同步
			String accessKeyId = aliyunOssConfig.getAccessId();  //RAM 中 test 子账号的 AK ID
			String accessKeySecret = aliyunOssConfig.getAccessKey(); //RAM 中 test 子账号的 AK Secret
			IMMClient client = new IMMClient("cn-beijing", accessKeyId, accessKeySecret);
			ConvertOfficeFormatRequest req = new ConvertOfficeFormatRequest();
			req.setProject(aliyunOssConfig.getProject());
			req.setSrcUri("oss://clms-dtd/"+url);
			req.setTgtUri("oss://clms-dtd/attachments/"+new MD5().getMD5ofStr(fileName).toLowerCase());
			req.setTgtType(aliyunOssConfig.getGtType());
			try{
				ConvertOfficeFormatResponse resp = client.getResponse(req);
				System.out.println("PageCount="+resp.getPageCount());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "预览文件生成成功！");
				jsonObject.put("viewUrl", aliyunOssConfig.getEndpoint()+"/"+key);
				jsonObject.put("code", 0);
				return jsonObject;
			}catch (ClientException e) {
				System.out.println("Call convert() Fail");
				e.printStackTrace();
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", "请求异常！");
				jsonObject.put("viewUrl", "");
				jsonObject.put("code", -1);
				return jsonObject;
			}
		}
		else{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("message", "预览文件生成失败！");
			jsonObject.put("viewUrl", "");
			jsonObject.put("code", -1);
			return jsonObject;
		}
	}

}
