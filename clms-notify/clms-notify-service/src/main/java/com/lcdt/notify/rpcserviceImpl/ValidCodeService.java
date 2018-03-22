package com.lcdt.notify.rpcserviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.notify.rpcservice.IValidCodeService;
import com.lcdt.notify.rpcservice.NotifyService;
import com.lcdt.notify.rpcservice.ValidCodeExistException;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.util.RandomNoUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class ValidCodeService implements IValidCodeService {


    Map<String, ValidCodeBean> vCodeMap = new HashMap<String, ValidCodeBean>();


    @Reference
    private UserService userService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    ValidCodeCountService validCodeCountService;

    private static final String sessionPrefixKey = "smsvalidcode_";

    public boolean isCodeCorrect(String code, String tag, String phoneNum) {
        ValidCodeBean codeBean = getCodeBean(phoneNum, tag);
        if (codeBean == null) {
            return false;
        } else {
            boolean result = codeBean.getPhoneNums().equals(phoneNum) && code.equals(codeBean.getValidCode());
            return result;
        }
    }


    /**
     * 发送短信验证码
     *
     * @param tag
     * @param timeout
     */
    public boolean sendValidCode(String tag, Integer timeout, String phoneNum) throws ValidCodeExistException {
        if (validCodeCountService.phoneTodayCount(phoneNum)) {
            ValidCodeBean attribute = getCodeBean(phoneNum, tag);
            if (attribute == null || canSendCode(attribute)) {
                ValidCodeBean validCodeBean = new ValidCodeBean();
                String random = RandomNoUtil.createRandom(true, 4);
                validCodeBean.setCreateTime(System.currentTimeMillis());
                validCodeBean.setValidCode(random);
                validCodeBean.setTimeout(timeout);
                validCodeBean.setPhoneNums(phoneNum);
                restoreCode(validCodeBean);
                notifyService.sendSms(new String[]{phoneNum}, generateCodeString(random));
                validCodeCountService.updateValidCodeLog(phoneNum);
                return true;
            }
            throw new ValidCodeExistException();
        }
        return false;
    }

    private void restoreCode(ValidCodeBean validCodeBean) {
        vCodeMap.put(validCodeBean.getPhoneNums(), validCodeBean);
    }

    private ValidCodeBean getValidCode(String phone) {
        ValidCodeBean validCodeBean = vCodeMap.get(phone);
        return validCodeBean;
    }

    private boolean canSendCode(ValidCodeBean codeBean) {

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


    private String generateCodeString(String code) {
        String format = "提醒您，验证码为：%s，%s分钟内有效，感谢使用！如非本人操作，请忽略！\n";
        String format1 = String.format(format, code, "15");
        return format1;
    }


    public ValidCodeBean getCodeBean(String phone, String tag) {
        ValidCodeBean validCode = getValidCode(phone);
        return validCode;
    }

    /**
     * 判断验证码是否在有效期
     *
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


    static class ValidCodeBean {
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
