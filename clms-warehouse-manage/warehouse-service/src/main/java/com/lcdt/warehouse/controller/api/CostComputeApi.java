package com.lcdt.warehouse.controller.api;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.warehouse.entity.CostComputeSet;
import com.lcdt.warehouse.mapper.CostComputeSetMapper;
import com.lcdt.warehouse.service.CostComputeSetService;
import com.lcdt.warehouse.utils.JSONResponseUtil;
import com.lcdt.warehouse.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/costset")
public class CostComputeApi {

    @Autowired
    CostComputeSetService setService;
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('compute_set')")
    @PostMapping("/update")
    public ResponseMessage<CostComputeSet> updateComputeSet(String set){
        return JSONResponseUtil.success(setService.updateSet(SecurityInfoGetter.getCompanyId(), set));
    }

    @GetMapping("/get")
    public ResponseMessage<CostComputeSet> computeSet(){
        return JSONResponseUtil.success(setService.selectById(SecurityInfoGetter.getCompanyId()));
    }
}
