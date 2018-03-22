package com.lcdt.notify.rpcserviceImpl;

import com.lcdt.notify.dao.ValidLogMapper;
import com.lcdt.notify.model.ValidLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ValidCodeCountService {

    @Autowired
    ValidLogMapper mapper;

    public boolean phoneTodayCount(String phoneNum){

        if (StringUtils.isEmpty(phoneNum)) {
            return true;
        }
        List<ValidLog> logs = mapper.selectByPhone(phoneNum);
        if (!CollectionUtils.isEmpty(logs)) {
            ValidLog validLog = logs.get(0);
            if (validLog.getSendCount() >= 3) {
                long current = System.currentTimeMillis();
                Date lastSendTime = validLog.getLastSendTime();
                long lastSendTimeS = lastSendTime.getTime();

                if (current - lastSendTimeS > 12 * 60 * 60 * 1000) {
                    return true;
                }
                return false;
            }
            return true;
        }
        return true;
    }

    public void updateValidCodeLog(String phone) {
        List<ValidLog> logs = mapper.selectByPhone(phone);
        if (CollectionUtils.isEmpty(logs)) {
            ValidLog validLog = new ValidLog();
            validLog.setLastSendTime(new Date());
            validLog.setPhone(phone);
            validLog.setSendCount(1);
            mapper.insert(validLog);
        } else {
            ValidLog validLog = logs.get(0);
            int sendCount = validLog.getSendCount();
            if (sendCount >= 3) {
                validLog.setSendCount(1);
            }else{
                validLog.setSendCount(validLog.getSendCount() + 1);
            }
            mapper.updateByPrimaryKey(validLog);
        }
    }



}
