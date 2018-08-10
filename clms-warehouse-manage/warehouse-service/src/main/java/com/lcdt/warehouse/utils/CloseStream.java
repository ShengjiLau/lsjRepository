package com.lcdt.warehouse.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Sheng-ji Lau
 * @date 2018年8月10日
 * @version
 * @Description: TODO 
 */
public class CloseStream {
	
	
	public static void close(Closeable stream) {
		if (null !=  stream) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
