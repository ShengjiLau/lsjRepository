package com.lcdt.userinfo.web.controller.api.admin;

import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.utils.JSONResponseUtil;
import com.lcdt.userinfo.utils.ResponseMessage;
import com.lcdt.userinfo.web.controller.api.admin.dto.CompanyQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/company")
public class CompanyManageApi {

    @Autowired
    CompanyService companyService;



    @PostMapping("/usercomps")
    public ResponseMessage userCompRels(String userId){
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(userId));
        return JSONResponseUtil.success(userCompRels);
    }

    @PostMapping("/comps")
    public ResponseMessage list(CompanyQueryDto companyQueryDto){
        return JSONResponseUtil.success(null);
    }


}
