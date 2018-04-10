package com.lcdt.contract.web.controller.api;

import com.github.pagehelper.PageInfo;
//import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.model.Contract;
import com.lcdt.contract.service.ContractService;
import com.lcdt.contract.web.dto.ContractDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @AUTHOR liuh
 * @DATE 2018-03-01
 */
@Api(description = "合同管理api")
@RestController
@RequestMapping("/contract")
public class ContractApi {

    Logger logger = LoggerFactory.getLogger(ContractApi.class);

    @Autowired
    private ContractService contractService;


    @ApiOperation(value = "合同列表", notes = "合同列表数据")
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('contract_list')")
    public PageBaseDto<List<Contract>> contractList(ContractDto contractDto) {
        Long companyId = SecurityInfoGetter.getCompanyId(); //  获取companyId
        contractDto.setCompanyId(companyId);

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPageNum(contractDto.getPageNum());    //设置页码
        pageInfo.setPageSize(contractDto.getPageSize());  //设置每页条数
        PageInfo<List<Contract>> listPageInfo = contractService.ontractList(contractDto, pageInfo);
        logger.debug("合同总条数：" + listPageInfo.getTotal());
        logger.debug("listPageInfo:" + listPageInfo.toString());
        PageBaseDto pageBaseDto = new PageBaseDto(listPageInfo.getList(), listPageInfo.getTotal());
        return pageBaseDto;
    }

}
