package com.lcdt.login.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.login.exception.ValidCodeExistException;
import com.lcdt.login.service.ValidCodeCountService;
import com.lcdt.notify.rpcservice.NotifyService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.util.RandomNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Service
public class ValidCodeService {

    @Reference
    private UserService userService;

    @Reference
    private NotifyService notifyService;

    @Autowired
    ValidCodeCountService validCodeCountService;

    private static final String sessionPrefixKey = "smsvalidcode_";

    public boolean isCodeCorrect(String code,HttpServletRequest request,String tag,String phoneNum){
        ValidCodeBean codeBean = getCodeBean(request, tag);
        if (codeBean == null) {
            return false;
        }else{
            boolean result = codeBean.getPhoneNums().equals(phoneNum) && code.equals(codeBean.getValidCode());
            return result;
        }
    }


    /**
     * 发送短信验证码
     * @param request
     * @param tag
     * @param timeout
     */
    public boolean sendValidCode(HttpServletRequest request,String tag,Integer timeout,String phoneNum) throws ValidCodeExistException {
        if (validCodeCountService.phoneTodayCount(phoneNum)) {
            HttpSession session = request.getSession(true);
            String sessionKey = sessionPrefixKey + tag;
            ValidCodeBean attribute = getCodeBean(request, tag);
            if (attribute == null || canSendCode(attribute)) {
                ValidCodeBean validCodeBean = new ValidCodeBean();
                String random = RandomNoUtil.createRandom(true, 4);
                validCodeBean.setCreateTime(System.currentTimeMillis());
                validCodeBean.setValidCode(random);
                validCodeBean.setTimeout(timeout);
                validCodeBean.setPhoneNums(phoneNum);
                session.setAttribute(sessionKey,validCodeBean);
                notifyService.sendSms(new String[]{phoneNum},generateCodeString(random));
                validCodeCountService.updateValidCodeLog(phoneNum);
                return true;
            }
            throw new ValidCodeExistException();
        }
        return false;
    }

    private  boolean canSendCode(ValidCodeBean codeBean){

        if (codeBean == null) {
            return false;
        }

        if (codeBean.getCreateTime() == 0) {
            return false;
        }

        if (codeBean.getTimeout() == 0) {
            return true;
        }

        long createTime = codeBean.getCreateTime();
        long expireTime = createTime + 60 * 1000;
        long currentTime = System.currentTimeMillis();

        if (currentTime <= expireTime) {
            return false;
        }

        return true;
    }


    private String generateCodeString(String code){
        String format = "提醒您，验证码为：%s，%s分钟内有效，感谢使用！如非本人操作，请忽略！\n";
        String format1 = String.format(format, code, "15");
        return format1;
    }


    public ValidCodeBean getCodeBean(HttpServletRequest request, String tag) {
        String sessionKey = sessionPrefixKey + tag;
        HttpSession session = request.getSession(true);
        Object attribute = session.getAttribute(sessionKey);
        if (attribute != null) {
            return (ValidCodeBean) attribute;
        }
        return null;
    }

    /**
     * 判断验证码是否在有效期
     * @param codeBean
     * @return
     */
    private boolean isCodeValid(ValidCodeBean codeBean) {
        if (codeBean == null) {
            return false;
        }

        if (codeBean.getCreateTime() == 0) {
            return false;
        }

        if (codeBean.getTimeout() == 0) {
            return true;
        }

        long createTime = codeBean.getCreateTime();
        long expireTime = createTime + codeBean.getTimeout() * 1000;
        long currentTime = System.currentTimeMillis();

        if (currentTime <= expireTime) {
            return true;
        }
        return false;
    }


    static class ValidCodeBean implements Serializable{
        String validCode;
        long createTime;
        int timeout;
        String phoneNums;

        public String getPhoneNums() {
            return phoneNums;
        }

        public void setPhoneNums(String phoneNums) {
            this.phoneNums = phoneNums;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public String getValidCode() {
            return validCode;
        }

        public void setValidCode(String validCode) {
            this.validCode = validCode;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }
}
