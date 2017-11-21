package com.lcdt.client.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.client.dao.MyClientLinkmanMapper;
import com.lcdt.client.model.MyClient;
import com.lcdt.client.model.MyClientLinkman;
import com.lcdt.client.service.MyClientService;
import com.lcdt.client.dao.MyClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */
@Service
@Transactional
public class MyClientServiceImpl implements MyClientService {

    @Autowired
    private MyClientMapper myClientMapper;

    @Autowired
    private MyClientLinkmanMapper myClientLinkmanMapper;

    @Override
    public List<MyClient> getMyClientList(MyClient myClient, PageInfo pageInfo) {
        PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize());
        return myClientMapper.selectByCondition(myClient);
    }

    @Override
    public MyClient getMyClientDetail(Long myClientId) {
        return myClientMapper.selectByPrimaryKey(myClientId);
    }

    @Override
    public List<MyClientLinkman> getMyClientLinkman(Long myClientId) {
        return myClientLinkmanMapper.selectByMyClientId(myClientId);
    }

    @Override
    public int updateMyClient(MyClient myClient) {
        return myClientMapper.updateByPrimaryKey(myClient);
    }

    @Override
    public int addMyClient(MyClient myClient) {
        return myClientMapper.insert(myClient);
    }

    @Override
    public int delMyClient(Long myClientId) {
        return myClientMapper.deleteByPrimaryKey(myClientId);
    }

    @Override
    public int toggleMyClientStatus(MyClient myClient) {
        return myClientMapper.updateStatus(myClient);
    }

    @Override
    public int updateMyClientLinkman(MyClientLinkman myClientLinkman) {
        return myClientLinkmanMapper.updateByPrimaryKey(myClientLinkman);
    }

    @Override
    public int addMyClientLinkman(MyClientLinkman myClientLinkman) {
        return myClientLinkmanMapper.insert(myClientLinkman);
    }

    @Override
    public int delMyClientLinkman(Long myClientLinkmanId) {
        return myClientLinkmanMapper.deleteByPrimaryKey(myClientLinkmanId);
    }

    @Override
    public int setDefaultLinkman(Long myClientLinkmanId) {
        MyClientLinkman myClientLinkman = new MyClientLinkman();
        myClientLinkman.setIsDefault(new Short("1"));
        myClientLinkman.setMyClientLinkmanId(myClientLinkmanId);
        return myClientLinkmanMapper.updateIsDefault(myClientLinkman);
    }
}
