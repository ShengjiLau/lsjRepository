package com.lcdt.manage.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.manage.dto.CategoryParamDto;
import com.lcdt.manage.dto.NoticeListDto;
import com.lcdt.manage.dto.NoticeListParamsDto;
import com.lcdt.manage.dto.PageBaseDto;
import com.lcdt.manage.entity.TNotice;
import com.lcdt.manage.entity.TNoticeCategory;
import com.lcdt.manage.service.TNoticeCategoryService;
import com.lcdt.manage.service.TNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-07-12
 */
@Api(description = "通知管理api")
@RestController
@RequestMapping("/admin/notice")
public class TNoticeController {
    @Autowired
    TNoticeCategoryService categoryService;
    @Autowired
    TNoticeService noticeService;

    Logger log = LoggerFactory.getLogger(TNoticeController.class);


    @ApiOperation("新闻分类列表")
    @PostMapping("/categoryList")
//    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('item_calc_unit_add')")
    public PageBaseDto findCategoryList(@Validated CategoryParamDto paramDto) {
        Page<TNoticeCategory> page = categoryService.findCategoryPage(paramDto);
        System.out.println("page.getTotal="+page.getTotal());
        PageBaseDto pageBaseDto = new PageBaseDto(page.getRecords(),page.getTotal());
        System.out.println("pageBaseDto.getTotal="+pageBaseDto.getTotal());
        return pageBaseDto;
    }

    @ApiOperation("新闻分类保存")
    @PostMapping("/categorySave")
    public JSONObject categorySave(@Validated TNoticeCategory category){
        JSONObject jsonObject = new JSONObject();
        boolean result = false;
        //校验是否存在相同的类别
        boolean ext =  categoryService.findExistTNoticeCategory(category);
        if(ext){
            jsonObject.put("code", 1);
            jsonObject.put("message", "类别重复，提交失败！");
            return jsonObject;
        }
        if(category.getCategoryId()!=null){
            result = categoryService.updateAllColumnById(category);
        }
        else{
            result = categoryService.insert(category);
        }
        if(result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "更新成功！");
        }
        else{
            jsonObject.put("code", 1);
            jsonObject.put("message", "更新失败！");
        }
        return jsonObject;
    }
    @ApiOperation("新闻分类下拉列表")
    @PostMapping("/categoryAllList")
    public JSONObject findCategoryAllList() {
        List<TNoticeCategory> list = categoryService.findCategoryAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("message", "更新成功！");
        jsonObject.put("data", list);
        return jsonObject;
    }

    /**
     * 删除分类
     * @return
     */
    @ApiOperation("删除新闻分类")
    @PostMapping("/categoryDelete")
    public JSONObject categoryDelete(@RequestParam Long categoryId) {
        List l = noticeService.findAllNoticesByCateId(categoryId);

        String msg = "";
        int code = 0;
        if(l!=null && l.size()>0){
            code = 1;
            msg = "该分类已在使用，不能删除！";
        }
        else{
            categoryService.deleteById(categoryId);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("message", msg);

        return jsonObject;
    }

    @ApiOperation("新闻列表")
    @PostMapping("/noticeList")
    public PageBaseDto findNoticeList(@Validated NoticeListParamsDto params){
        Page<NoticeListDto> page = noticeService.findTopNoticesByPage(params);
        System.out.println("page.getTotal="+page.getTotal());
        PageBaseDto pageBaseDto = new PageBaseDto(page.getRecords(),page.getTotal());


        return pageBaseDto;
    }

    @ApiOperation("新闻保存")
    @PostMapping("/noticeSave")
    public JSONObject noticeSave(@Validated TNotice notice){
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        boolean result = false;
        notice.setCreateTime(new Date());
        notice.setCreateUserId(userId);
        notice.setCreateUserName(userName);


        if(notice.getNoticeId()==null)
            result = noticeService.insert(notice);
        else
            result = noticeService.updateAllColumnById(notice);


        JSONObject jsonObject = new JSONObject();
        if(result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "更新成功！");
        }
        else{
            jsonObject.put("code", 1);
            jsonObject.put("message", "更新失败！");
        }
        return jsonObject;
    }

    @ApiOperation("新闻详情 ")
    @PostMapping("/noticeRead")
    public JSONObject noticeRead(@RequestParam Long noticeId){
        TNotice notice = noticeService.selectById(noticeId);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("notice",notice);
        jsonObject.put("code", 0);
        jsonObject.put("message", "读取成功！");
        return jsonObject;
    }

    @ApiOperation("新闻状态切换 ")
    @PostMapping("/noticeSwitch")
    public JSONObject noticeSwitch(@Validated TNotice notice){
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        boolean result =false;
        String msg = null;
        JSONObject jsonObject = new JSONObject();
        TNotice entity = noticeService.selectById(notice.getNoticeId());
        if(notice.getNoticeStatus()!=9) {
            entity.setNoticeStatus(notice.getNoticeStatus());
            result = noticeService.updateAllColumnById(entity);
            if(result) {
                jsonObject.put("code", 0);
                jsonObject.put("message", "更新成功！");
            }
            else{
                jsonObject.put("code", 1);
                jsonObject.put("message", "更新失败！");
            }
        }
        else{
            result = noticeService.deleteById(notice.getNoticeId());
            if(result) {
                jsonObject.put("code", 0);
                jsonObject.put("message", "删除成功！");
            }
            else{
                jsonObject.put("code", 1);
                jsonObject.put("message", "删除失败！");
            }
        }
        return jsonObject;
    }


}

