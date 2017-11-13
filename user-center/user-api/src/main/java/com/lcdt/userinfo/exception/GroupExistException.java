package com.lcdt.userinfo.exception;

/**
 * Created by yangbinq on 2017/11/13.
 */
public class GroupExistException extends RuntimeException {

    public GroupExistException() {
    }

    public GroupExistException(String message) {
        super(message);
    }
}
