package com.lcdt.pay.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.dao.ProductCountLogMapper;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/balanceList")
    @ApiOperation("根据公司名和管理员账号查询公司余额")
    public PageResultDto allList(String companyName,String adminUserName){
        return new PageResultDto(companyBalanceService.companyBalance(selectCompanyIds(companyName, adminUserName)));
    }

    @PostMapping("/serviceList")
    @ApiOperation("根据公司名和管理员账号查询服务剩余次数")
    public PageResultDto serviceCountList(String companyName,String adminUserName){
        final List<Long> longs = selectCompanyIds(companyName, adminUserName);
        return new PageResultDto(companyServiceCountMapper.selectByCompanyIds(CommonUtils.joinStringWithToken(longs, ',')));
    }

    @PostMapping("/addservicenum")
    @ApiOperation("充值服务次数，serviceName服务代码")
    public CompanyServiceCount topUp(Long companyId,String serviceName,Integer num){
        return countService.addCountNum(companyId, serviceName, num, SecurityInfoGetter.getUser().getPhone());
    }
    @PostMapping("/countlog")
    @ApiOperation("查询服务消费日志")
    public PageResultDto serviceCountLogs(Integer pageNo, Integer pageSize,Long companyId, String serviceName, Date begin,Date end){
        PageHelper.startPage(pageNo, pageSize);
        final List<ProductCountLog> productCountLogs = logMapper.selectByProductNameCompanyId(companyId, serviceName, begin, end, ProductCountServiceImpl.CountLogType.ADMIN_TOPUP);
        return new PageResultDto(productCountLogs);
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

}
