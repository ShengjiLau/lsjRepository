package com.lcdt.pay.utils;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import javax.servlet.http.HttpServletRequest;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommonUtils {
    public static String getRandomOrderId() {
        // UUID.randomUUID().toString().replace("-","")
        Random random = new Random(System.currentTimeMillis());
        int value = random.nextInt();
        while (value < 0) {
            value = random.nextInt();
        }
        return value + "";
    }

    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                //增加CDATA标记
                boolean cdata = true;
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

   public static String successXml(Object object){
       return xstream.toXML(object);
   }


   public static String joinStringWithToken(List<Long> longs,char token){
       final StringBuffer stringBuffer = new StringBuffer();

       for (int i = 0;i < longs.size();i++) {
           if (i > 0) {
               stringBuffer.append(token);
           }
           stringBuffer.append(longs.get(i));
       }
       return stringBuffer.toString();
   }
}
