package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.token.config.JwtTokenUtil;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.notify.rpcservice.IValidCodeService;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.CreateCompanyService;
import com.lcdt.userinfo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.lcdt.driver.wechat.api.AuthApi.VCODETAG;

@RestController
@RequestMapping("/auth/api/manage/")
public class LoginApi {

    @Reference
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Reference
    CompanyService companyService;

    @Reference
    IValidCodeService validCodeService;

    @Reference
    CreateCompanyService createCompanyService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public User registerUser(RegisterDto registerDto) {
        try {
            String ecode = registerDto.getEcode();
            boolean codeCorrect = validCodeService.isCodeCorrect(ecode, VCODETAG, registerDto.getUserPhoneNum());
            if (!codeCorrect) {
                throw new RuntimeException("验证码错误");
            }
            User user = userService.registerUser(registerDto);
            return user;
        } catch (PhoneHasRegisterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String userName, String pwd) throws UserNotExistException, PassErrorException {
        User user = userService.userLogin(userName, pwd);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userId", user.getUserId());
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, instance.get(Calendar.DAY_OF_YEAR + 30));
        String s = jwtTokenUtil.generateToken(stringStringHashMap,instance.getTime());
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(user.getUserId()));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("user", user);
        jsonObject.put("comps", userCompRels);
        jsonObject.put("message", "请求成功");
        return jsonObject.toString();
    }

    @RequestMapping(value = "/complist",method = RequestMethod.POST)
    public PageBaseDto<UserCompRel> compList(String token) throws Exception {
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new Exception("token 错误");
        }
        String userId = (String) claimsFromToken.get("userId");
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(userId));
        return new PageBaseDto<UserCompRel>(userCompRels);
    }


    @RequestMapping(value = "/choosecomp",method = RequestMethod.POST)
    public String chooseCompany(String token,Long compId) throws Exception {
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new Exception("token 错误");
        }
        String userId = String.valueOf(claimsFromToken.get("userId"));
        UserCompRel userCompRel = companyService.queryByUserIdCompanyId(Long.valueOf(userId), compId);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userName", userCompRel.getUser().getPhone());
        stringStringHashMap.put("userCompId", userCompRel.getUserCompRelId());
        String s = jwtTokenUtil.generateToken(stringStringHashMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("data", userCompRel);
        jsonObject.put("message", "请求成功");
        return jsonObject.toString();
    }

    @RequestMapping(value = "/createcomp", method = RequestMethod.POST)
    public Company createCompany(CompanyDto companyDto) throws CompanyExistException, UserNotExistException {
        Company company = createCompanyService.createCompany(companyDto);
        return company;
    }


}
