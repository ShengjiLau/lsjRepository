package com.lcdt.manage.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.manage.dto.FileParamsDto;
import com.lcdt.manage.dto.FileListDto;
import com.lcdt.manage.dto.FileUploadDto;
import com.lcdt.manage.dto.PageBaseDto;
import com.lcdt.manage.entity.TNoticeCategory;
import com.lcdt.manage.entity.TNoticeFile;
import com.lcdt.manage.service.TNoticeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author code generate
 * @since 2018-08-01
 */
@Api(description = "素材管理api")
@RestController
@RequestMapping("/admin/noticefile")
public class TNoticeFileController {
    @Autowired
    TNoticeFileService fileService;

    @ApiOperation("素材列表")
    @PostMapping("/findFileList")
    public PageBaseDto findFileList(@Validated FileParamsDto paramDto) {
        Page<FileListDto> page = fileService.findListByPage(paramDto);
        System.out.println("page.getTotal="+page.getTotal());
        PageBaseDto pageBaseDto = new PageBaseDto(page.getRecords(),page.getTotal());
        return  pageBaseDto;
    }

    @ApiOperation("文件上传")
    @PostMapping("/uploadFiles")
    public JSONObject uploadFiles(@Validated FileUploadDto o, BindingResult bindingResult){
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        boolean result =false;

        if(o.getFiles()!=null){
            Date createTime = new Date();
            List<TNoticeFile> fileList = new ArrayList<TNoticeFile>();
            for (Map<String,Object> f : o.getFiles()) {
                //1-图片，2-视频，3-音频，4-文档
                String[] type1 ={"jpg","jpeg","png","bmp","gif"};
                String[] type4 ={"doc","docx","xls","xlsx","rar","zip","pdf"};
    //
                TNoticeFile file = new TNoticeFile();
                file.setFileName(f.get("name").toString());
                file.setOssPath(f.get("url").toString());
                file.setCategoryId(o.getCategoryId());
                file.setCreateTime(createTime);
                file.setCreateId(userId);
                file.setCreateName(userName);
                //图片格式
                for(String type : type1){
                    if(f.get("name").toString().endsWith("."+type)) {
                        file.setFileType(1);
                        break;
                    }
                }
                //文档格式
                for(String type : type4){
                    if(f.get("name").toString().endsWith("."+type)) {
                       file.setFileType(4);
                       break;
                    }
                }
                fileList.add(file);
            }
            result = fileService.insertBatch(fileList);
        }
        JSONObject jsonObject = new JSONObject();
        if(result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "保存成功！");
        }
        else {
            jsonObject.put("code", 1);
            jsonObject.put("message", "保存失败！");
        }
        return jsonObject;
    }
    @ApiOperation("文件信息修改")
    @PostMapping("/updateFile")
    public JSONObject updateFile(@Validated TNoticeFile file){
        boolean result =false;
        if(file!=null){
            TNoticeFile old = fileService.selectById(file.getFileId());
            if(old!=null) {
                old.setRemark(file.getRemark());
                old.setFileType(file.getFileType());
                result = fileService.updateAllColumnById(old);
            }
        }
        JSONObject jsonObject = new JSONObject();
        if(result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "保存成功！");
        }
        else {
            jsonObject.put("code", 1);
            jsonObject.put("message", "保存失败！");
        }
        return jsonObject;
    }
    @ApiOperation("文件信息修改")
    @PostMapping("/deleteFile")
    public JSONObject deleteFile(@RequestParam Long fileId){
        boolean result =false;
        if(fileId!=null){
            result = fileService.deleteById(fileId);
        }
        JSONObject jsonObject = new JSONObject();
        if(result) {
            jsonObject.put("code", 0);
            jsonObject.put("message", "保存成功！");
        }
        else {
            jsonObject.put("code", 1);
            jsonObject.put("message", "保存失败！");
        }
        return jsonObject;
    }
}

