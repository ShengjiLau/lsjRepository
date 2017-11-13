package com.lcdt.userinfo.web.controller.api;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.model.Department;
import com.lcdt.userinfo.service.DepartmentService;
import com.lcdt.userinfo.web.dto.DepartmentDto;
import com.lcdt.userinfo.web.dto.DeparmentResultDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangbinq on 2017/11/9.
 */
@Api(value = "部门api",description = "部门相关api")
@RestController
@RequestMapping("/api/dept")
public class DepartmentApi {

    @Autowired
    private DepartmentService departmentService;



    /**
     * 部门新增
     *
     * @return
     */
    @ApiOperation("新增部门")
    @RequestMapping(value = "/deptAdd",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('dept_add')")
    public Department deptAdd(@Validated DepartmentDto dto) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Department department = new Department();
        department.setCompanyId(companyId);
        department.setDeptName(dto.getDeptName());
        department.setDeptPid(dto.getDeptPid());
        Map map = new HashMap();
        map.put("companyId",companyId);
        department.setDeptOrder(departmentService.getMaxIndex(map));
        try {
            departmentService.createDepartment(department);
        } catch (DeptmentExistException e) {
            throw new DeptmentExistException("部门存在");
        }
        return department;
    }


    /**
     * 部门排序
     *
     * @return
     */
    @ApiOperation("部门排序")
    @RequestMapping(value = "/deptOrder",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('dept_list')")
    public Department deptOrder(@ApiParam(value = "部门ID-变动ID",required = true) @RequestParam Long deptId,
                                @ApiParam(value = "部门ID-被变ID",required = true) @RequestParam Long deptId1) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Department department = departmentService.getDepartment(deptId);
        if (department == null) {
            throw new DeptmentExistException("部门不存在");
        }
        Department department1 = departmentService.getDepartment(deptId1);
        if (department1 == null) {
            throw new DeptmentExistException("部门不存在");
        }
        long index1 = department.getDeptOrder();
        long index2 = department1.getDeptOrder();
        department.setDeptOrder(index2);
        departmentService.updateDepartment(department);
        department1.setDeptOrder(index1);
        departmentService.updateDepartment(department1);
        return department;
    }


    /**
     * 部门修改
     *
     * @return
     */
    @ApiOperation("修改部门")
    @RequestMapping(value = "/deptEdit",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('dept_edit')")
    public Department deptEdit(@ApiParam(value = "部门ID",required = true) @RequestParam Long deptId,
                               @ApiParam(value = "类型ID",required = true) @RequestParam Long deptPid,
                               @ApiParam(value = "部门名称",required = true)  @RequestParam String deptName) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Department department = new Department();
        department.setDeptId(deptId);
        department.setDeptPid(deptPid);
        department.setCompanyId(companyId);
        department.setDeptName(deptName);
        try {
            departmentService.updateDepartment(department);
        } catch (DeptmentExistException e) {
            throw new DeptmentExistException(e.getMessage());
        }
        return department;
    }



    /**
     * 部门移除
     *
     * @return
     */
    @ApiOperation("部门移除")
    @RequestMapping(value = "/deptremove",method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('dept_remove')")
    public Department removeDept(@Validated DepartmentDto dto) {

        /***
         * 这块删除逻辑待定（所涉及到的部门模块）
         */
        return null;
    }

    /**
     * 部门列表
     *
     * @return
     */
    @ApiOperation("部门列表")
    @RequestMapping(value = "/deptList",method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SYS_ADMIN') or hasAuthority('dept_list')")
    public DeparmentResultDto deptList(@ApiParam(value = "页码",required = true) @RequestParam Integer pageNo,
                                       @ApiParam(value = "每页显示条数",required = true) @RequestParam Integer pageSize) {
        Long companyId = SecurityInfoGetter.getCompanyId();
        Map map = new HashMap();
        map.put("companyId",companyId);
        map.put("page_no",pageNo);
        map.put("page_size",pageSize);
        map.put("deptPid",0); //获取一级栏目
        PageInfo pageInfo = departmentService.deptmentList(map);
        List<Department> list = null;
        if (pageInfo.getTotal()>0) {
            list = pageInfo.getList();
            for (Department tobj :list) {
                List<Department> list1 = getChild(tobj);
                if (list!=null && list1.size()>0) {
                    tobj.setList(list1);
                    tobj.setIsSub((short)1); //存在
                } else {
                    tobj.setIsSub((short)0); //存在
                }

            }
        }
        DeparmentResultDto rDto = new DeparmentResultDto();
        rDto.setList(list);
        rDto.setTotal(pageInfo.getTotal());
        return rDto;
    }

    /**
     * 递归部门树型
     * @param obj
     * @return
     */
    private List<Department> getChild(Department obj) {
        List<Department> childList = new ArrayList<Department>();
        Map map = new HashMap();
        map.put("companyId",obj.getCompanyId());
        map.put("deptPid",obj.getDeptId());
        PageInfo pageInfo = departmentService.deptmentList(map);
        if (pageInfo.getTotal()>0) {
            List<Department> tlist = pageInfo.getList();
            for(Department tobj : tlist) {
                childList.add(tobj);
            }
        } else {
            return null; // 递归退出条件
        }
        for (Department department : childList) {
            List<Department> list1 = getChild(department);
            if (list1!=null && list1.size()>0) {
                department.setList(list1);
                department.setIsSub((short)1); //存在
            } else {
                department.setIsSub((short)0); //存在
            }
        }
        return childList;
    }



}

