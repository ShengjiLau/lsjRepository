package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSON;
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
    public PageBaseDto<List<ApprovalProcessDto>> approvalList(ApprovalProcessDto approvalProcessDto) {
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
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('approval_process_add')")
    public JSONObject addApprovalProcess(@RequestBody ApprovalProcessDto approvalProcessDto, BindingResult bindingResult) {
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

    @ApiOperation(value = "修改审批流程", notes = "新增审批流程")
    @PostMapping("/modify")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('approval_process_modify')")
    public JSONObject modArrpovalProcess(@RequestBody ApprovalProcessDto approvalProcessDto, BindingResult bindingResult) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        approvalProcessDto.setCompanyId(companyId);
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", -1);
            jsonObject.put("message", bindingResult.getFieldError().getDefaultMessage());
            return jsonObject;
        }
        approvalProcessService.modApprovalProcess(approvalProcessDto);
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");

        return jsonObject;
    }

    @ApiOperation(value = "删除审批流程", notes = "删除审批流程及相关审批人信息")
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('approval_process_delete')")
    public JSONObject delArrpovalProcess(Long processId) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        JSONObject jsonObject = new JSONObject();

        approvalProcessService.delApprovalProcess(processId,companyId);
        jsonObject.put("code", 0);
        jsonObject.put("message", "修改成功");

        return jsonObject;
    }

    @ApiOperation(value = "审批详情", notes = "审批流程详情包含审批人信息")
    @GetMapping("/detail")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('approval_process_list')")
    public JSONObject approvalDetail(ApprovalProcess approvalProcess) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
//        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取userId
        approvalProcess.setCompanyId(companyId);
        List<ApprovalProcessDto> listPageInfo = approvalProcessService.approvalProcessDetail(approvalProcess);
        JSONObject object = new JSONObject();
        object.put("detail",listPageInfo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",object);
        jsonObject.put("code", 0);
        jsonObject.put("message", "获取成功");
        return jsonObject;
    }

    @ApiOperation(value = "流程展示", notes = "根据type获取对应企业下的所有类型")
    @GetMapping("/show")
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('approval_process_list')")
    public JSONObject approvalShow(ApprovalProcess approvalProcess) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        approvalProcess.setCompanyId(companyId);
        List<ApprovalProcessDto> listPageInfo = approvalProcessService.approvalProcessShow(approvalProcess);
        JSONObject object = new JSONObject();
        object.put("list",listPageInfo);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",object);
        jsonObject.put("code", 0);
        jsonObject.put("message", "获取成功");
        return jsonObject;
    }
}
