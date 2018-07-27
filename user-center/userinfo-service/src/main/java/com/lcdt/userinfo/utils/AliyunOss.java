package com.lcdt.userinfo.utils;

import com.aliyun.oss.OSSClient;

/**
 * Created by xrr on 2018/7/27.
 * 阿里云oss接口
 */
public class AliyunOss {

    private static AliyunOss aliyunOss = null;
    public static AliyunOss getInstance() {

        if (aliyunOss == null) {
            aliyunOss = new AliyunOss();
        }
        return aliyunOss;
    }


    //校验是否存在osskey
    public boolean validOssKey(String url)
    {
        OSSClient ossClient = new OSSClient(
                "https://oss-cn-beijing.aliyuncs.com",
                "89nsjzR8irwKjep7",
                "F8d08IUID5tFtWI9c88e8qfgbko62s");
        if (url != null && !url.equals("")) {
            // Object是否存在
            boolean found = ossClient.doesObjectExist(
                    "clms-dtd", url);
            if (found) {
                ossClient.shutdown();
                return true;
            } else {
                ossClient.shutdown();
                return false;
            }
            // 关闭client
        } else {
            ossClient.shutdown();
            return false;
        }
    }
}
