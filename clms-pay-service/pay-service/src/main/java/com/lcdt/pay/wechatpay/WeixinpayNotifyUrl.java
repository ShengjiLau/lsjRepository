package com.lcdt.pay.wechatpay;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;


/**
 * 微信异步返回结果的接收类
 * @author zhangf
 *
 */
public class WeixinpayNotifyUrl extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2782602288393356427L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject jo = new JSONObject();
		jo.put("return_code", "FAIL");
		try {
        	
            Map<String, Object> map = getCallbackParams(request);
            if(map != null){
            	if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            		String orderId = map.get("out_trade_no").toString();
            		//这里写成功后的业务逻辑
					jo.put("return_code", "SUCCESS");
					jo.put("return_msg", "OK");
					}
            }else{
            	jo.put("return_msg", "签名验证失败!");
            }
            
       } catch (Exception e) {
    	   jo.put("return_msg", "异常");
           e.printStackTrace();
       }
	}
	
	/**
     * 获取请求参数
     * @Title: getCallbackParams
     * @Description: TODO
     * @param @param request
     * @param @return
     * @param @throws Exception    
     * @return Map<String,String>    
     * @throws
     */
    public Map<String, Object> getCallbackParams(HttpServletRequest request)
            throws Exception {
        InputStream inStream = request.getInputStream();
        if(inStream != null){
        	
        	ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        	byte[] buffer = new byte[1024];
        	int len = 0;
        	while ((len = inStream.read(buffer)) != -1) {
        		outSteam.write(buffer, 0, len);
        	}
        	
        	outSteam.close();
        	inStream.close();
        	String result = new String(outSteam.toByteArray(), "utf-8");
        	boolean usedflag = Signature.checkIsSignValidFromResponseString(result);
        	if(!usedflag){//可能被第三方篡改过
        		return null;
        	}else{
        		return XMLParser.getMapFromXML(result);
        	}
        }
        return null;
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
//		System.out.println("<------------------------------------------我是邪恶的分割线post---------------------------------------------------------------------------->");
		//获取支付宝POST过来反馈信息
		resp.setCharacterEncoding("utf-8");         
		resp.setContentType("text/xml; charset=utf-8");         
		doGet(request, resp);
	}
	
	private String toXmlStr (Object object){
		String returnStr = null;
		try {
			//解决XStream对出现双下划线的bug
			XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

			//将要提交给API的数据对象转换成XML格式数据Post给API
			returnStr = xStreamForRequestPostData.toXML(object);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnStr;
	}

	
}
