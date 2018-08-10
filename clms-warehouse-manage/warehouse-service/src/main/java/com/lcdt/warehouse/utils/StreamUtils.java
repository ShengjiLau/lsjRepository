package com.lcdt.warehouse.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月10日
 * @version
 * @Description: TODO 
 */
public class StreamUtils {
	
	/**
	 * 关闭流
	 */
	public static void closeStream(Closeable stream) {
		if (null !=  stream) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 设置消息头和ContentType  获取OutputStream流
	 */
	public static OutputStream getOutputStream(HttpServletResponse response, String fileName) {
		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("utf-8"),"iso-8859-1") + "\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
			OutputStream ouputStream = response.getOutputStream();
			return ouputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	

}
