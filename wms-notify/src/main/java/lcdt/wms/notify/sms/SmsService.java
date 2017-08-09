package lcdt.wms.notify.sms;

import lcdt.wms.notify.dto.SmsDto;

/**
 * Created by ybq on 2017/8/9.
 */
public interface SmsService {

    /***
     * 短息发送
     * @param smsDto
     * @param phones
     * @param signature
     * @param message
     */
    boolean sendSms(SmsDto smsDto, String[] phones, String signature, String message);

    /***
     * 短信余额查询
     * @param smsDto
     * @return
     */
    String findSmsBalance(SmsDto smsDto);

}
