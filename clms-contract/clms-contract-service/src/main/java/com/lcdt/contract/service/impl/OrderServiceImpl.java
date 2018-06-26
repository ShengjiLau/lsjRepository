package com.lcdt.contract.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.lcdt.clms.security.helper.SecurityInfoGetter;
import com.lcdt.contract.dao.OrderApprovalMapper;
import com.lcdt.contract.model.OrderApproval;
import com.lcdt.userinfo.model.User;
import com.lcdt.userinfo.model.UserCompRel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcdt.contract.dao.ConditionQueryMapper;
import com.lcdt.contract.dao.OrderMapper;
import com.lcdt.contract.dao.OrderProductMapper;
import com.lcdt.contract.model.Order;
import com.lcdt.contract.model.OrderProduct;
import com.lcdt.contract.service.OrderService;
import com.lcdt.contract.web.dto.OrderDto;
import com.lcdt.traffic.dto.WaybillParamsDto;
import com.lcdt.traffic.model.PlanDetail;
import com.lcdt.traffic.model.WaybillPlan;
import com.lcdt.traffic.vo.ConstantVO;


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
    
    
    @Override
    public int addOrder(OrderDto orderDto) {
        BigDecimal aTotal = new BigDecimal(0);// aTotal为所有商品总价格
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
        int i = 0;
        int j = 0;
        if (null != orderDto.getOrderProductList() && orderDto.getOrderProductList().size() != 0) {
            for (OrderProduct orderProduct : orderDto.getOrderProductList()) {
                //为每个商品添加OrderId
                orderProduct.setOrderId(order.getOrderId());
            }
            i += nonautomaticMapper.insertOrderProductByBatch(orderDto.getOrderProductList());
            logger.debug("新增订单商品数量:" + i);
        }

        //审批流程添加
        if (null != orderDto.getOrderApprovalList() && orderDto.getOrderApprovalList().size() > 0) {
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
            j += orderMapper.updateByPrimaryKeySelective(order);
        } else {
            // todo 没有添加审批人，则认为合同无需审批
            //同时设置合同的审批状态为审批中
            order.setApprovalStatus(new Short("0"));
            j += orderMapper.updateByPrimaryKeySelective(order);
        }

        if (i > 0) {
            return result;
        } else {
            throw new RuntimeException("订单商品添加失败");
        }
    }


    @Override
    public int modOrder(OrderDto orderDto) {
        BigDecimal aTotal = new BigDecimal(0);
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
    public PageInfo<OrderDto> OrderList(OrderDto orderDto) {
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
        return pageInfo;
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


    @Override
    public int updateOrderIsDraft(Long orderId, Short isDraft) {
        return orderMapper.updateIsDraft(orderId, isDraft);
    }


	@Override
	public Boolean generateTrafficPlan(Long orderId) {
		int purchaseFlag = 1;
		int salesFlag = 2;
		
		Order order = orderMapper.selectByPrimaryKey(orderId);
		WaybillParamsDto WaybillParamsDto = new WaybillParamsDto();
		WaybillParamsDto.setCustomerId(order.getSupplierId());
		WaybillParamsDto.setCustomerName(order.getSupplier());
		Long companyId = SecurityInfoGetter.getCompanyId();
	    User loginUser = SecurityInfoGetter.getUser();
	    UserCompRel userCompRel = SecurityInfoGetter.geUserCompRel();
	    WaybillParamsDto.setDeptNames(userCompRel.getDeptNames());
	    WaybillParamsDto.setCreateId(loginUser.getUserId());
	    WaybillParamsDto.setCreateName(loginUser.getRealName());
	    WaybillParamsDto.setCompanyId(companyId);
	    WaybillParamsDto.setCompanyName(userCompRel.getCompany().getFullName()); //企业名称
	    WaybillParamsDto.setPlanSource(ConstantVO.PLAN_SOURCE_ENTERING); //计划来源-录入
	    WaybillParamsDto.setSalesOrder(order.getOrderSerialNo());
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
	   
	    List<OrderProduct> orderProductList = orderProductMapper.getOrderProductByOrderId(order.getOrderId());
	    List<PlanDetail> planDetailList = new ArrayList<PlanDetail>(orderProductList.size());
	    for (OrderProduct orderProduct : orderProductList) {
	    	PlanDetail planDetail = new PlanDetail();
	    	planDetail.setGoodsName(orderProduct.getName());
	    	planDetail.setGoodsSpec(orderProduct.getSpec());
	    	planDetail.setUnit(orderProduct.getSku());
	    	planDetail.setPlanAmount(orderProduct.getNum().floatValue());
	    	planDetail.setPayPrice(orderProduct.getPrice().floatValue());
	    	planDetail.setPayTotal(orderProduct.getTotal().floatValue());
	    	planDetail.setCompanyId(companyId);
	    	planDetail.setIsDeleted((short) 0);
	    	planDetail.setCreateDate(new Date());
	    	planDetail.setCreateId(loginUser.getUserId());
	    	planDetail.setCreateName(loginUser.getRealName());
	    	planDetailList.add(planDetail);
	    }
	    WaybillParamsDto.setPlanDetailList(planDetailList);
	    
	    
	    
	    
	    
	    
	   return null; 
	}


  


}
