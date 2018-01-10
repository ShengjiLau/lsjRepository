package com.lcdt.notify.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ResponseData;
import com.lcdt.notify.dao.WebMessageDao;
import com.lcdt.notify.dao.WebMessageMapper;
import com.lcdt.notify.model.WebMessage;
import com.lcdt.notify.webnotify.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/webmessages")
public class WebNotifyController {


    @Autowired
    WebMessageDao webMessageDao;

    @Autowired
    MessageService messageService;

    @Autowired
    WebMessageMapper mapper;
    /**
     * 获取通知消息
     */
    @RequestMapping("/list")
    public PageResultDto<WebMessage> messageList(Integer pageNo, Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        PageHelper.startPage(pageNo, pageSize);
        List<WebMessage> webMessages = webMessageDao.selectUnReadMessage(companyId, userId);
        return new PageResultDto(webMessages);
    }


    /**
     * 设置消息已读
     */
    @RequestMapping("/read")
    public WebMessage readMessage(Long messageId) {
        Long userId = SecurityInfoGetter.getUser().getUserId();
        Long companyId = SecurityInfoGetter.getCompanyId();
        messageService.readMessage(messageId,companyId,userId);
        WebMessage webMessage = mapper.selectByPrimaryKey(messageId);
        return webMessage;
    }


    /**
     * 消息未读数
     */
    @RequestMapping("/unreadcount")
    public String messageTotalNum() {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        Integer integer = webMessageDao.unreadMessageCount(companyId, userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "获取成功");
        return jsonObject.toString();
    }




    /**
     * 删除消息
     */
    @RequestMapping("/remove")
    public String removeMessage(Long messageId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
//        Integer integer = webMessageDao.unreadMessageCount(companyId, userId);
        webMessageDao.removeMessage(messageId,companyId,userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "删除成功");
        return jsonObject.toString();
    }


}
