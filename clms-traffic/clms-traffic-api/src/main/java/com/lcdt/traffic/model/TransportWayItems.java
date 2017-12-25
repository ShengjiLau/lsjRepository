package com.lcdt.traffic.model;

import com.lcdt.converter.ResponseData;

public class TransportWayItems implements java.io.Serializable,ResponseData {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_transport_way_items.transport_way_items_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long transportWayItemsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_transport_way_items.waybill_plan_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Long waybillPlanId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_transport_way_items.transport_way
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private Short transportWay;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_transport_way_items.item_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private String itemName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_transport_way_items.item_value
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    private String itemValue;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_transport_way_items.transport_way_items_id
     *
     * @return the value of t_transport_way_items.transport_way_items_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getTransportWayItemsId() {
        return transportWayItemsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_transport_way_items.transport_way_items_id
     *
     * @param transportWayItemsId the value for t_transport_way_items.transport_way_items_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setTransportWayItemsId(Long transportWayItemsId) {
        this.transportWayItemsId = transportWayItemsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_transport_way_items.waybill_plan_id
     *
     * @return the value of t_transport_way_items.waybill_plan_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Long getWaybillPlanId() {
        return waybillPlanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_transport_way_items.waybill_plan_id
     *
     * @param waybillPlanId the value for t_transport_way_items.waybill_plan_id
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setWaybillPlanId(Long waybillPlanId) {
        this.waybillPlanId = waybillPlanId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_transport_way_items.transport_way
     *
     * @return the value of t_transport_way_items.transport_way
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public Short getTransportWay() {
        return transportWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_transport_way_items.transport_way
     *
     * @param transportWay the value for t_transport_way_items.transport_way
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setTransportWay(Short transportWay) {
        this.transportWay = transportWay;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_transport_way_items.item_name
     *
     * @return the value of t_transport_way_items.item_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_transport_way_items.item_name
     *
     * @param itemName the value for t_transport_way_items.item_name
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_transport_way_items.item_value
     *
     * @return the value of t_transport_way_items.item_value
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_transport_way_items.item_value
     *
     * @param itemValue the value for t_transport_way_items.item_value
     *
     * @mbg.generated Tue Dec 12 09:56:12 CST 2017
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue == null ? null : itemValue.trim();
    }
}