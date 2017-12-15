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
    public static String PLAN_STATUS_SEND_OFF = "40";//已派完
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
    public static Short PLAN_CARRIER_TYPE_CHENGYUNSHANG = 1; //承运商
    public static Short PLAN_CARRIER_TYPE_DRIVER = 2; //司机





}