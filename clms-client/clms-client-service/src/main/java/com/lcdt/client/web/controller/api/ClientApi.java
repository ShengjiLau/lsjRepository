package com.lcdt.client.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.client.model.Client;
import com.lcdt.client.service.ClientService;
import com.lcdt.client.web.dto.ClientListDto;
import com.lcdt.client.web.dto.ClientParamsDto;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.util.WebProduces;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangbinq on 2017/11/20.
 */
@RestController
@RequestMapping("/api/traffic/client")
@Api(value = "客户接口",description = "运输客户模块接口")
public class ClientApi {

    @Autowired
    private ClientService clientService;


    @ApiOperation("我的客户列表")
    @RequestMapping(value = "/clientList", produces = WebProduces.JSON_UTF_8, method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_list')")
    public ClientListDto clientList(@Validated ClientParamsDto dto,
                                    @ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                    @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {

      Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("page_no", pageNo);
        map.put("page_size", pageSize);
        //map.put("clientType", ConstantVO.TRAFFIC_CLIENT_TYPE);//运输客户
        if (StringUtil.isNotEmpty(dto.getComplexContition())) {
           map.put("complexStr",dto.getComplexContition());
        }
        if (dto.getStatus()!=null) {
            map.put("status",dto.getStatus());
        }
        if (StringUtil.isNotEmpty(dto.getProvince())) {
            map.put("province",dto.getProvince());
        }
        if (StringUtil.isNotEmpty(dto.getCity())) {
            map.put("city",dto.getCity());
        }
        if (StringUtil.isNotEmpty(dto.getCounty())) {
            map.put("county",dto.getCounty());
        }
        PageInfo pageInfo = clientService.getMyClientList(map);
        ClientListDto dto1 = new ClientListDto();
        dto1.setList(pageInfo.getList());
        dto1.setTotal(pageInfo.getTotal());
        return dto1;
    }


    /**
     * 客户详情
     *
     * @return
     */
/*
    @ApiOperation("客户详情")
    @RequestMapping(value = "/clientDetail",method = RequestMethod.POST)
   // @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('traffic_client_list')")
    public MyClient clientDetail(@ApiParam(value = "客户ID",required = true) @RequestParam Long myClientId) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        return myClientService.getMyClientDetail(myClientId);
    }
*/






    /**
     * 客户新增
     *
     * @return
     */
    @ApiOperation("新增客户")
    @RequestMapping(value = "/clientAdd",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('client_add')")
    public Client clientAdd(@Validated ClientParamsDto dto) {



        return null;
    }






    /**
     * 客户编辑
     *
     * @return
     */
    @ApiOperation("客户编辑")
    @RequestMapping(value = "/clientEdit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('client_add')")
    public Client clientEdit(@Validated ClientParamsDto dto) {



        return null;
    }


}
