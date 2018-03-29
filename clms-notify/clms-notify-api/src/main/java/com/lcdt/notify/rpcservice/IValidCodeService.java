package com.lcdt.notify.rpcservice;


public interface IValidCodeService {

    boolean isCodeCorrect(String code, String tag, String phoneNum);

    String sendValidCode(String tag, Integer timeout, String phoneNum) throws ValidCodeExistException;

}
