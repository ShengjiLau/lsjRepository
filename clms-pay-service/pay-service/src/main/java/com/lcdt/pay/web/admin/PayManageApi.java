package com.lcdt.pay.web.admin;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lcdt.pay.dao.CompanyServiceCountMapper;
import com.lcdt.pay.model.CompanyServiceCount;
import com.lcdt.pay.model.PageResultDto;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.utils.CommonUtils;
import com.lcdt.userinfo.dto.CompanyQueryDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/balanceList")
    public PageResultDto allList(String companyName,String adminUserName){
        return new PageResultDto(companyBalanceService.companyBalance(selectCompanyIds(companyName, adminUserName)));
    }

    @PostMapping("/serviceList")
    public PageResultDto serviceCountList(String companyName,String adminUserName){
        final List<Long> longs = selectCompanyIds(companyName, adminUserName);
        return new PageResultDto(companyServiceCountMapper.selectByCompanyIds(CommonUtils.joinStringWithToken(longs, ',')));
    }

    @PostMapping("/addservicenum")
    public String topUp(Long companyId,String serviceName,Integer num){
        final List<CompanyServiceCount> companyServiceCounts = companyServiceCountMapper.selectByCompanyId(companyId, serviceName);
        return null;
    }

    @PostMapping("/topup")
    public String balanceTopup(Long companyId, Integer num) {
        return null;
    }



    public List<Long> selectCompanyIds(String companyName, String adminUserName) {
        final CompanyQueryDto companyQueryDto = new CompanyQueryDto();
        companyQueryDto.setAdminphone(adminUserName);
        companyQueryDto.setFullName(companyName);
        final List<Company> byComanyQueryDto = companyService.findByComanyQueryDto(companyQueryDto);
        return byComanyQueryDto.stream().map(company -> company.getCompId()).collect(Collectors.toList());
    }

}
