package com.lcdt.traffic.dao;

import com.lcdt.traffic.model.PricingRule;

public interface PricingRuleMapper {
    int deleteByPrimaryKey(Long prId);

    int insert(PricingRule record);

    int insertSelective(PricingRule record);

    PricingRule selectByPrimaryKey(Long prId);

    int updateByPrimaryKeySelective(PricingRule record);

    int updateByPrimaryKey(PricingRule record);
}