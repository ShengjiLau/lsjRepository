package com.lcdt.notify.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TemplateParser {

    static Pattern pattern = Pattern.compile("(\\$\\{([a-zA-Z0-9]+)\\})");

    public static String parseTemplateParams(String tempalte, Map<String,String> valueBean) {
        Matcher m = pattern.matcher(tempalte);
        StringBuffer stringBuffer = new StringBuffer();
        while (m.find()) {
            String key = m.group(2);
            String value = resolveTemplateVariables(key, valueBean);
            m.appendReplacement(stringBuffer, value);
        }
        m.appendTail(stringBuffer);
        return stringBuffer.toString();
    }


    public static String resolveTemplateVariables(String propertyName,Map<String,String> bean){
        String s = bean.get(propertyName);
        return s == null ? "" : s;
    }

    public static String resolveTemplateVariables(String propertyName,Object bean){
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
        if (propertyDescriptor != null) {
            Method readMethod = propertyDescriptor.getReadMethod();
            try {
                Object invoke = readMethod.invoke(bean);
                return invoke.toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return "";
    }


}
