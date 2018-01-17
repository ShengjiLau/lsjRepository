package com.lcdt.notify.webnotify;

import com.lcdt.notify.dao.WebMessageDao;
import com.lcdt.notify.dao.WebMessageMapper;
import com.lcdt.notify.model.WebMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    WebMessageMapper webMessageMapper;

    @Autowired
    WebMessageDao webMessageDao;

    public void createWebMessage(String category,String content,Long receiveCompanyId,Long receiverUserId,String url){
        WebMessage webMessage = new WebMessage();
        webMessage.setMessageContent(content);
        webMessage.setMessageReceiveCompanyId(receiveCompanyId);
        webMessage.setMessageReceiveUserId(receiverUserId);
        webMessage.setMessageIsread(false);
        webMessage.setMessageAttachUrl(url);
        webMessage.setMessageCategory(category);
        webMessageMapper.insert(webMessage);
    }

    public List<WebMessage> messages(Long companyId,Long userId,String category){
        List<WebMessage> webMessages = webMessageDao.selectUnReadMessage(companyId, userId,category);
        return webMessages;
    }

    public void readMessage(Long messageId,Long companyId,Long userId){
        webMessageDao.setReadMessage(messageId,companyId,userId);
    }

}
