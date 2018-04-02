package com.lcdt.customer.Utils;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.StringTokenizer;

public class CommonUtil {

    public static String[] splitCommaStr(String str){
        return StringUtils.commaDelimitedListToStringArray(str);
    }


    /**
     * 分号隔开:1;2;3;
     1-销售客户
     2-仓储客户
     3-运输客户
     4-仓储服务商
     5-运输服务商
     6-供应商
     7-其他
     * @param str
     * @return
     */
    public static String reverseCustomerTypesStr(String str) {
        String[] strings = StringUtils.commaDelimitedListToStringArray(str);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < strings.length ;i++) {
            String type = strings[i];
            Integer integer = Integer.valueOf(type);
            Integer integer1 = reverseType(integer);
            stringBuilder.append(integer1);
            if (i < strings.length - 1) {
                stringBuilder.append(',');
            }
        }
        return stringBuilder.toString();
    }

    private static Integer reverseType(Integer type) {
        switch (type) {
            case 1:
                return 6;
            case 3:
                return 5;
            case 2:
                return 4;
            case 6:
                return 1;
            case 5:
                return 3;
            case 4:
                return 2;
                default:
                    return 7;
        }
    }


}
