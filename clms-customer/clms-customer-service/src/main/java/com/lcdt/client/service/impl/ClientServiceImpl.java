package com.lcdt.client.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.client.dao.ClientLinkmanMapper;
import com.lcdt.client.dao.ClientMapper;
import com.lcdt.client.dao.ClientTypeRelationMapper;
import com.lcdt.client.exception.ClientException;
import com.lcdt.client.model.Client;
import com.lcdt.client.model.ClientLinkman;
import com.lcdt.client.model.ClientTypeRelation;
import com.lcdt.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR liuh
 * @DATE 2017-11-16
 */

public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientLinkmanMapper clientLinkmanMapper;

    @Autowired
    private ClientTypeRelationMapper clientTypeRelationMapper;


    @Transactional(readOnly = true)
    @Override
    public PageInfo getClientList(Map m) {
        int pageNo = 1;
        int pageSize = 0; //0表示所有

        if (m.containsKey("page_no")) {
            if (m.get("page_no") != null) {
                pageNo = (Integer) m.get("page_no");
            }
        }
        if (m.containsKey("page_size")) {
            if (m.get("page_size") != null) {
                pageSize = (Integer) m.get("page_size");
            }
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Client> list = clientMapper.selectByCondition(m);
        PageInfo pageInfo = new PageInfo(list);

        return  pageInfo;
    }

    @Override
    public Client getClientDetail(Long myClientId) {
        return clientMapper.selectByPrimaryKey(myClientId);
    }


    @Transient
    @Override
    public int addClient(Client client) {
        Map map = new HashMap();
        map.put("companyId", client.getCompanyId());
        map.put("clientName", client.getClientName());
        List<Client> list = clientMapper.selectByCondition(map);
        if (list.size()>0) {
            throw new ClientException("客户名称已存在");
        }
        int flag = clientMapper.insert(client);
        if (flag>0) {
            //默认联系人
            ClientLinkman clientLinkman = new ClientLinkman();
            clientLinkman.setName(client.getLinkMan());
            clientLinkman.setDuty(client.getLinkDuty());
            clientLinkman.setMobile(client.getLinkTel());
            clientLinkman.setMail(client.getLinkEmail());
            clientLinkman.setIsDefault((short) 1); //默认联系人
            clientLinkman.setCompanyId(client.getCompanyId());
            clientLinkman.setProvince(client.getProvince());
            clientLinkman.setCity(client.getCity());
            clientLinkman.setCounty(client.getCounty());
            clientLinkman.setDetailAddress(client.getDetailAddress());
            clientLinkman.setClientId(client.getClientId());
            clientLinkmanMapper.insert(clientLinkman);

            //组关系表
            if (!StringUtils.isEmpty(client.getClientTypes())) {
                String[] typeArrays = client.getClientTypes().split(",");  //传过来的值用逗号隔开
                for (int i=0; i<typeArrays.length; i++) {

                    ClientTypeRelation relationObj = new ClientTypeRelation();
                    relationObj.setClientId(client.getClientId());
                    relationObj.setClientName(client.getClientName());
                    relationObj.setClientType(Short.valueOf(typeArrays[i]));
                    relationObj.setCompanyId(client.getCompanyId());
                    clientTypeRelationMapper.insert(relationObj);
                }
            }
        }
        return flag;
    }





/*    @Override
    public List<MyClientLinkman> getMyClientLinkman(Long myClientId) {
        return myClientLinkmanMapper.selectByMyClientId(myClientId);
    }

    @Override
    public int updateMyClient(MyClient myClient) {
        return myClientMapper.updateByPrimaryKey(myClient);
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
    public int de·                                                                                                          lMyClientLinkman(Long myClientLinkmanId) {
        return myClientLinkmanMapper.deleteByPrimaryKey(myClientLinkmanId);
    }

    @Override
    public int setDefaultLinkman(Long myClientLinkmanId) {
        MyClientLinkman myClientLinkman = new MyClientLinkman();
        myClientLinkman.setIsDefault(new Short("1"));
        myClientLinkman.setMyClientId(myClientLinkmanId);
        return myClientLinkmanMapper.updateIsDefault(myClientLinkman);
    }*/
}
