package com.lcdt.contract.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.model.OrderApproval;
import com.lcdt.contract.service.OrderApprovalService;
import com.lcdt.contract.web.dto.OrderApprovalDto;
import com.lcdt.contract.web.dto.OrderApprovalListDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @AUTHOR liuh
 * @DATE 2018-03-23
 */
@Api(description = "销售单审批api")
@RestController
@RequestMapping("/sales/order_approval")
public class SalesOrderApprovalApi {

    Logger logger = LoggerFactory.getLogger(SalesOrderApprovalApi.class);

    @Autowired
    private OrderApprovalService orderApprovalService;


    @ApiOperation(value = "销售单审批列表", notes = "销售单审批列表")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_list')")
    public PageBaseDto<List<Contract>> approvalList(OrderApprovalListDto orderApprovalListDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId();    //获取user_id
        orderApprovalListDto.setCompanyId(companyId);
        orderApprovalListDto.setUserId(userId);
        orderApprovalListDto.setOrderType(new Short("1"));    //  1 - 销售单

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(orderApprovalListDto.getPageNum());    //设置页码
        pageInfo.setPageSize(orderApprovalListDto.getPageSize());  //设置每页条数
        PageInfo<List<OrderApprovalDto>> listPageInfo = orderApprovalService.orderApprovalList(orderApprovalListDto, pageInfo);
        logger.debug("总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

    @ApiOperation(value = "待审批数量",notes = "返回我待审批的总数量")
    @GetMapping(value = "/pending_num")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_list')")
    public JSONObject pendingNum() {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        Long userId = SecurityInfoGetter.getUser().getUserId();    //获取user_id
        int total = orderApprovalService.pendingApprovalNum(userId,companyId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "查询成功!");
        jsonObject.put("pendingNum", total);

        return jsonObject;
    }

    @ApiOperation(value = "审批同意",notes = "正常通过审批操作")
    @PostMapping(value = "/agree")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_operate')")
    public JSONObject agreeApproval(@RequestBody OrderApproval orderApproval) {
        int result = orderApprovalService.agreeApproval(orderApproval);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");

        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "驳回审批", notes = "驳回操作")
    @PostMapping(value = "/reject")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_operate')")
    public JSONObject rejectApproval(@RequestBody OrderApproval orderApproval) {
        int result = orderApprovalService.rejectApproval(orderApproval);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "撤销审批", notes = "撤销操作")
    @PostMapping(value = "/revoke")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_revoke')")
    public JSONObject revokeApproval(@RequestBody OrderApproval orderApproval) {
        int result = orderApprovalService.revokeApproval(orderApproval);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "转办审批", notes = "转办操作")
    @PostMapping(value = "/turnDo")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_operate')")
    public JSONObject turnDoApproval(@RequestBody List<OrderApproval> orderApprovalList) {
        int result = orderApprovalService.turnDoApproval(orderApprovalList);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }

    @ApiOperation(value = "抄送", notes = "抄送操作")
    @PostMapping(value = "/cc")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('sales_order_approval_cc')")
    public JSONObject ccApproval(@RequestBody List<OrderApproval> orderApprovalList) {
        int result = orderApprovalService.ccApproval(orderApprovalList);
        JSONObject jsonObject = new JSONObject();
        if (result > 0) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "操作成功");
        } else {
            jsonObject.put("code", -1);
            jsonObject.put("message", "操作失败");
        }
        return jsonObject;
    }


}
