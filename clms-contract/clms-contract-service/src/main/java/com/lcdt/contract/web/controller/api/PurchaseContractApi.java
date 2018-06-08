package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.ContractLog;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.lcdt.clms.security.helper.SecurityInfoGetter;
//import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-01
 */
@Api(description = "采购合同管理api")
@RestController
@RequestMapping("/purchaseContract")
public class PurchaseContractApi {

    Logger logger = LoggerFactory.getLogger(PurchaseContractApi.class);

    @Autowired
    private ContractService contractService;


    @ApiOperation(value = "合同列表", notes = "合同列表数据")
    @GetMapping("/contractList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_list')")
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
        PageInfo<List<Contract>> listPageInfo = contractService.contractList(contractDto, pageInfo);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("合同新建")
    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_add')")
    public JSONObject addContract(@Validated @RequestBody ContractDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        User user = SecurityInfoGetter.getUser();
        dto.setCompanyId(companyId);
        dto.setCreateId(user.getUserId());
        dto.setCreateName(user.getRealName());
        dto.setCreateTime(new Date());
//        dto.setPartyAId(user.getUserId());
//        dto.setPartyAName(user.getRealName());
        //设置合同状态
        if(dto.getIsDraft() == 0){//存为草稿
            dto.setContractStatus((short)2);
        }else{
            dto = Utils.getContractStatus(dto);
        }

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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_modify')")
    public JSONObject modifyContract(@Validated @RequestBody ContractDto dto) {
        User user = SecurityInfoGetter.getUser();
//        dto.setPartyAId(user.getUserId());
//        dto.setPartyAName(user.getRealName());
        //设置合同状态
        if(dto.getIsDraft() == 0){//存为草稿
            dto.setContractStatus((short)2);
        }else{
            dto = Utils.getContractStatus(dto);
        }
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

    @ApiOperation("合同终止/生效/草稿发布")
    @RequestMapping(value = "/updateContractStatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_status_update')")
    public JSONObject updateContractStatus(@ApiParam(value = "合同ID",required = true) @RequestParam Long contractId,
                                        @ApiParam(value = "状态 0-生效 3-失效 2-草稿发布",required = true) @RequestParam short contractStatus) {
        Contract dto = new Contract();
        dto.setContractId(contractId);
        dto.setContractStatus(contractStatus);
        if(contractStatus == 0){//0生效
            dto.setStartDate(new Date());
        }else if(contractStatus == 3){//3失效
            dto.setEndDate(new Date());
        }else{
            ContractDto oldDto = contractService.selectByPrimaryKey(contractId);
            oldDto.setIsDraft((short)1);
            dto = Utils.getContractStatus(oldDto);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_list')")
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

    @ApiOperation("合同日志")
    @GetMapping("/contractLogList")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_log_list')")
    public PageBaseDto<List<ContractLog>> contractLogList(@ApiParam(value = "合同id",required = true) @RequestParam Long contractId,
                                                          @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                                          @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize) {
        Map map = new HashMap();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("contractId", contractId);
        PageInfo<List<ContractLog>> listPageInfo = contractService.contractLogList(map);
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    //增加合同列表直接上传附件功能
    @ApiOperation("上传附件")
    @RequestMapping(value = "/uploadAttachment", method = RequestMethod.POST)
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_attachment_modify')")
    public JSONObject uploadAttachment(@RequestBody ContractDto dto) {
        User user = SecurityInfoGetter.getUser();
        //设置合同状态
        boolean result = contractService.uploadAttachment(dto);
        if (result) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "上传成功");
            return jsonObject;
        } else {
            throw new RuntimeException("上传失败");
        }
    }

    @ApiOperation("取消合同")
    @RequestMapping(value = "/cancelContract", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_cancel')")
    public JSONObject cancelContract(@ApiParam(value = "合同ID",required = true) @RequestParam Long contractId) {
        Contract dto = new Contract();
        dto.setContractId(contractId);
        dto.setContractStatus((short)4);//取消
        int result = contractService.modContractStatus(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "取消成功");
            return jsonObject;
        } else {
            throw new RuntimeException("取消失败");
        }
    }
}

