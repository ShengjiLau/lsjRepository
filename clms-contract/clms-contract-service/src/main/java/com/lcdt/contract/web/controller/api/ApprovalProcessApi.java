package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.ApprovalProcess;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.service.ApprovalProcessService;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ApprovalProcessDto;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-01
 */
@Api(description = "审批流程设置api")
@RestController
@RequestMapping("/approval/process")
public class ApprovalProcessApi {

    Logger logger = LoggerFactory.getLogger(ApprovalProcessApi.class);

    @Autowired
    private ApprovalProcessService approvalProcessService;


    @ApiOperation(value = "流程列表", notes = "审批流程列表数据")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('approval_process_list')")
    public PageBaseDto<List<ApprovalProcessDto>> contractList(ApprovalProcessDto approvalProcessDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取userId
        approvalProcessDto.setCompanyId(companyId);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(approvalProcessDto.getPageNum());    //设置页码
        pageInfo.setPageSize(approvalProcessDto.getPageSize());  //设置每页条数
        PageInfo<List<ApprovalProcessDto>> listPageInfo = approvalProcessService.approvalProcessList(approvalProcessDto, pageInfo);
        logger.debug("总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "新增审批流程", notes = "新增审批流程")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('owndriver_add')")
    public JSONObject addOwnDriver(@RequestBody ApprovalProcessDto approvalProcessDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        approvalProcessDto.setCompanyId(companyId);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        approvalProcessService.addApprovalProcess(approvalProcessDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "新增成功");

        return jsonObject;
    }

}
