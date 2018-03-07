package com.lcdt.notify.model;

public interface NotifyReceiver {

    String getPhoneNum();

    Long getUserId();

    Long getCompanyId();

    Long getCarrierCompanyId();

    Long getCarrierUserId();

    String getCarrierPhoneNum();

    String getCustomerPhoneNum();

    String getDriverPhoneNum();

    String getReceivePhoneNum();
}
