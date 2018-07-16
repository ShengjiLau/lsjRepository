package com.lcdt.clms.permission.service;

import com.github.pagehelper.PageInfo;
import com.lcdt.clms.permission.dto.PermissionParamsDto;
import com.lcdt.clms.permission.model.Permission;

/**
 * Created by lyqishan on 2018/7/16
 */

public interface PermissionService {
    /**
     * 新增
     * @param permission
     * @return
     */
    int addPermission(Permission permission);

    /**
     * 删除
     * @param permissionId
     * @return
     */
    int deletePermission(Integer permissionId);

    /**
     * 修改
     * @param permission
     * @return
     */
    int modifyPermission(Permission permission);

    /**
     * 查询
     * @param permissionId
     * @return
     */
    Permission queryPermission(Integer permissionId);

    /**
     * 查询列表
     * @param params
     * @return
     */
    PageInfo<Permission> queryPermissionList(PermissionParamsDto params);

}
