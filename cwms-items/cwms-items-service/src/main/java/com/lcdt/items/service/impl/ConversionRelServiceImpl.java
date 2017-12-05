package com.lcdt.items.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lcdt.items.dao.ConversionRelMapper;
import com.lcdt.items.model.ConversionRel;
import com.lcdt.items.service.ConversionRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lyqishan on 06/11/2017
 */
@Transactional
@Service
public class ConversionRelServiceImpl implements ConversionRelService {

    @Autowired
    private ConversionRelMapper conversionRelMapper;

    @Override
    public ConversionRel addConversionRel(ConversionRel conversionRel) {
        conversionRelMapper.insert(conversionRel);
        return conversionRel;
    }

    @Override
    public int deleteConversionRel(Long converId,Long companyId) {
        ConversionRel conversionRel=new ConversionRel();
        conversionRel.setConverId(converId);
        conversionRel.setCompanyId(companyId);
        return conversionRelMapper.deleteByConverIdAndCompanyId(conversionRel);
    }

    @Override
    public int modifyConversionRel(ConversionRel conversionRel) {
        return conversionRelMapper.updateByConverIdAndCompanyId(conversionRel);
    }

    @Override
    public ConversionRel queryConversionRel(Long converId,Long companyId) {
        ConversionRel conversionRel = new ConversionRel();
        conversionRel.setConverId(converId);
        conversionRel.setCompanyId(companyId);
        return conversionRelMapper.selectByConverIdAndCompanyId(conversionRel);
    }
}
