package com.sso.client.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ss on 2017/9/18.
 */
public class PropertyUtils {

	public static final String LOGIN_URL = "sso.server.login";

	private static final String PROPERTY_LOCATION = "sso.properties";

	private static Properties properties;

	static {
		loadProps();
	}

	synchronized static private void loadProps(){
		properties = new Properties();
		InputStream resourceAsStream = PropertyUtils.class.getClassLoader().getResourceAsStream(PROPERTY_LOCATION);
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (null != resourceAsStream) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String readProperties(String key){
		if (properties != null) {
			Object o = properties.get(key);
			String property = properties.getProperty(key, "");
			return property;
		}
		return null;
	}

}
