package com.lcdt.wms.permission.service;

import com.lcdt.wms.permission.model.WmsUserRole;

import java.util.List;

/**
 * Created by ss on 2017/8/8.
 */
public interface UserRoleService {

	List<WmsUserRole> getUserRole(Integer userId, Long companyId);


}
