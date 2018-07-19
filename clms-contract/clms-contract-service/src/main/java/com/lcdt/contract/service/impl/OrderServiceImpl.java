package com.lcdt.contract.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.OrderApprovalMapper;
import com.lcdt.contract.model.OrderApproval;
import com.lcdt.contract.notify.ContractAttachment;
import com.lcdt.contract.notify.ContractNotifyBuilder;
import com.lcdt.contract.notify.ContractNotifyProducer;
import com.lcdt.notify.model.ContractNotifyEvent;
import com.lcdt.notify.model.DefaultNotifyReceiver;
import com.lcdt.notify.model.DefaultNotifySender;
import com.lcdt.userinfo.model.Group;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;
import com.lcdt.userinfo.rpc.CompanyRpcService;
import com.lcdt.userinfo.rpc.GroupWareHouseRpcService;
import com.lcdt.warehouse.dto.InWhPlanDto;
import com.lcdt.warehouse.dto.InWhPlanGoodsDto;
import com.lcdt.warehouse.dto.OutWhPlanDto;
import com.lcdt.warehouse.dto.OutWhPlanGoodsDto;
import com.lcdt.warehouse.entity.InWarehousePlan;
import com.lcdt.warehouse.entity.OutWarehousePlan;
import com.lcdt.warehouse.rpc.WarehouseRpcService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ConditionQueryMapper;
import com.lcdt.contract.dao.OrderMapper;
import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.dao.PaymentApplicationMapper;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.model.PaymentApplication;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.vo.OrderVO;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.contract.web.dto.PageBaseDto;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.service.TrafficRpc;
import com.lcdt.traffic.vo.ConstantVO;
import org.springframework.util.StringUtils;


