package com.lcdt.login.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.lcdt.login.mapper.ValidLogMapper;
import com.lcdt.login.model.ValidLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class ValidCodeCountService extends ServiceImpl<ValidLogMapper,ValidLog>{


    public boolean phoneTodayCount(String phoneNum){

        if (StringUtils.isEmpty(phoneNum)) {
            return true;
        }
        List<ValidLog> logs = baseMapper.selectList(new EntityWrapper<ValidLog>().eq("phone", phoneNum));
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
        List<ValidLog> logs = baseMapper.selectList(new EntityWrapper<ValidLog>().eq("phone", phone));
        if (CollectionUtils.isEmpty(logs)) {
            ValidLog validLog = new ValidLog();
            validLog.setLastSendTime(new Date());
            validLog.setPhone(phone);
            validLog.setSendCount(1);
            baseMapper.insert(validLog);
        } else {
            ValidLog validLog = logs.get(0);
            int sendCount = validLog.getSendCount();
            if (sendCount >= 3) {
                validLog.setSendCount(1);
            }else{
                validLog.setSendCount(validLog.getSendCount() + 1);
            }
            baseMapper.updateById(validLog);
        }
    }



}
