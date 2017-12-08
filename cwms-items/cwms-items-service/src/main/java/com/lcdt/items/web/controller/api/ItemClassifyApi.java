package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.model.ItemClassifyDao;
import com.lcdt.items.model.ItemsInfoDao;
import com.lcdt.items.service.ItemClassifyService;
import com.lcdt.items.web.dto.PageBaseDto;
import com.lcdt.userinfo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by lyqishan on 2017/11/24
 */
@Api(description = "分类管理api")
@RestController
@RequestMapping("/items/itemclassify")
public class ItemClassifyApi {
    Logger log = LoggerFactory.getLogger(ItemClassify.class);

    @Autowired
    private ItemClassifyService itemClassifyService;

    @ApiOperation("新增分类")
    @PostMapping("/add")
    public ItemClassify addItemClassify(HttpSession httpSession, @ApiParam(value = "父类id", required = true) @RequestParam Long pid,
                                        @ApiParam(value = "分类名字", required = true) @RequestParam String classifyName) {
        Long companyId= SecurityInfoGetter.getCompanyId();
        User user=SecurityInfoGetter.getUser();
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setPid(pid);
        itemClassify.setClassifyName(classifyName);
        itemClassify.setCompanyId(companyId);
        itemClassify.setCreateId(user.getUserId());
        itemClassify.setCreateName(user.getRealName());
        itemClassify.setIsDefault(false);
        if (itemClassifyService.addItemClassify(itemClassify) != null) {
            return itemClassify;
        } else {
            throw new RuntimeException("添加失败");
        }

    }

    @ApiOperation("删除分类")
    @PostMapping("/delete")
    public JSONObject delItemClassify(HttpSession httpSession, @ApiParam(value = "分类ID", required = true) @RequestParam Long classifyId) {
        Long companyId=SecurityInfoGetter.getCompanyId();
        int result = itemClassifyService.deleteItemsClassifyAndChildren(classifyId,companyId);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "删除成功");
            return jsonObject;
        } else {
            throw new RuntimeException("删除失败");
        }
    }

    @ApiOperation("修改分类")
    @PostMapping("/modify")
    public JSONObject modifyItemClassify(HttpSession httpSession, @ApiParam(value = "分类id", required = true) @RequestParam Long classifyId,
                                         @ApiParam(value = "分类名字", required = true) @RequestParam String classifyName) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setClassifyId(classifyId);
        itemClassify.setClassifyName(classifyName);
        itemClassify.setCompanyId(companyId);
        int result = itemClassifyService.modifyByClassifyIdAndCompanyId(itemClassify);
        if (result > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("message", "修改成功");
            return jsonObject;
        } else {
            throw new RuntimeException("修改失败");
        }
    }

    @ApiOperation("查询分类列表")
    @GetMapping("/list")
    public PageBaseDto<List<ItemClassifyDao>> queryClassify(HttpSession httpSession) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        PageInfo pageInfo=new PageInfo();
        pageInfo.setPageSize(0);
        pageInfo.setPageNum(1);
        PageInfo<List<ItemClassifyDao>> listPageInfo = itemClassifyService.queryItemClassifyAndChildren(companyId,pageInfo);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }

    @ApiOperation("根据最小分类id查询他的所有上级分类")
    @GetMapping("/parentlist")
    public PageBaseDto<List<ItemClassify>> queryParentItemClassifyByMinClassifyId(HttpSession httpSession,@ApiParam(value = "分类id", required = true) @RequestParam Long classifyId){
        Long companyId=SecurityInfoGetter.getCompanyId();
        PageInfo pageInfo=new PageInfo();
        pageInfo.setPages(0);
        pageInfo.setPageNum(1);
        PageInfo<List<ItemClassify>> listPageInfo=itemClassifyService.queryClassifyByMinChildren(classifyId,companyId,pageInfo);
        return new PageBaseDto(listPageInfo.getList(),listPageInfo.getTotal());
    }
}