/**
 * @author Sheng-ji Lau
 * @date 2018年3月2日下午2:31:06
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private OrderApprovalMapper orderApprovalMapper;

    @Autowired
    private ConditionQueryMapper nonautomaticMapper;

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderProductMapper orderProductMapper;
    
    @Autowired
    private PaymentApplicationMapper paymentApplicationMapper;

    @Reference
    private TrafficRpc trafficRpc;
    
    @Reference
    private GroupWareHouseRpcService groupWareHouseRpcService;
    
    @Reference
    private WarehouseRpcService warehouseRpcService;
    @Reference
    private CompanyRpcService companyRpcService;
    @Autowired
    private ContractNotifyProducer producer;
    
    @Override
    public int addOrder(OrderDto orderDto) {
    	Long UserId = SecurityInfoGetter.getUser().getUserId();
		Long companyId = SecurityInfoGetter.getCompanyId();
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
		orderDto.setCreateTime(new Date());
		
        BigDecimal aTotal = OrderVO.ZERO_VALUE;// aTotal为所有商品总价格
        if (null != orderDto.getOrderProductList() && orderDto.getOrderProductList().size() != 0) {
            for (OrderProduct orderProduct : orderDto.getOrderProductList()) {
                BigDecimal num = orderProduct.getNum();
                BigDecimal price = orderProduct.getPrice();
                //计算单个商品总价
                BigDecimal total = num.multiply(price);
                aTotal = aTotal.add(total);
                orderProduct.setTotal(total);
            }
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setSummation(aTotal);
        int result = orderMapper.insertOrder(order);
        //新增订单商品的总数量
        int productCount = 0;
        if (null != orderDto.getOrderProductList() && orderDto.getOrderProductList().size() != 0) {
            for (OrderProduct orderProduct : orderDto.getOrderProductList()) {
                //为每个商品添加OrderId
                orderProduct.setOrderId(order.getOrderId());
            }
            productCount += nonautomaticMapper.insertOrderProductByBatch(orderDto.getOrderProductList());
            logger.debug("新增订单商品数量:" + productCount);
        }

        //审批流程添加
        if (null != orderDto.getOrderApprovalList() && orderDto.getOrderApprovalList().size() > 0) {
            /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/
            for (OrderApproval oa : orderDto.getOrderApprovalList()) {
                //设置关联订单id
                oa.setOrderId(order.getOrderId());
                if (oa.getActionType().shortValue() == 0) {
                    if (oa.getSort() == 1) {
                        //同时设置第一个审批的人的状态为审批中
                        oa.setStatus(new Short("1"));
                        /**↓发送消息通知开始*/
                        //发送
                        DefaultNotifySender defaultNotifySender = ContractNotifyBuilder.notifySender(orderDto.getCompanyId(), orderDto.getCreateUserId());
                        User user = companyRpcService.selectByPrimaryKey(oa.getUserId());
                        Order queryOrder = orderMapper.selectByPrimaryKey(order.getOrderId());
                        //接收
                        DefaultNotifyReceiver defaultNotifyReceiver = ContractNotifyBuilder.notifyCarrierReceiver(orderDto.getCompanyId(), user.getUserId(), user.getPhone());
                        ContractAttachment attachment = new ContractAttachment();
                        attachment.setEmployee(SecurityInfoGetter.getUser().getRealName());
                        attachment.setPurOrderSerialNum(queryOrder.getOrderSerialNo());
                        attachment.setCarrierWebNotifyUrl(ContractNotifyBuilder.ORDER_WEB_NOTIFY_URL+queryOrder.getOrderSerialNo());
                        String eventName = "purchase_approval_publish";
                        //如果是销售单
                        if (orderDto.getOrderType().shortValue() == 1) {
                            eventName = "sale_approval_publish";
                            attachment.setSaleOrderSerialNum(queryOrder.getOrderSerialNo());
                        }
                        ContractNotifyEvent plan_publish_event = new ContractNotifyEvent(eventName, attachment, defaultNotifyReceiver, defaultNotifySender);
                        producer.sendNotifyEvent(plan_publish_event);
                        /**↑发送消息通知结束*/
                    } else {
                        //设置其他审批状态为 0 - 初始值
                        oa.setStatus(new Short("0"));
                    }
                } else {
                    //设置其他审批状态为 0 - 初始值
                    oa.setStatus(new Short("0"));
                }
            }
            OrderApproval orderApproval = new OrderApproval();
            User user = SecurityInfoGetter.getUser();
            UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
            orderApproval.setOrderId(order.getOrderId());
            orderApproval.setUserName(user.getRealName());
            orderApproval.setUserId(user.getUserId());
            orderApproval.setDeptName(userCompRel.getDeptNames());
            // 0 为创建着
            orderApproval.setSort(0);
            //默认actionType 0
            orderApproval.setActionType(new Short("0"));
            //创建人默认
            orderApproval.setStatus(new Short("2"));
            orderApproval.setTime(new Date());
            orderDto.getOrderApprovalList().add(orderApproval);
            orderApprovalMapper.insertBatch(orderDto.getOrderApprovalList());
            //同时设置合同的审批状态为审批中
            order.setApprovalStatus(new Short("1"));
            order.setApprovalStartDate(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            // todo 没有添加审批人，则认为合同无需审批
            //同时设置合同的审批状态为审批中
            order.setApprovalStatus(new Short("0"));
            orderMapper.updateByPrimaryKeySelective(order);
        }

        if (productCount  > 0) {
            return result;
        } else {
            throw new RuntimeException("订单商品添加失败");
        }
    }


    @Override
    public int modOrder(OrderDto orderDto) {
        BigDecimal aTotal = OrderVO.ZERO_VALUE;
        if (null != orderDto.getOrderProductList() && orderDto.getOrderProductList().size() != 0) {
            for (OrderProduct orderProduct : orderDto.getOrderProductList()) {
                BigDecimal num = orderProduct.getNum();
                BigDecimal price = orderProduct.getPrice();
                BigDecimal total = num.multiply(price);
                aTotal = aTotal.add(total);
                orderProduct.setTotal(total);
                orderProduct.setOrderId(orderDto.getOrderId());
            }
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setSummation(aTotal);
        int result = orderMapper.updateByPrimaryKeySelective(order);
        int i = 0;
        int j = 0;
        if (null != orderDto.getOrderProductList() && orderDto.getOrderProductList().size() != 0) {
            //删除订单下所有商品
            nonautomaticMapper.deleteOrderProductByOrderId(order.getOrderId());
            //插入新的订单商品
            i += nonautomaticMapper.insertOrderProductByBatch(orderDto.getOrderProductList());
            logger.debug("修改订单商品数量为:" + i);
        }

        //审批流程添加 如果添加了审批人，则先清楚数据库中原来保存的审批人，然后新增
        if (null != orderDto.getOrderApprovalList() && orderDto.getOrderApprovalList().size() > 0) {
            //删除之前数据库保存的审批人信息
            orderApprovalMapper.deleteByOrderId(orderDto.getOrderId());
            /*1.加入创建人信息 2.设置关联的合同id 3.批量插入审批人信息*/

            for (OrderApproval oa : orderDto.getOrderApprovalList()) {
                oa.setOrderId(order.getOrderId()); //设置关联订单id
                if (oa.getActionType().shortValue() == 0) {
                    if (oa.getSort() == 1) {
                        oa.setStatus(new Short("1"));   //同时设置第一个审批的人的状态为审批中
                    } else {
                        oa.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                    }
                } else {
                    oa.setStatus(new Short("0"));   //设置其他审批状态为 0 - 初始值
                }
            }
            OrderApproval orderApproval = new OrderApproval();
//            Long companyId = SecurityInfoGetter.getCompanyId();
            User user = SecurityInfoGetter.getUser();
            UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
            orderApproval.setOrderId(order.getOrderId());
            orderApproval.setUserName(user.getRealName());
            orderApproval.setUserId(user.getUserId());
            orderApproval.setDeptName(userCompRel.getDeptNames());
            orderApproval.setSort(0);    // 0 为创建着
            orderApproval.setActionType(new Short("0"));    //默认actionType 0
            orderApproval.setStatus(new Short("2"));    //创建人默认
            orderApproval.setTime(new Date());
            orderDto.getOrderApprovalList().add(orderApproval);
            j += orderApprovalMapper.insertBatch(orderDto.getOrderApprovalList());
            //同时设置合同的审批状态为审批中
            order.setApprovalStatus(new Short("1"));
            order.setApprovalStartDate(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            /*如果审批流程被清除，则视为改合同不需要审批。需要：1.删除之前关联的审批人信息 2.更新合同状态为无需审批 0 */
            orderApprovalMapper.deleteByOrderId(orderDto.getOrderId());
            //同时设置合同的审批状态为审批中
            order.setApprovalStatus(new Short("0"));
            //	order.setApprovalStartDate(null);
            j += orderMapper.updateByPrimaryKeySelective(order);
        }
        if (i > 0) {
            return result;
        } else {
            throw new RuntimeException("订单商品修改失败");
        }
    }

    @Override
    public int delOrder(Long orderId) {
        int result = orderMapper.deleteByPrimaryKey(orderId);
        //result+=orderProductService.delOrderProductByOrderId(orderId);
        return result;
    }

    @Override
    public  PageBaseDto<OrderDto> OrderList(OrderDto orderDto) {
    	Long UserId = SecurityInfoGetter.getUser().getUserId();//get 创建者
		Long companyId = SecurityInfoGetter.getCompanyId();//get 公司id	
		orderDto.setCompanyId(companyId);
		orderDto.setCreateUserId(UserId);
        if (orderDto.getPageNum() <= 0) {
            orderDto.setPageNum(1);
        }
        if (orderDto.getPageSize() <= 0) {
            orderDto.setPageSize(0);//设置为0是查询全部
        }
        PageHelper.startPage(orderDto.getPageNum(), orderDto.getPageSize());//分页
        List<OrderDto> orderDtoList = nonautomaticMapper.selectByCondition(orderDto);
        if (null != orderDtoList && orderDtoList.size() != 0) {
            for (OrderDto ord : orderDtoList) {
                //获取订单商品
                List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(ord.getOrderId());
                if (null != orderProductList && orderProductList.size() > 0) {
                    ord.setOrderProductList(orderProductList);
                }
            }
            //获取付款状态 付款单记录 开票记录信息
            List<Map> paymentList = nonautomaticMapper.paymentInfo(orderDtoList,orderDto.getCompanyId());
            List<Map> billingRecordList = nonautomaticMapper.billingInfo(orderDtoList,orderDto.getCompanyId());
            for (OrderDto od : orderDtoList) {
            	//通过RPC查询添加计划状态
            	if (null != od.getTrafficPlan() && !"".equals(od.getTrafficPlan())) {
            		WaybillPlan waybillPlan = trafficRpc.getWaybillPlanBySerialNo(od.getTrafficPlan());
            		if (null != waybillPlan) {
            			od.setTrafficPlanStatus(waybillPlan.getPlanStatus());
            		}
            	}
            	if (null != od.getWarehousePlan() && !"".equals(od.getWarehousePlan())) {
            		if (0 == od.getOrderType()) {
            			InWarehousePlan inWarehousePlan = warehouseRpcService.getInWarehousePlanBySerialNo(od.getWarehousePlan());
            			od.setWarehousePlanStatus(inWarehousePlan.getPlanStatus());
            		}
            		if (1 == od.getOrderType()) {
            			OutWarehousePlan outWarehousePlan = warehouseRpcService.getOutWarehousePlanBySerialNo(od.getWarehousePlan());
            			if (null != outWarehousePlan) {
            				od.setWarehousePlanStatus(outWarehousePlan.getPlanStatus());
            			}
            		}
            	}
            	
                //整合付款单信息
                if (paymentList.size() > 0) {
                    for (int i = 0; i < paymentList.size(); i++) {
                        Map map = paymentList.get(i);
                        if ((long) map.get("order_id") == od.getOrderId()) {
                            od.setPaymentStatus(new Short(map.get("payment_status").toString()));
                            od.setPaymentNum(map.get("payment_num").toString());
                            od.setPaymentSum(map.get("payment_sum").toString());
                            break;
                        } else if (i == paymentList.size() - 1) {
                            od.setPaymentStatus(new Short("0"));
                            od.setPaymentNum("0");
                            od.setPaymentSum("0");
                        }
                    }
                } else {
                    od.setPaymentStatus(new Short("0"));
                    od.setPaymentNum("0");
                }
                //整合开票记录信息
                if (billingRecordList.size() > 0) {
                    for (int j = 0; j < billingRecordList.size(); j++) {
                        Map map = billingRecordList.get(j);
                        if ((long) map.get("order_id") == od.getOrderId()) {
                            od.setBillingRecordNum(map.get("billing_record_num").toString());
                            break;
                        } else if (j == billingRecordList.size() - 1) {
                            od.setBillingRecordNum("0");
                        }
                    }
                } else {
                    od.setBillingRecordNum("0");
                }
            }

        }

        PageInfo<OrderDto> pageInfo = new PageInfo<OrderDto>(orderDtoList);
        PageBaseDto<OrderDto> pageBaseDto = new PageBaseDto<OrderDto>();
        pageBaseDto.setList(pageInfo.getList());
		pageBaseDto.setTotal(pageInfo.getTotal());
		logger.debug("销售订单条目数"+pageInfo.getTotal());
		
        return pageBaseDto;
    }


    @Override
    public OrderDto selectByPrimaryKey(Long orderId) {
        OrderDto orderDto = orderMapper.selectByPrimaryKey(orderId);
        if (null != orderDto) {
            logger.debug("订单id为" + orderDto.getOrderId());
            //获取订单下商品
            List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(orderDto.getOrderId());
            if (null != orderProductList && orderProductList.size() != 0) {
                orderDto.setOrderProductList(orderProductList);
            }
            //添加审批人及抄送人信息
            List<OrderApproval> orderApprovalList = orderApprovalMapper.selectForOrderDetail(orderDto.getOrderId());
            if (null != orderApprovalList && orderApprovalList.size() > 0) {
                orderDto.setOrderApprovalList(orderApprovalList);
            }
        }
        return orderDto;
    }

    /**
     * 取消订单时需要判断是否具有付款单，具有付款单的订单不能取消
     */
    @Override
    public int updateOrderIsDraft(Long orderId, Short isDraft) {
    	List<PaymentApplication> billingRecordList = paymentApplicationMapper.selectByOrderId(orderId);
    	if (null != billingRecordList && 0 != billingRecordList.size()) {
    		return 0;
    	}
        return orderMapper.updateIsDraft(orderId, isDraft);
    }

    /**
     * 订单生成运输计划
     */
	@Override
	public Boolean generateTrafficPlan(Long orderId) {
		int purchaseFlag = 1;
		int salesFlag = 2;
		int flag;
		
		Order order = orderMapper.selectByPrimaryKey(orderId);
		WaybillParamsDto WaybillParamsDto = new WaybillParamsDto();
		Long companyId = SecurityInfoGetter.getCompanyId();
	    User loginUser = SecurityInfoGetter.getUser();
	    UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
	    WaybillParamsDto.setDeptNames(userCompRel.getDeptNames());
	    WaybillParamsDto.setCreateId(loginUser.getUserId());
	    WaybillParamsDto.setCreateName(loginUser.getRealName());
	    WaybillParamsDto.setCompanyId(companyId);
	    WaybillParamsDto.setCompanyName(userCompRel.getCompany().getFullName()); //企业名称
	    WaybillParamsDto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入
	    WaybillParamsDto.setSalesOrder(order.getOrderNo());
	    WaybillParamsDto.setTransportWay((short) 1);//设置运输方式为陆运
	    WaybillParamsDto.setGroupId(order.getGroupId());
	    Group group = groupWareHouseRpcService.selectByGroupId(order.getGroupId());
	    WaybillParamsDto.setGroupName(group.getGroupName());
	    if (0 == order.getOrderType()) {
	    	WaybillParamsDto.setSendMan(order.getSender());
		    WaybillParamsDto.setSendPhone(order.getSenderPhone());
		    WaybillParamsDto.setSendProvince(order.getSendProvince());
		    WaybillParamsDto.setSendCity(order.getSendCity());
		    WaybillParamsDto.setSendCounty(order.getSendDistrict());
		    WaybillParamsDto.setSendAddress(order.getSendAddress());
		    WaybillParamsDto.setReceiveMan(order.getReceiver());
		    WaybillParamsDto.setReceivePhone(order.getReceiverPhone());
		    WaybillParamsDto.setReceiveProvince(order.getReceiverProvince());
		    WaybillParamsDto.setReceiveCity(order.getReceiverCity());
		    WaybillParamsDto.setReceiveCounty(order.getReceiveDistrict());
		    WaybillParamsDto.setReceiveAddress(order.getReceiveAddress());
		    if (null != order.getSendTime()) {
		    	WaybillParamsDto.setStartDate(order.getSendTime().toLocaleString());
		    }else {
		    	WaybillParamsDto.setStartDate(new Date().toLocaleString());
		    }
		    if (null != order.getReceiveTime()) {
		    	WaybillParamsDto.setArriveDate(order.getReceiveTime().toLocaleString());
		    }else {
		    	WaybillParamsDto.setArriveDate(null);
		    }
		    flag = purchaseFlag;
	    }else {
	    	WaybillParamsDto.setSendWhId(order.getWarehouseId());
	 	    WaybillParamsDto.setSendWhName(order.getReceiveWarehouse());
	    	WaybillParamsDto.setSendMan(order.getReceiver());
		    WaybillParamsDto.setSendPhone(order.getReceiverPhone());
		    WaybillParamsDto.setSendProvince(order.getReceiverProvince());
		    WaybillParamsDto.setSendCity(order.getReceiverCity());
		    WaybillParamsDto.setSendCounty(order.getReceiveDistrict());
		    WaybillParamsDto.setSendAddress(order.getReceiveAddress());
		    if (null != order.getReceiveTime()) {
		    	WaybillParamsDto.setStartDate(order.getReceiveTime().toLocaleString());
		    }else {
		    	WaybillParamsDto.setStartDate(new Date().toLocaleString());
		    }
		    if (null != order.getSendTime()) {
		    	 WaybillParamsDto.setArriveDate(order.getSendTime().toLocaleString());
		    }else {
		    	 WaybillParamsDto.setArriveDate(null);
		    }
		    flag = salesFlag;
	    }
	    
	    List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(order.getOrderId());
	    List<PlanDetail> planDetailList = new ArrayList<PlanDetail>(orderProductList.size());
	    for (OrderProduct orderProduct : orderProductList) {
	    	PlanDetail planDetail = new PlanDetail();
	    	planDetail.setGoodsName(orderProduct.getName());
	    	planDetail.setGoodsSpec(orderProduct.getSpec());
	    	planDetail.setUnit(orderProduct.getSku());
	    	planDetail.setPlanAmount(orderProduct.getNum().doubleValue());
//	    	planDetail.setPayPrice(orderProduct.getPrice().doubleValue());
//	    	planDetail.setPayTotal(orderProduct.getTotal().doubleValue());
	    	planDetail.setCompanyId(companyId);
	    	planDetail.setIsDeleted((OrderVO.IS_DELETED));
	    	planDetail.setCreateDate(new Date());
	    	planDetail.setCreateId(loginUser.getUserId());
	    	planDetail.setCreateName(loginUser.getRealName());
	    	planDetailList.add(planDetail);
	    }
	    WaybillParamsDto.setPlanDetailList(planDetailList);
	
	   
 	    WaybillPlan waybillPlan = trafficRpc.purchase4Plan(WaybillParamsDto, flag);
	    if (null != waybillPlan) {
	    	order.setTrafficPlan(waybillPlan.getSerialCode());
	    	orderMapper.updateByPrimaryKeySelective(order);
	    	return true;
	    }else {
	    	return false;
	    }
	    
	}

	/**
	 * 采购订单生成入库计划
	 */
	@Override
	public Boolean generateInWarehousePlan(Long orderId) {
		InWhPlanDto inWhPlanAddParamsDto = new InWhPlanDto();
		Order order = orderMapper.selectByPrimaryKey(orderId);
		Long companyId = SecurityInfoGetter.getCompanyId();
	    User loginUser = SecurityInfoGetter.getUser();
	    UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
	    inWhPlanAddParamsDto.setCompanyId(companyId);
	    inWhPlanAddParamsDto.setCreateUserId(loginUser.getUserId());
	    inWhPlanAddParamsDto.setCreateUserName(loginUser.getRealName());
	    inWhPlanAddParamsDto.setContractNo(order.getContractCode());
	    inWhPlanAddParamsDto.setCustomerId(companyId);
	    inWhPlanAddParamsDto.setCustomerName(userCompRel.getCompany().getFullName());
	    inWhPlanAddParamsDto.setGroupId(order.getGroupId());
	    Group group = groupWareHouseRpcService.selectByGroupId(order.getGroupId());
	    inWhPlanAddParamsDto.setGroupName(group.getGroupName());
	    inWhPlanAddParamsDto.setWareHouseId(order.getWarehouseId());
	    inWhPlanAddParamsDto.setWarehouseName(order.getReceiveWarehouse());
	    inWhPlanAddParamsDto.setCustomerContactName(order.getSender());
	    inWhPlanAddParamsDto.setCustomerContactPhone(order.getSenderPhone());
	    inWhPlanAddParamsDto.setCustomerPurchaseNo(order.getOrderNo());
	    if (null != order.getReceiveTime()) {
	    	inWhPlanAddParamsDto.setStoragePlanTime(order.getReceiveTime().toLocaleString());
	    }
//	    inWhPlanAddParamsDto.setStorageRemark(order.getRemarks());
	    
	    List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(order.getOrderId());
	    List<InWhPlanGoodsDto> inWhPlanGoodsDtoList = new ArrayList<InWhPlanGoodsDto>(orderProductList.size());
		for (OrderProduct orderProduct : orderProductList) {
			InWhPlanGoodsDto inWhPlanGoodsDto = new InWhPlanGoodsDto();
			inWhPlanGoodsDto.setGoodsId(orderProduct.getGoodsId());
			inWhPlanGoodsDto.setGoodsName(orderProduct.getName());
			inWhPlanGoodsDto.setGoodsCode(orderProduct.getCode());
			inWhPlanGoodsDto.setGoodsSpec(orderProduct.getSpec());
			inWhPlanGoodsDto.setInHousePrice(orderProduct.getPrice().doubleValue());
			inWhPlanGoodsDto.setPlanGoodsNum(orderProduct.getNum().doubleValue());
			inWhPlanGoodsDto.setUnit(orderProduct.getSku());
			inWhPlanGoodsDtoList.add(inWhPlanGoodsDto);
		}
  
	    inWhPlanAddParamsDto.setInWhPlanGoodsDtoList(inWhPlanGoodsDtoList);
	    
        String warehousePlan = warehouseRpcService.inWhPlanAdd(inWhPlanAddParamsDto);
		if (!StringUtils.isEmpty(warehousePlan)) {
			order.setWarehousePlan(warehousePlan);
		    orderMapper.updateByPrimaryKeySelective(order);
			return true;
		}else {
			return false;
		}
		
	}

	
	/**
	 * 销售单生成出库计划
	 */
	@Override
	public Boolean generateOutWarehousePlan(Long orderId) {
		Order order = orderMapper.selectByPrimaryKey(orderId);
		
		OutWhPlanDto outWhPlanDto = new OutWhPlanDto();
		Long companyId = SecurityInfoGetter.getCompanyId();
	    User loginUser = SecurityInfoGetter.getUser();
	    UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
		outWhPlanDto.setCompanyId(companyId);
		outWhPlanDto.setCreateUserId(loginUser.getUserId());
		outWhPlanDto.setCustomerId(companyId);
		outWhPlanDto.setCustomerName(userCompRel.getCompany().getFullName());
		outWhPlanDto.setContractNo(order.getContractCode());
		outWhPlanDto.setGroupId(order.getGroupId());
		Group group = groupWareHouseRpcService.selectByGroupId(order.getGroupId());
		outWhPlanDto.setGroupName(group.getGroupName());
		outWhPlanDto.setWareHouseId(order.getWarehouseId());
		outWhPlanDto.setWarehouseName(order.getReceiveWarehouse());
		outWhPlanDto.setCustomerPurchaseNo(order.getOrderNo());
		outWhPlanDto.setCustomerContactName(order.getReceiver());
		outWhPlanDto.setCustomerContactPhone(order.getReceiverPhone());
		if (null != order.getReceiveTime()) {
			outWhPlanDto.setStoragePlanTime(order.getReceiveTime().toLocaleString());
		}
//		outWhPlanDto.setStorageRemark(order.getRemarks());
		
		List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(order.getOrderId());
		List<OutWhPlanGoodsDto> outWhPlanGoodsDtoList = new ArrayList<>(orderProductList.size());
		for (OrderProduct orderProduct : orderProductList) {
			OutWhPlanGoodsDto outWhPlanGoodsDto = new OutWhPlanGoodsDto();
			outWhPlanGoodsDto.setGoodsName(orderProduct.getName());
			outWhPlanGoodsDto.setGoodsId(orderProduct.getGoodsId());
			outWhPlanGoodsDto.setGoodsSpec(orderProduct.getSpec());
			outWhPlanGoodsDto.setGoodsCode(orderProduct.getCode());
			outWhPlanGoodsDto.setUnit(orderProduct.getSku());
			outWhPlanGoodsDto.setPlanGoodsNum(orderProduct.getNum().doubleValue());
			outWhPlanGoodsDto.setOutHousePrice(orderProduct.getPrice().doubleValue());
			outWhPlanGoodsDtoList.add(outWhPlanGoodsDto);
		}
		outWhPlanDto.setOutWhPlanGoodsDtoList(outWhPlanGoodsDtoList);
		
		String warehousePlan  = warehouseRpcService.outWhPlanAdd(outWhPlanDto);
		if (!StringUtils.isEmpty(warehousePlan)) {
			order.setWarehousePlan(warehousePlan);
			orderMapper.updateByPrimaryKeySelective(order);
			return true;
		}else {
			return false;
		}
	
	}

	
	
	
	
	
	
	
	

  


}
