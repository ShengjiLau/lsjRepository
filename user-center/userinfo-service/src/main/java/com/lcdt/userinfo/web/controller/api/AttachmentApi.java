package com.lcdt.userinfo.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.userinfo.model.TAttachmentClassify;
import com.lcdt.userinfo.service.AttachmentService;
import com.lcdt.userinfo.web.dto.PageBaseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/3.
 */
@RestController
@RequestMapping("/api/attachment")
@Api(value = "附件设置api", description = "附件操作")
public class AttachmentApi {

    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation("文件列表")
    @RequestMapping(value = "/attachFileList", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('attachFile_list')")
    public PageBaseDto attachFileList(){
        PageBaseDto pageBaseDto = new PageBaseDto();

        PageInfo pageInfo = attachmentService.attachFileList();

        pageBaseDto.setList(pageInfo.getList());
        pageBaseDto.setTotal(pageInfo.getTotal());

        return pageBaseDto;

    }

    @ApiOperation("附件分类列表")
    @RequestMapping(value = "/attachmentClassifyList", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('attachmentClassify_list')")
    public PageBaseDto attachmentClassifyList(@Validated TAttachmentClassify attachmentClassify,
                                              @ApiParam(value = "页码",required = true, defaultValue = "1") @RequestParam Integer pageNo,
                                              @ApiParam(value = "每页显示条数",required = true, defaultValue = "10") @RequestParam Integer pageSize){
        PageBaseDto pageBaseDto = new PageBaseDto();

        Map<String,Object> map = new HashedMap();
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("tAttachmentFileType", attachmentClassify.gettAttachmentFileType());
        PageInfo pageInfo = attachmentService.attachmentClassifyList(map);

        pageBaseDto.setList(pageInfo.getList());
        pageBaseDto.setTotal(pageInfo.getTotal());

        return pageBaseDto;

    }

    @ApiOperation("附件分类添加")
    @RequestMapping(value = "/addAttachmentClassify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('attachmentClassify_add')")
    public JSONObject addTAttachmentClassify(@RequestBody TAttachmentClassify attachmentClassify){
        JSONObject jo = new JSONObject();

        int code = -1;
        String message = "添加失败！";

        if(attachmentService.isRepeat(attachmentClassify)){
            message = "附件分类名称重复！";
        }else{
            int result = attachmentService.addTAttachmentClassify(attachmentClassify);

            if(result > 0){
                code = 0;
                message = "添加成功！";
            }
        }


        jo.put("code", code);
        jo.put("message",message);

        return jo;

    }

    @ApiOperation("附件分类修改")
    @RequestMapping(value = "/modifyAttachmentClassify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('attachmentClassify_modify')")
    public JSONObject modifyTAttachmentClassify(@RequestBody TAttachmentClassify attachmentClassify){
        JSONObject jo = new JSONObject();
        int code = -1;
        String message = "修改失败！";

        if(attachmentService.isRepeat(attachmentClassify)){
            message = "附件分类名称重复！";
        }else{
            int result = attachmentService.modifyTAttachmentClassify(attachmentClassify);


            if(result > 0){
                code = 0;
                message = "修改成功！";
            }
        }


        jo.put("code", code);
        jo.put("message",message);

        return jo;

    }

    @ApiOperation("附件分类删除")
    @RequestMapping(value = "/deleteAttachmentClassify", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('attachmentClassify_delete')")
    public JSONObject deleteTAttachmentClassify(@RequestParam Integer attachmentClassifyId){
        JSONObject jo = new JSONObject();

        int result = attachmentService.modifyTAttachmentClassifyDelete(attachmentClassifyId);

        int code = -1;
        String message = "删除失败！";

        if(result > 0){
            code = 0;
            message = "删除成功！";
        }
        jo.put("code", code);
        jo.put("message",message);

        return jo;

    }
}
