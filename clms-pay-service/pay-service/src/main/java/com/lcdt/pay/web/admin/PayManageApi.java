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
import com.lcdt.pay.model.PayOrder;
import com.lcdt.pay.rpc.CompanyServiceCountService;
import com.lcdt.pay.rpc.ProductCountLog;
import com.lcdt.pay.service.CompanyBalanceService;
import com.lcdt.pay.service.TopupService;
import com.lcdt.pay.service.impl.ProductCountServiceImpl;
import com.lcdt.pay.utils.CommonUtils;
import com.lcdt.pay.web.admin.dto.BalanceLogDto;
import com.lcdt.pay.web.admin.dto.PayOrderDto;
import com.lcdt.pay.web.admin.dto.ProductCountLogDto;
import com.lcdt.userinfo.dto.CompanyQueryDto;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
import com.lcdt.util.ResponseJsonUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private CompanyServiceCountService countService;

    @Autowired
    private ProductCountLogMapper logMapper;

    @Autowired
    private CompanyServiceCountMapper countMapper;

    @Autowired
    private BalanceLogMapper balanceLogMapper;

    @Autowired
    private TopupService topupService;

    @Reference
    private UserService userService;



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
    public JSONObject topUp(Long companyId,String serviceName,Integer num){
        final CompanyServiceCount companyServiceCount = countService.addCountNum(companyId, SecurityInfoGetter.getUser().getUserId(), serviceName, num, SecurityInfoGetter.getUser().getPhone());
        return ResponseJsonUtils.successResponseJson(companyServiceCount, "操作成功");
    }
    @PostMapping("/countlog")
    @ApiOperation("查询服务流水记录")
    public PageResultDto serviceCountLogs(Integer pageNo, Integer pageSize,@RequestParam(required = false) Long companyId, String serviceName, Date begin,Date end,Integer logtype){
        PageHelper.startPage(pageNo, pageSize);
        final List<ProductCountLog> productCountLogs = logMapper.selectByProductNameCompanyId(companyId, serviceName, begin, end, logtype);
        final ArrayList<ProductCountLogDto> productCountLogDtos = new ArrayList<>();
        for (ProductCountLog log : productCountLogs) {
            final ProductCountLogDto productCountLogDto = new ProductCountLogDto();
            BeanUtils.copyProperties(log, productCountLogDto);
            productCountLogDto.setCompany(companyService.selectById(log.getCompanyId()));
            productCountLogDtos.add(productCountLogDto);
        }
        return new PageResultDto(productCountLogDtos);
    }

    @RequestMapping(value = "/balancelog",method = RequestMethod.POST)
    @ApiOperation("金额余额流水记录")
    public PageResultDto<BalanceLogDto> balanceLog(Integer pageSize, Integer pageNo,
                                                @RequestParam(required = false) Long companyId,
                                                @RequestParam(required = false) Date beginTime,
                                                @RequestParam(required = false) Date endTime
            , @RequestParam(required = false) Integer payType, @RequestParam(required = false)Integer orderType
        ,@RequestParam(required = false) String operationUserName
    )
    {
        PageHelper.startPage(pageNo, pageSize);
        List<BalanceLog> balanceLogs = balanceLogMapper.selectByCompanyId(companyId, beginTime, endTime, orderType,payType,operationUserName);
        final ArrayList<BalanceLogDto> balanceLogDtos = new ArrayList<>();
        for (BalanceLog balanceLog : balanceLogs) {
            final BalanceLogDto balanceLogDto = new BalanceLogDto();
            final Company company = companyService.selectById(balanceLog.getCompanyId());
            balanceLogDto.setCompany(company);
            BeanUtils.copyProperties(balanceLog,balanceLogDto);
            balanceLogDtos.add(balanceLogDto);
        }
        return new PageResultDto<BalanceLogDto>(balanceLogDtos);
    }

    @ApiOperation("查看所有订单")
    @RequestMapping(value = "/orders",method = RequestMethod.GET)
    public PageResultDto<PayOrderDto> allorderlist(Integer pageNo, Integer pageSize,
                                                @RequestParam(required = false) Long companyId,
                                                @RequestParam(required = false) Date beginTime,
                                                @RequestParam(required = false) Date endTime,
                                                @RequestParam(required = false)Integer orderType
            , @RequestParam(required = false) Integer payType
    ){
        PageHelper.startPage(pageNo, pageSize);
        List<PayOrder> payOrders = topupService.topUpOrderList(companyId, orderType,beginTime,endTime,payType);

        final ArrayList<PayOrderDto> payOrderDtos = new ArrayList<>();

        for (PayOrder payOrder : payOrders) {
            final PayOrderDto payOrderDto = new PayOrderDto();
            BeanUtils.copyProperties(payOrder, payOrderDto);
            final Company company = companyService.selectById(payOrder.getOrderPayCompanyId());
            payOrderDto.setCompany(company);
            payOrderDtos.add(payOrderDto);
        }

        return new PageResultDto<PayOrderDto>(payOrderDtos);
    }


    @PostMapping("/topup")
    @ApiOperation("公司余额充值")
    public String balanceTopup(Long companyId, Integer num) {
        //充值
        companyBalanceService.adminRecharge(companyId,num,SecurityInfoGetter.getUser());
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
