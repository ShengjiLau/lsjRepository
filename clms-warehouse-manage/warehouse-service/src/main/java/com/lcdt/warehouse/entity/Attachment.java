package com.lcdt.warehouse.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author code generate
 * @since 2018-05-07
 */
public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "attachment_id", type = IdType.AUTO)
    private Long attachmentId;
    private String obj1;
    private String obj2;
    private String obj3;
    private String obj4;
    private String obj5;


    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getObj1() {
        return obj1;
    }

    public void setObj1(String obj1) {
        this.obj1 = obj1;
    }

    public String getObj2() {
        return obj2;
    }

    public void setObj2(String obj2) {
        this.obj2 = obj2;
    }

    public String getObj3() {
        return obj3;
    }

    public void setObj3(String obj3) {
        this.obj3 = obj3;
    }

    public String getObj4() {
        return obj4;
    }

    public void setObj4(String obj4) {
        this.obj4 = obj4;
    }

    public String getObj5() {
        return obj5;
    }

    public void setObj5(String obj5) {
        this.obj5 = obj5;
    }

    @Override
    public String toString() {
        return "Attachment{" +
        ", attachmentId=" + attachmentId +
        ", obj1=" + obj1 +
        ", obj2=" + obj2 +
        ", obj3=" + obj3 +
        ", obj4=" + obj4 +
        ", obj5=" + obj5 +
        "}";
    }
}
