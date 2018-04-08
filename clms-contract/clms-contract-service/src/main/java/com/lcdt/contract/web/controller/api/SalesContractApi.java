
package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.contract.web.utils.Utils;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by liz on 2018/3/5.
 */
@Api(description = "销售合同管理api")
@RestController
@RequestMapping("/salesContract")
public class SalesContractApi {

    Logger logger = LoggerFactory.getLogger(SalesContractApi.class);

    @Autowired
    private ContractService contractService;


    @ApiOperation(value = "合同列表", notes = "合同列表数据")
    @GetMapping("/contractlist")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_contract_list')")
    public PageBaseDto<List<Contract>> contractList(@Validated ContractDto contractDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        contractDto.setCompanyId(companyId);

        if (StringUtil.isNotEmpty(contractDto.getBeginTime())) { //发布时间
            contractDto.setBeginTime(contractDto.getBeginTime()+" 00:00:00");
        }
        if (StringUtil.isNotEmpty(contractDto.getEndTime())) {
            contractDto.setEndTime(contractDto.getEndTime()+" 23:59:59");
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(contractDto.getPageNum());    //设置页码
        pageInfo.setPageSize(contractDto.getPageSize());  //设置每页条数
        PageInfo<List<Contract>> listPageInfo = contractService.ontractList(contractDto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("合同新建")
    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_sales_contract')")
    public JSONObject addWarehouse(@Validated @RequestBody ContractDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateTime(new Date());
        dto.setContractStatus((short)2);
        dto.setPartyBId(user.getUserId());
        dto.setPartyBName(user.getRealName());
        //设置合同状态
        dto = Utils.getContractStatus(dto);

        int result = contractService.addContract(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "添加成功");
            return jsonObject;
        } else {
            throw new RuntimeException("添加失败");
        }
    }

    @ApiOperation("合同编辑")
    @RequestMapping(value = "/modifyContract", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_sales_contract')")
    public JSONObject modifyWarehouse(@Validated @RequestBody ContractDto dto) {
        User user = SecurityInfoGetter.getUser();
        dto.setPartyBId(user.getUserId());
        dto.setPartyBName(user.getRealName());
        //设置合同状态
        dto = Utils.getContractStatus(dto);
        int result = contractService.modContract(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("合同终止/生效/创建中")
    @RequestMapping(value = "/updateContractStatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_sales_contract_status')")
    public JSONObject updateContractStatus(@ApiParam(value = "合同ID",required = true) @RequestParam Long contractId,
                                        @ApiParam(value = "状态 0-生效 3-失效 2-创建中",required = true) @RequestParam short contractStatus) {
        Contract dto = new Contract();
        dto.setContractId(contractId);
        dto.setContractStatus(contractStatus);
        if(contractStatus == 0){//0生效
            dto.setEffectiveTime(new Date());
        }else if(contractStatus == 3){//3失效
            dto.setTerminationTime(new Date());
        }else{
            dto.setIsDraft((short)1);
        }
        int result = contractService.modContractStatus(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", contractStatus==0?"生效成功":contractStatus==3?"终止成功":"创建成功");
            return jsonObject;
        } else {
            throw new RuntimeException(contractStatus==0?"生效失败":contractStatus==3?"终止失败":"创建失败");
        }
    }

    @ApiOperation("合同详情")
    @RequestMapping(value = "/contractDetail", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_contract_detail')")
    public  JSONObject contractDetail(@ApiParam(value="合同ID")@RequestParam Long contractId){
        Contract dto= contractService.selectByPrimaryKey(contractId);
        JSONObject jsonObject=new JSONObject();
        if(dto!=null) {
            jsonObject.put("code",0);
            jsonObject.put("message","合同详情");
            jsonObject.put("data",dto);
            return jsonObject;
        }else {
            throw new RuntimeException("获取失败");
        }
    }
}


