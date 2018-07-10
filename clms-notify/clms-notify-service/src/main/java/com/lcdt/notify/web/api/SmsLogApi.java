package com.lcdt.notify.web.api;

import com.lcdt.notify.dao.SmsLogMapper;
import com.lcdt.notify.model.SmsLog;
import com.lcdt.notify.web.PageResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/smslogapi")
public class SmsLogApi {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @PostMapping("/list")
    public PageResultDto<SmsLog> selectByBusinessNo(String businessNo){
        List<SmsLog> smsLogs = smsLogMapper.selectByBusinessNo(businessNo);
        return new PageResultDto<SmsLog>(smsLogs);
    }

}
