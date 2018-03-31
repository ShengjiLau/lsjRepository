package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.web.dto.PageBaseDto;
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

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(contractDto.getPageNum());    //设置页码
        pageInfo.setPageSize(contractDto.getPageSize());  //设置每页条数
        PageInfo<List<Contract>> listPageInfo = contractService.ontractList(contractDto, pageInfo);
//        logger.debug("合同总条数：" + listPageInfo.getTotal());
//        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("合同新建")
    @RequestMapping(value = "/addContract", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('add_purchase_contract')")
    public JSONObject addContract(@Validated @RequestBody ContractDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        dto.setCompanyId(companyId);
        dto.setCreateTime(new Date());
        dto.setContractStatus((short)2);
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('modify_purchase_contract')")
    public JSONObject modifyContract(@Validated ContractDto dto) {
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

    @ApiOperation("合同终止/生效")
    @RequestMapping(value = "/updateContractStatus", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('update_purchase_contract_status')")
    public JSONObject updateContractStatus(@ApiParam(value = "合同ID",required = true) @RequestParam Long contractId,
                                        @ApiParam(value = "状态 0-生效 3-失效",required = true) @RequestParam short contractStatus) {
        Contract dto = new Contract();
        dto.setContractId(contractId);
        dto.setContractStatus(contractStatus);
        if(contractStatus == 0){//0生效
            dto.setEffectiveTime(new Date());
        }else{//3失效
            dto.setTerminationTime(new Date());
        }
        int result = contractService.modContractStatus(dto);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "终止成功");
            return jsonObject;
        } else {
            throw new RuntimeException("终止失败");
        }
    }

    @ApiOperation("合同生成采购订单")
    @RequestMapping(value = "/createPurchaseOrder", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('create_purchase_order')")
    public JSONObject createPurchaseOrder(@ApiParam(value = "合同ID",required = true) @RequestParam Long contractId) {
        int result = contractService.createOrderByContract(contractId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "采购订单创建成功");
            return jsonObject;
        } else {
            throw new RuntimeException("采购订单创建失败");
        }
    }

    @ApiOperation("合同详情")
    @RequestMapping(value = "/contractDetail", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('purchase_contract_detail')")
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

