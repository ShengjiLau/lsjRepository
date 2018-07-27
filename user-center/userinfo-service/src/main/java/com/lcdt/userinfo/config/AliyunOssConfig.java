package com.lcdt.userinfo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by xrr on 2018/7/26.
 * 阿里云oss配置参数
 */
@ConfigurationProperties(prefix = "aliyunoss")
@Component
public class AliyunOssConfig {
    private String endPointWithoutProtocol;
    private String endpoint;
    private String accessId;
    private String accessKey;
    private String bucket;
    private String dir;
    private String host;

    private String project;
    private String gtType;
    public String getEndPointWithoutProtocol() {
        return endPointWithoutProtocol;
    }

    public void setEndPointWithoutProtocol(String endPointWithoutProtocol) {
        this.endPointWithoutProtocol = endPointWithoutProtocol;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getGtType() {
        return gtType;
    }

    public void setGtType(String gtType) {
        this.gtType = gtType;
    }
}
