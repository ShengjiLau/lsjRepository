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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/webmessages")
@Api("网页通知轮寻接口")
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
    @ApiOperation("获取消息列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public PageResultDto<WebMessage> messageList(@RequestParam(required = false) String messageCategory,Integer pageNo, Integer pageSize,@RequestParam(required = false)Integer isread) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        PageHelper.startPage(pageNo, pageSize);
        List<WebMessage> webMessages = webMessageDao.selectUnReadMessage(companyId, userId,messageCategory,isread);
        return new PageResultDto(webMessages);
    }


    /**
     * 设置消息已读
     */
    @ApiOperation("设置消息已读")
    @RequestMapping(value = "/read",method = RequestMethod.GET)
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
    @ApiOperation("获取消息未读数")
    @RequestMapping(value = "/unreadcount",method = RequestMethod.GET)
    public String messageTotalNum(@RequestParam(required = false) String messageCategory,@RequestParam(required = false) Integer isread) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Long userId = SecurityInfoGetter.getUser().getUserId();
        Integer integer = webMessageDao.unreadMessageCount(companyId, userId,messageCategory,isread);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "获取成功");
        jsonObject.put("data", integer);
        return jsonObject.toString();
    }




    /**
     * 删除消息
     */
    @ApiOperation("删除消息")
    @RequestMapping(value = "/remove",method = RequestMethod.GET)
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
