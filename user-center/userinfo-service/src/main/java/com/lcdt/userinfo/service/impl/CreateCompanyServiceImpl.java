package com.lcdt.userinfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.model.*;
import com.lcdt.userinfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ss on 2017/10/24.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CreateCompanyServiceImpl implements CreateCompanyService {

	public final String defaultGroupName = "默认项目";
	public final String defaultGroupRemark = "公司默认项目";

	public static final String ADMIN_ROLE_CODE = "role_sys_admin";

	@Autowired
	CompanyService companyService;

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	GroupManageService groupManageService;

	@Autowired
	UserGroupService userGroupService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	UserService userService;

	@Autowired
	UserCompRelMapper userCompRelMapper;

	@Autowired
	WarehouseService warehouseService;

	@Autowired
	FeePropertyService feePropertyService;

	private SysRole sysAdminRole;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company createCompany(CompanyDto companyDto) throws CompanyExistException, DeptmentExistException, UserNotExistException {
		Company company = companyService.saveCompanyMetaData(companyDto);
		assert company.getCreateId() != null;
		//默认添加创建者为管理员权限
		addAdminRole(company);
		//创建者加入默认新建组
		addDefaultGroup(company);
		List<UserCompRel> userCompRels = userCompRelMapper.selectByUserIdCompanyId(company.getCreateId(), company.getCompId());
		UserCompRel userCompRel = userCompRels.get(0);
		User user = userCompRel.getUser();

		feePropertyService.initFeeProperty(user,company.getCompId());

		//发送公司初始化事件
		sendCompanyInitEvent(userCompRels.get(0));

		return company;
	}

	/**
	 * 为公司创建者添加管理员权限
	 * @param company
	 */
	private void addAdminRole(Company company) {
		sysRoleService.addUserSysRole(getSysAdminRole(), company.getCreateId(), company.getCompId());
	}

	@Autowired
	Producer producer;

	@Autowired
	AliyunConfigProperties aliyunConfigProperties;

	public void sendCompanyInitEvent(UserCompRel compRel){
		if (compRel == null) {
			return;
		}
		warehouseService.initWarehouse(compRel.getUser(), compRel.getCompId());
		Message message = new Message();
		message.setKey("companyinit");
		message.setTopic(aliyunConfigProperties.getTopic());
		message.setBody(JSONObject.toJSONBytes(compRel, SerializerFeature.BrowserCompatible));
		producer.send(message);
		return;
	}


	@Transactional(rollbackFor = Exception.class)
	public Group createDefaultCompanyGroup(Company company) {
		Group group = new Group();
		group.setGroupName(defaultGroupName);
		group.setCompanyId(company.getCompId());
		group.setCreateDate(new Date());
		group.setGroupRemark(defaultGroupRemark);
		group.setIsValid(true);
		groupManageService.createGroup(group);
		return group;
	}


	@Transactional(rollbackFor = Exception.class)
	public void addDefaultGroup(Company company) {
		Group group = createDefaultCompanyGroup(company);
		userGroupService.addUserToGroup(company.getCompId(), company.getCreateId(), group);
	}


	private SysRole getSysAdminRole(){
		if (sysAdminRole != null) {
			return sysAdminRole;
		}else{
			SysRole sysRole = sysRoleService.selectBySysRoleCode(ADMIN_ROLE_CODE);
			return sysRole;
		}
	}

}
