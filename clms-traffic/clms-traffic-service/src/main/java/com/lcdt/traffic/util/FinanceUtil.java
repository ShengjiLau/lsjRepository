package com.lcdt.traffic.util;

import com.lcdt.traffic.web.dto.FeeAccountDto;
import com.lcdt.traffic.web.dto.FeeAccountWaybillDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liz on 2018/4/16.
 */
public class FinanceUtil {
    /**
     * 记账列表费用统计
     * @param feeTotalDto
     * @return
     */
    public static Map getFeeTotalDto(FeeAccountWaybillDto feeTotalDto){
        Map feeTotal = new HashMap();
        if(feeTotalDto != null){
            feeTotal.put("freightTotalSum", feeTotalDto.getFreightTotal());
            feeTotal.put("otherFeeTotalSum", feeTotalDto.getOtherFeeTotal());
            feeTotal.put("feeTotalSum", feeTotalDto.getFeeTotal());
        }else{
            feeTotal.put("freightTotalSum", 0);
            feeTotal.put("otherFeeTotalSum", 0);
            feeTotal.put("feeTotalSum", 0);
        }
        return feeTotal;
    }

    /**
     * 记账单列表费用统计
     * @param total
     * @param feeTotalDto
     * @return
     */
    public static Map getFeeAccountFeeTotalDto(Long total, FeeAccountDto feeTotalDto){
        Map feeTotal = new HashMap();
        if(feeTotalDto != null){
            feeTotal.put("freightTotalSum", feeTotalDto.getFreightTotal());
            feeTotal.put("otherFeeTotalSum", feeTotalDto.getOtherFeeTotal());
            feeTotal.put("feeTotalSum", feeTotalDto.getFeeTotal());
        }else {
            if(total > 0){
                feeTotal.put("freightTotalSum", 0);
                feeTotal.put("otherFeeTotalSum", 0);
                feeTotal.put("feeTotalSum", 0);
            }
        }
        return feeTotal;
    }
}
