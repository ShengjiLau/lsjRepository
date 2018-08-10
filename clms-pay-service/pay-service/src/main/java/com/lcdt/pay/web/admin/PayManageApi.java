package com.lcdt.pay.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.dao.BalanceLogMapper;
import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.dao.ProductCountLogMapper;
import com.lcdt.pay.model.BalanceLog;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.PageResultDto;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.impl.ProductCountServiceImpl;
import com.lcdt.pay.utils.CommonUtils;
import com.lcdt.userinfo.dto.CompanyQueryDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.service.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/pay")
public class PayManageApi {

    @Autowired
    public CompanyBalanceService companyBalanceService;

    @Reference
    private CompanyService companyService;

    @Autowired
    private CompanyServiceCountMapper companyServiceCountMapper;

    private CompanyServiceCountService countService;

    @Autowired
    private ProductCountLogMapper logMapper;

    @Autowired
    private CompanyServiceCountMapper countMapper;

    @Autowired
    private BalanceLogMapper balanceLogMapper;



    @PostMapping("/balances")
    @ApiOperation("根据公司id list查询现金余额")
    public PageResultDto queryBalanceList(@RequestBody List<Long> compIds){
        return new PageResultDto(companyBalanceService.companyBalance(compIds));
    }

    @PostMapping("/counts")
    @ApiOperation("根据公司id list查询服务余额")
    public PageResultDto queryCompanyCount(@RequestBody List<Long> compIds){
        if (CollectionUtils.isEmpty(compIds)) {
            return new PageResultDto(new ArrayList());
        }
        return new PageResultDto(countMapper.selectByCompanyIds(compIds));
    }

    @PostMapping("/balanceList")
    @ApiOperation("根据公司名和管理员账号查询公司余额")
    public PageResultDto allList(Integer pageSize, Integer pageNo, @RequestParam(required = false) String companyName, @RequestParam(required = false) String adminUserName){
        PageHelper.startPage(pageNo, pageSize);
        if (StringUtils.isEmpty(companyName) && StringUtils.isEmpty(adminUserName)) {
            return new PageResultDto(companyBalanceService.allBalance());
        }
        return new PageResultDto(companyBalanceService.companyBalance(selectCompanyIds(companyName, adminUserName)));
    }

    @PostMapping("/serviceList")
    @ApiOperation("根据公司名和管理员账号查询服务剩余次数")
    public PageResultDto serviceCountList(String companyName,String adminUserName){
        final List<Long> longs = selectCompanyIds(companyName, adminUserName);
        return new PageResultDto(companyServiceCountMapper.selectByCompanyIds(longs));
    }

    @PostMapping("/addservicenum")
    @ApiOperation("充值服务次数，serviceName服务代码")
    public CompanyServiceCount topUp(Long companyId,String serviceName,Integer num){
        return countService.addCountNum(companyId, serviceName, num, SecurityInfoGetter.getUser().getPhone());
    }
    @PostMapping("/countlog")
    @ApiOperation("查询服务流水记录")
    public PageResultDto serviceCountLogs(Integer pageNo, Integer pageSize,@RequestParam(required = false) Long companyId, String serviceName, Date begin,Date end,Integer logtype){
        PageHelper.startPage(pageNo, pageSize);
        final List<ProductCountLog> productCountLogs = logMapper.selectByProductNameCompanyId(companyId, serviceName, begin, end, logtype);
        return new PageResultDto(productCountLogs);
    }

    @RequestMapping(value = "/balancelog",method = RequestMethod.POST)
    @ApiOperation("金额余额流水记录")
    public PageResultDto<BalanceLog> balanceLog(Integer pageSize, Integer pageNo,
                                                @RequestParam(required = false) Long companyId,
                                                @RequestParam(required = false) Date beginTime,
                                                @RequestParam(required = false) Date endTime
            , @RequestParam(required = false) Integer payType, @RequestParam(required = false)Integer orderType
        ,@RequestParam(required = false) String operationUserName
    )
    {
        PageHelper.startPage(pageNo, pageSize);
        List<BalanceLog> balanceLogs = balanceLogMapper.selectByCompanyId(companyId, beginTime, endTime, orderType,payType,operationUserName);
        return new PageResultDto<BalanceLog>(balanceLogs);
    }


    @PostMapping("/topup")
    @ApiOperation("公司余额充值")
    public String balanceTopup(Long companyId, Integer num) {
        //充值
        companyBalanceService.adminRecharge(companyId,num,String.valueOf(SecurityInfoGetter.getUser().getUserId()));
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "操作成功");
        jsonObject.put("code", 0);
        return jsonObject.toString();
    }



    public List<Long> selectCompanyIds(String companyName, String adminUserName) {
        final CompanyQueryDto companyQueryDto = new CompanyQueryDto();
        companyQueryDto.setAdminphone(adminUserName);
        companyQueryDto.setFullName(companyName);
        final List<Company> byComanyQueryDto = companyService.findByComanyQueryDto(companyQueryDto);
        return byComanyQueryDto.stream().map(company -> company.getCompId()).collect(Collectors.toList());
    }


    public void initBinder(){

    }


}
