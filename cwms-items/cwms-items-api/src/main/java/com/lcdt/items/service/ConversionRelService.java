package com.lcdt.items.service;

import com.lcdt.items.dto.ConversionRelDto;
import com.lcdt.items.model.ConversionRel;

/**
 * Created by lyqishan on 06/11/2017
 */

public interface ConversionRelService {
    /**
     * 增加一个多单位计算
     * @param conversionRelDto
     * @return
     */
    ConversionRel addConversionRel(ConversionRelDto conversionRelDto);

    /**
     * 根据converId删除一个多单位
     * @param converId
     * @return
     */
    int deleteConversionRel(Long converId);

    /**
     * 根据converId修改一个多单位
     * @param conversionRelDto
     * @return
     */
    int modifyConversionRel(ConversionRelDto conversionRelDto);

    /**
     * 根据converId查询ConversionRel
     * @param converId
     * @return
     */
    ConversionRel queryConversionRel(Long converId);
}
