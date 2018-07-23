package com.lcdt.contract.web.dto;

/**
 * @AUTHOR liuh
 * @DATE 2018-07-20
 */
public class ResponseBaseDto {

    private int code;
    private String message;

    public ResponseBaseDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
