package com.lcdt.userinfo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.lcdt.aliyunmq.AliyunConfigProperties;
import com.lcdt.clms.permission.model.SysRole;
import com.lcdt.clms.permission.service.SysRoleService;
import com.lcdt.clms.permission.service.UserRoleService;
import com.lcdt.customer.model.Customer;
import com.lcdt.customer.rpcservice.CustomerRpcService;
import com.lcdt.pay.rpc.ProductCountService;
import com.lcdt.userinfo.dao.UserCompRelMapper;
import com.lcdt.userinfo.dto.CompanyDto;
import com.lcdt.userinfo.event.CreateCompanyEvent;
import com.lcdt.userinfo.exception.CompanyExistException;
import com.lcdt.userinfo.exception.DeptmentExistException;
import com.lcdt.userinfo.exception.UserNotExistException;
import com.lcdt.userinfo.localservice.GroupWareHouseService;
import com.lcdt.userinfo.model.Company;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.service.*;
import com.lcdt.warehouse.entity.Warehouse;
import com.lcdt.warehouse.entity.WarehouseLinkman;
import com.lcdt.warehouse.entity.WarehouseLoc;
import com.lcdt.warehouse.rpc.WarehouseRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ss on 2017/10/24.
 */
@com.alibaba.dubbo.config.annotation.Service
public class CreateCompanyServiceImpl implements CreateCompanyService,ApplicationEventPublisherAware {

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
	UserRoleService userRoleService;

	@Autowired
	UserCompRelMapper userCompRelMapper;

	@Reference
	WarehouseRpcService warehouseRpcService;

	@Autowired
	FeePropertyService feePropertyService;

	@Reference
	CustomerRpcService customerRpcService;

	@Reference
	ProductCountService productCountService;

	@Autowired
	GroupWareHouseService groupWareHouseService;


	private SysRole sysAdminRole;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Company createCompany(CompanyDto companyDto) throws CompanyExistException, DeptmentExistException, UserNotExistException {
		Company company = companyService.saveCompanyMetaData(companyDto);

		assert company.getCreateId() != null;
		//默认添加创建者为管理员权限
		addAdminRole(company);
		//初始化其他业务角色（采购经理、销售经理、运输经理、仓储经理）
		addInitRole(company);
		//创建者加入默认新建组
		Group group = addDefaultGroup(company);
		List<UserCompRel> userCompRels = userCompRelMapper.selectByUserIdCompanyId(company.getCreateId(), company.getCompId());
		UserCompRel userCompRel = userCompRels.get(0);
		User user = userCompRel.getUser();
		//初始化费用类型（运费）
		feePropertyService.initFeeProperty(user,company.getCompId());
		//发送公司初始化事件
		sendCompanyInitEvent(userCompRels.get(0),group);
		//初始化客户信息
		addCustomer(company,userCompRel,group);
		//初始化短信条数和基站条数
		addInitPayCount(userCompRel);
		publisher.publishEvent(new CreateCompanyEvent(userCompRel));
		return company;
	}

	private void addInitPayCount(UserCompRel userRel) {
		//充值短信50条
		productCountService.logAddProductCountAndCompanyCount("sms_service","系统赠送试用",50,userRel.getUser().getPhone(),userRel.getCompId(),50);
		//充值基站20条
		productCountService.logAddProductCountAndCompanyCount("gms_location","系统赠送试用",20,userRel.getUser().getPhone(),userRel.getCompId(),20);
		//仓单服务30条
		productCountService.logAddProductCountAndCompanyCount("waybill_service","系统赠送试用",30,userRel.getUser().getPhone(),userRel.getCompId(),30);
		//运单服务30条
		productCountService.logAddProductCountAndCompanyCount("storage_service","系统赠送试用",30,userRel.getUser().getPhone(),userRel.getCompId(),30);
	}

