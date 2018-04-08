package com.lcdt.contract.web.utils;

import com.lcdt.contract.web.dto.ContractDto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liz on 2018/4/8.
 */
public class Utils {
    //根据合同生效日期判断合同状态
    public static ContractDto getContractStatus(ContractDto dto){
        int isApproval = 0;//不需要审批
        if(dto.getContractApprovalList() != null && dto.getContractApprovalList().size() > 0){
            isApproval = 1;//需要审批
        }
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date nowDate = sdf.parse(sdf.format(new Date()));//当前日期
            if(dto.getStartDate() != null && dto.getEndDate() != null){
                Date startDate = sdf.parse(sdf.format(dto.getStartDate()));
                Date endDate = sdf.parse(sdf.format(dto.getEndDate()));
                if(startDate.getTime() <= nowDate.getTime() && nowDate.getTime() <= endDate.getTime()){
                    dto.setContractStatus(isApproval==0?(short)0:(short)2);
                }else{
                    if(startDate.getTime() > nowDate.getTime()) {
                        dto.setContractStatus(isApproval == 0 ? (short) 1 : (short) 2);
                    }
                    if(nowDate.getTime() > endDate.getTime()) {
                        dto.setContractStatus(isApproval==0?(short)3:(short)2);
                    }
                }
            }else if(dto.getStartDate() != null && dto.getEndDate() == null){
                Date startDate = sdf.parse(sdf.format(dto.getStartDate()));
                if(startDate.getTime() > nowDate.getTime()){
                    dto.setContractStatus(isApproval==0?(short)1:(short)2);
                }else{
                    dto.setContractStatus(isApproval==0?(short)0:(short)2);
                }
            }else if(dto.getStartDate() == null && dto.getEndDate() != null){
                Date endDate = sdf.parse(sdf.format(dto.getEndDate()));
                if(nowDate.getTime() > endDate.getTime()){
                    dto.setContractStatus(isApproval==0?(short)3:(short)2);
                }else{
                    dto.setContractStatus(isApproval==0?(short)0:(short)2);
                }
            }else{
                dto.setContractStatus(isApproval==0?(short)1:(short)2);//没有生效时间，不需要审批为待生效
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dto;
    }
}
