package com.lcdt.driver.wechat.api.DriverManageApi;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.lcdt.clms.security.token.config.JwtTokenUtil;
import com.lcdt.driver.dto.PageBaseDto;
import com.lcdt.userinfo.dto.RegisterDto;
import com.lcdt.userinfo.exception.PassErrorException;
import com.lcdt.userinfo.exception.PhoneHasRegisterException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.CompanyService;
import com.lcdt.userinfo.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/auth/api/manage/")
public class LoginApi {

    @Reference
    UserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Reference
    CompanyService companyService;

    @RequestMapping("/register")
    public User registerUser(RegisterDto registerDto) {
        try {
            User user = userService.registerUser(registerDto);
            return user;
        } catch (PhoneHasRegisterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/login")
    public User login(String userName, String pwd) throws UserNotExistException, PassErrorException {
        User user = userService.userLogin(userName, pwd);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userId", user.getUserId());
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.set(Calendar.HOUR_OF_DAY, instance.get(Calendar.HOUR_OF_DAY + 1));
        String s = jwtTokenUtil.generateToken(stringStringHashMap,instance.getTime());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("user", user);
        jsonObject.put("message", "请求成功");
        return user;
    }

    @RequestMapping("/complist")
    public PageBaseDto<UserCompRel> compList(String token) throws Exception {
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new Exception("token 错误");
        }
        String userId = (String) claimsFromToken.get("userId");
        List<UserCompRel> userCompRels = companyService.companyList(Long.valueOf(userId));
        return new PageBaseDto<UserCompRel>(userCompRels);
    }


    @RequestMapping("/choosecomp")
    public String chooseCompany(String token,Long compId) throws Exception {
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new Exception("token 错误");
        }
        String userId = (String) claimsFromToken.get("userId");
        UserCompRel userCompRel = companyService.queryByUserIdCompanyId(Long.valueOf(userId), compId);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("userId", userId);
        stringStringHashMap.put("userCompId", userCompRel.getUserCompRelId());
        String s = jwtTokenUtil.generateToken(stringStringHashMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", s);
        jsonObject.put("result", 0);
        jsonObject.put("data", userCompRel);
        jsonObject.put("message", "请求成功");
        return jsonObject.toString();
    }


}
