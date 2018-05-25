package com.lcdt.warehouse.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.warehouse.dto.*;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.service.InWarehousePlanService;
import com.lcdt.warehouse.service.OutWarehousePlanService;
import com.lcdt.warehouse.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */

public class OutplanGoodsController {

}

