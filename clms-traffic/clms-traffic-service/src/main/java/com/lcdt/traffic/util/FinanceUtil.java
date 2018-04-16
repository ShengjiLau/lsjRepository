package com.lcdt.traffic.util;

import com.lcdt.traffic.web.dto.FeeAccountWaybillDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liz on 2018/4/16.
 */
public class FinanceUtil {
    public static Map getFeeTotalDto(FeeAccountWaybillDto feeTotalDto){
        Map feeTotal = new HashMap();
        if(feeTotalDto != null){
            feeTotal.put("freightTotalSum", feeTotalDto.getFreightTotal());
            feeTotal.put("otherFeeTotalSum", feeTotalDto.getOtherFeeTotal());
            feeTotal.put("feeTotalSum", feeTotalDto.getFeeTotal());
        }
        return feeTotal;
    }
}
