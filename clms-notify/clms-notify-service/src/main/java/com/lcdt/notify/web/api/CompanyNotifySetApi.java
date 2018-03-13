package com.lcdt.notify.web.api;

import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.notify.dao.NotifyDao;
import com.lcdt.notify.model.CompanyNotifySetting;
import com.lcdt.notify.service.NotifyService;
import com.lcdt.notify.web.PageResultDto;
import com.lcdt.notify.web.dto.NotifySetDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api/notifyset")
public class CompanyNotifySetApi {


    @Autowired
    NotifyDao notifyDao;

    @Autowired
    NotifyService notifyService;

    /**
     * 获取通知设置列表
     */
    @ApiOperation("返回设置通知列表")
    @RequestMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('notify_set_list')")
    public PageResultDto<NotifySetDto> alltemplateSetting(String category, Integer pageNo, Integer pageSize){
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageHelper.startPage(pageNo, pageSize);
        List<NotifySetDto> notifySetDtos = notifyDao.selectNotifySetByCateGory(companyId, category);
        return new PageResultDto<NotifySetDto>(notifySetDtos);
    }

    @ApiOperation("短信通知开关")
    @RequestMapping("/toggersms")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('notify_set_toggle')")
    public CompanyNotifySetting customeNotifyTemplate(Long notifyId,boolean isEnable) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        CompanyNotifySetting companyNotifySetting = notifyService.setNotifyEnable(companyId, notifyId, isEnable);
        return companyNotifySetting;
    }

    @ApiOperation("web通知开关")
    @RequestMapping("/toggerweb")
    public CompanyNotifySetting configWebNotify(Long notifyId,boolean isEnable) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        CompanyNotifySetting companyNotifySetting = notifyService.setWebNotifyEnable(companyId, notifyId, isEnable);
        return companyNotifySetting;
    }

}
