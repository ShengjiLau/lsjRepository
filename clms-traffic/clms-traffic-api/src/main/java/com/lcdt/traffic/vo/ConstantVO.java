package com.lcdt.traffic.vo;

/**
 * Created by yangbinq on 2017/12/12.
 */
public class ConstantVO {


    //计划-创建方式
    public static String PLAN_SOURCE_ENTERING = "10"; //录入
    public static String PLAN_SOURCE_PUSH = "20";//推送

    //计划-状态
    public static String PLAN_STATUS_WAITE＿PUBLISH = "10"; //待发布
    public static String PLAN_STATUS_BIDDING = "15";//竞价中
    public static String PLAN_STATUS_APPROVAL = "20";//审批中
    public static String PLAN_STATUS_SEND_ORDERS = "30";//派单中
    public static String PLAN_STATUS_SEND_OFF = "40";//已派单，原来已派完
    public static String PLAN_STATUS_COMPLETED = "50";//已完成
    public static String PLAN_STATUS_CANCEL = "60"; //已取消

    //计划-派车方式(派车中是没有派完车，  已派车是派完车了--这块需要承运商派车后回传状态)
    public static Short PLAN_SEND_CARD_STATUS_ELSE = 0; //其它
    public static Short PLAN_SEND_CARD_STATUS_DOING = 1; //派车中
    public static Short PLAN_SEND_CARD_STATUS_COMPLETED = 2; //已派完


    //计划-派单方式
    public static Short PLAN_SEND_ORDER_TPYE_ELSE = 0; //其它
    public static Short PLAN_SEND_ORDER_TPYE_ZHIPAI = 1; //直派
    public static Short PLAN_SEND_ORDER_TPYE_JINGJIA = 2; //竞价


    //计划-承运人类型
    public static Short PLAN_CARRIER_TYPE_ELSE = 0; //其它
    public static Short PLAN_CARRIER_TYPE_CARRIER = 1; //承运商
    public static Short PLAN_CARRIER_TYPE_DRIVER = 2; //司机
    public static Short PLAN_CARRIER_TYPE_ALL_DRIVER = 4; //全部司机


    //计划-留言-留言人类型
    public static Short PLAN_LEAVE_MSG_TYPE_SHIPPER = 1;//货主
    public static Short PLAN_LEAVE_MSG_TYPE_CARRIER = 2;//承运商

    //运单状态
    public static short WAYBILL_STATUS_WATIE_SEND=1; //待发货
    public static short WAYBILL_STATUS_HAVE_FACTORY=2;//已入厂
    public static short WAYBILL_STATUS_HAVE_LOADING=3;//已装车
    public static short WAYBILL_STATUS_IN_TRANSIT=4;//运输中
    public static short WAYBILL_STATUS_IS_UNLOADING=5;//已卸货
    public static short WAYBILL_STATUS_HAVE_SIGNED=6;//已签收
    public static short WAYBILL_STATUS_HAVE_FINISH=7;//已完成
    public static short WAYBILL_STATUS_HAVE_CANCEL=8;//已取消


    //抢单是否采用
    public static Short SNATCH_GOODS_USING_DOING = 0; //处理中
    public static Short SNATCH_GOODS_USING_PASS = 1; //使用
    public static Short SNATCH_GOODS_USING_NOPASS = 2; //未采用



    //计划消息中的APP_URL
    public static String APP_URL = "";
    public static String CANCEL_FLAG = "";
    
    //正常生成的账单状态
    public static final short NORMAL_STATUS = 0;
    //应付对账单
    public static final short RECONCILE_PAYABLE = 1;
    //应收对账单
    public static final short RECONCILE_RECEIVABLE = 0;
    //未取消状态
    public static final short NO_CANCEL = 0;
    //不存在收付款状态
    public static final Integer NO_PAYMENT = 2;
    //存在收付款状态
    public static final Integer ALREADY_PAYMENT = 1;
    //默认每页数量
    public static final Integer PAGE_SIZE = 0;
    //默认页码
    public static final Integer PAGE_NUM = 1;
    //留言记账单
    public static final short MSG_ACCOUNT_TYPE = 0;
    //留言对账单
    public static final short MSG_RECONCILE_TYPE = 1;
    //付款
    public static final short EXCHANGE_PAYABLE = 1;
    //收款
    public static final short EXCHANGE_RECEIVABLE = 0;
    //异常值
    public static final Integer EXCEPTION_VALUE = -1;
    

}