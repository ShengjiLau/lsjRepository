package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.token.config.JwtTokenUtil;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.driver.dto.WechatCreateCompanyDto;
import com.lcdt.notify.rpcservice.IValidCodeService;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tl.commons.util.DateUtility;

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
    public JSONObject registerUser(RegisterDto registerDto) throws PhoneHasRegisterException {
            boolean codeCorrect = validCodeService.isCodeCorrect(registerDto.getEcode(), VCODETAG, registerDto.getUserPhoneNum());
            if (!codeCorrect) {
                throw new RuntimeException("验证码错误");
            }
            registerDto.setRegisterFrom("管车宝小程序");
            User user = userService.registerUser(registerDto);
            HashMap<String, Object> stringStringHashMap = new HashMap<>();
            stringStringHashMap.put("userId", user.getUserId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", user);
            jsonObject.put("code", 0);
            jsonObject.put("token", jwtTokenUtil.generateToken(stringStringHashMap));
            return jsonObject;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public JSONObject login(String userName, String pwd) throws UserNotExistException, PassErrorException {
        User user = userService.userLogin(userName, pwd);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userId", user.getUserId());
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(DateUtility.getDateAfterDays(date,36500));
        String s = jwtTokenUtil.generateToken(stringStringHashMap,instance.getTime());
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(user.getUserId()));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("user", user);
        jsonObject.put("comps", userCompRels);
        jsonObject.put("message", "请求成功");
        return jsonObject;
    }

    @RequestMapping(value = "/complist",method = RequestMethod.POST)
    public PageBaseDto<UserCompRel> compList(String token) throws Exception {
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new Exception("token 错误");
        }
        String userId = String.valueOf(claimsFromToken.get("userId"));
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(userId));
        return new PageBaseDto<UserCompRel>(userCompRels);
    }


    @RequestMapping(value = "/choosecomp",method = RequestMethod.POST)
    public JSONObject chooseCompany(String token,Long compId) throws Exception {
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new Exception("token 错误");
        }
        String userId = String.valueOf(claimsFromToken.get("userId"));
        UserCompRel userCompRel = companyService.queryByUserIdCompanyId(Long.valueOf(userId), compId);
        if (userCompRel.getCompany().getEnable() != null && userCompRel.getCompany().getEnable().equals(false)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", "");
            jsonObject.put("result", -1);
            jsonObject.put("data", userCompRel);
            jsonObject.put("message", "企业已被禁用");
            return jsonObject;
        }
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userName", userCompRel.getUser().getPhone());
        stringStringHashMap.put("userCompId", userCompRel.getUserCompRelId());
        stringStringHashMap.put("userId", userCompRel.getUserId());
        String s = jwtTokenUtil.generateToken(stringStringHashMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("data", userCompRel);
        jsonObject.put("message", "请求成功");
        return jsonObject;
    }

    @RequestMapping(value = "/createcomp", method = RequestMethod.POST)
    public Company createCompany(WechatCreateCompanyDto companyDto) throws CompanyExistException, UserNotExistException {
        String token = companyDto.getToken();
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken != null) {
            String userId = String.valueOf(claimsFromToken.get("userId"));
            if (StringUtils.isEmpty(userId)) {
                throw new RuntimeException("token错误");
            }
            companyDto.setCreateDt(new Date());
            companyDto.setCreateId(Long.valueOf(userId));
            companyDto.setUserId(Long.valueOf(userId));
        }else{
            throw new RuntimeException("token错误");
        }
        Company company = createCompanyService.createCompany(companyDto);
        return company;
    }
}