	private void addCustomer(Company company, UserCompRel userCompRel,Group group) {
		Customer customer = new Customer();
		customer.setCompanyId(userCompRel.getCompId());
		customer.setCustomerName(company.getFullName());
		customer.setShortName(company.getShortName());
		customer.setClientTypes("1,5,2,3,7,6,4");
		customer.setGroupIds(group.getGroupId().toString());
		customer.setGroupNames(group.getGroupName());
		customer.setLinkMan(company.getLinkMan());
		customer.setLinkTel(company.getLinkTel());
		customer.setCreateId(userCompRel.getUser().getUserId());
		customer.setCreateName(userCompRel.getUser().getRealName());
		customer.setCreateDate(new Date());
		customerRpcService.customerAdd(customer);

	}

	/**
	 * 为公司添加其他初始化可编辑角色
	 * @param company
	 */
	private void addInitRole(Company company) {
		userRoleService.addInitRole(company.getCompId());
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

	public void sendCompanyInitEvent(UserCompRel compRel,Group group){
		if (compRel == null) {
			return;
		}
		initWarehouse(compRel,group);
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
	public Group addDefaultGroup(Company company) {
		Group group = createDefaultCompanyGroup(company);
		userGroupService.addUserToGroup(company.getCompId(), company.getCreateId(), group);
		return group;
	}


	private SysRole getSysAdminRole(){
		if (sysAdminRole != null) {
			return sysAdminRole;
		}else{
			SysRole sysRole = sysRoleService.selectBySysRoleCode(ADMIN_ROLE_CODE);
			return sysRole;
		}
	}
	public boolean initWarehouse(UserCompRel userCompRel, Group group){
		try{
			//添加仓库
			Warehouse warehouse = new Warehouse();
			warehouse.setWhName("默认仓库");
			warehouse.setWhType((short)0);
			warehouse.setPrincipal(userCompRel.getUser().getRealName());
			warehouse.setMobile(userCompRel.getUser().getPhone());
			warehouse.setWhStatus((short)0);
			warehouse.setCreateId(userCompRel.getUserId());
			warehouse.setCreateName(userCompRel.getUser().getRealName());
			warehouse.setCreateDate(new Date());
			warehouse.setIsDeleted((short)0);
			warehouse.setCompanyId(userCompRel.getCompId());
//			warehouse.setGroupIds(""+group.getGroupId());
			warehouse = warehouseRpcService.addWarehouse(warehouse);
			//添加仓库默认联系人
			WarehouseLinkman linkman = new WarehouseLinkman();
			linkman.setWhId(warehouse.getWhId());
			linkman.setName(warehouse.getPrincipal());
			linkman.setMobile(warehouse.getMobile());
			linkman.setCreateId(warehouse.getCreateId());
			linkman.setCreateName(warehouse.getCreateName());
			linkman.setCreateDate(new Date());
			linkman.setIsDefault((short)1);
			linkman.setIsDeleted((short)0);
			linkman.setCompanyId(warehouse.getCompanyId());
			linkman.setSex("男");
			linkman = warehouseRpcService.addWarehouseLinkMan(linkman);

			//添加仓库与业务组关系

			groupWareHouseService.addWareHouseRelation(group.getGroupId(),userCompRel.getUser().getUserId(),userCompRel.getCompId(),warehouse.getWhId());

			//初始化库位信息
			WarehouseLoc loc = new WarehouseLoc();
			loc.setWhId(warehouse.getWhId());
			loc.setCode("MOREN");
			loc.setName("默认库位");
			loc.setStatus((short) 0);
			loc.setCreateDate(new Date());
			loc.setCreateId(userCompRel.getUserId());
			loc.setCompanyId(userCompRel.getCompId());
			loc.setCreateName(userCompRel.getName());
			loc.setIsDeleted((short)0);
			loc = warehouseRpcService.addWarehouseLoc(loc);
			return true;
		}catch (Exception e){
			return false;
		}
	}

	private ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
