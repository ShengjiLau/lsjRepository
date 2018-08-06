package com.lcdt.manage.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.manage.dto.FileParamsDto;
import com.lcdt.manage.dto.FileListDto;
import com.lcdt.manage.dto.PageBaseDto;
import com.lcdt.manage.entity.TNoticeCategory;
import com.lcdt.manage.entity.TNoticeFile;
import com.lcdt.manage.service.TNoticeFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public JSONObject uploadFiles(@Validated List<TNoticeFile> files){
        Long userId = SecurityInfoGetter.getUser().getUserId(); //获取用户id
        String userName = SecurityInfoGetter.getUser().getRealName();   //获取用户姓名
        System.out.println("filePaths:"+files);
        boolean result =false;

        if(files!=null){
            Date createTime = new Date();
            for (TNoticeFile f:files) {
                System.out.println(f);
                //1-图片，2-视频，3-音频，4-文档
                String[] type1 ={"jpg","jpeg","png","bmp","gif"};
                String[] type4 ={"doc","docx","xls","xlsx","rar","zip","pdf"};
                for(String type : type1){

                    if(f.getFileName().endsWith("."+type)) {
                        f.setFileType(1);
                        break;
                    }
                }
                for(String type : type4){

                    if(f.getFileName().endsWith("."+type)) {
                        f.setFileType(4);
                        break;
                    }
                }
                f.setCreateTime(createTime);
                f.setCreateId(userId);
                f.setCreateName(userName);
                //fileService.insertAllColumn(f);
            }
            result = fileService.insertBatch(files);
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
        return null;
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

