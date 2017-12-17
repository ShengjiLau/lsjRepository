package com.lcdt.notify.web.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.notify.dao.MsgTemplateMapper;
import com.lcdt.notify.dao.NotifyMapper;
import com.lcdt.notify.model.MsgTemplate;
import com.lcdt.notify.model.Notify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notify")
public class ConfigApi {

    @Autowired
    NotifyMapper mapper;


    @Autowired
    MsgTemplateMapper templateMapper;

    /**
     * 添加通知节点
     */
    @RequestMapping(value = "/addnotify",method = RequestMethod.POST)
    public Notify addNotify(Notify notify){
        mapper.insert(notify);
        return notify;
    }

    @RequestMapping(value = "/addtemplate",method = RequestMethod.POST)
    public MsgTemplate addTemplate(MsgTemplate template){
        templateMapper.insert(template);
        return template;
    }

    @RequestMapping(value = "/templates",method = RequestMethod.GET)
    public List<MsgTemplate> templates(){
        List<MsgTemplate> msgTemplates = templateMapper.selectAll();
        return msgTemplates;
    }

    public void updateTemplate(MsgTemplate msgTemplate){

    }

    @RequestMapping(value = "/classisvalid",method = RequestMethod.POST)
    public String checkClass(String className){
        JSONObject jsonObject = new JSONObject();

        try {
            Class<?> aClass = this.getClass().forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            jsonObject.put("code", -1);
        }
        jsonObject.put("code", 0);
        return jsonObject.toJSONString();
    }

}
