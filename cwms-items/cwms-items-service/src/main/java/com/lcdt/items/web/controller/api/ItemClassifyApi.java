package com.lcdt.items.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.lcdt.converter.ArrayListResponseWrapper;
import com.lcdt.items.model.ItemClassify;
import com.lcdt.items.service.ItemClassifyService;
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
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setPid(pid);
        itemClassify.setClassifyName(classifyName);
        itemClassify.setCompanyId(8L);
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
        int result = itemClassifyService.deleteItemsClassifyAndChildren(classifyId);
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
                                         @ApiParam(value = "分类名字", required = true) @RequestParam String classifyName,
                                         @ApiParam(value = "父类id", required = true) @RequestParam Long pid,
                                         @ApiParam(value = "是否是默认分类", required = true) @RequestParam boolean isDefault) {
        Long companyId = 8L;
        ItemClassify itemClassify = new ItemClassify();
        itemClassify.setClassifyId(classifyId);
        itemClassify.setClassifyName(classifyName);
        itemClassify.setPid(pid);
        itemClassify.setIsDefault(isDefault);
        itemClassify.setCompanyId(companyId);
        int result = itemClassifyService.modifyByPrimaryKey(itemClassify);
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
    public List<ItemClassify> modifyClassify(HttpSession httpSession) {
        Long companyId = 8L;
        List<ItemClassify> itemClassifyList = new ArrayListResponseWrapper<ItemClassify>(itemClassifyService.queryItemClassifyAndChildren(companyId));
        return itemClassifyList;
    }


}
