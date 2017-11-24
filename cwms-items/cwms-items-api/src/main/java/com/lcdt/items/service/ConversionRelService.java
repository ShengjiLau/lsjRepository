package com.lcdt.items.service;

import com.lcdt.items.model.ConversionRel;

/**
 * Created by lyqishan on 06/11/2017
 */

public interface ConversionRelService {
    /**
     * 增加一个多单位计算
     * @param conversionRel
     * @return
     */
    ConversionRel addConversionRel(ConversionRel conversionRel);

    /**
     * 根据converId删除一个多单位
     * @param converId
     * @return
     */
    int deleteConversionRel(Long converId);

    /**
     * 根据converId修改一个多单位
     * @param conversionRel
     * @return
     */
    int modifyConversionRel(ConversionRel conversionRel);

    /**
     * 根据converId查询ConversionRel
     * @param converId
     * @return
     */
    ConversionRel queryConversionRel(Long converId);
}
